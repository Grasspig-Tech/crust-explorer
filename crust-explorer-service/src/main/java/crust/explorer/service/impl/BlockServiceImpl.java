package crust.explorer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import crust.explorer.enums.SyncModeEnum;
import crust.explorer.enums.TableEnum;
import crust.explorer.enums.hint.LogicEnum;
import crust.explorer.event.SyncBlockEvent;
import crust.explorer.exception.BusinessException;
import crust.explorer.mapper.BlockMapper;
import crust.explorer.mapper.EventMapper;
import crust.explorer.mapper.ExtrinsicMapper;
import crust.explorer.pojo.dto.PageTableDto;
import crust.explorer.pojo.po.BlockPO;
import crust.explorer.pojo.po.SysSyncLogPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.SyncBlockVO;
import crust.explorer.pojo.vo.TableVo;
import crust.explorer.runner.Publisher;
import crust.explorer.sao.ChainSaoImpl;
import crust.explorer.service.BlockService;
import crust.explorer.service.SysSyncLogService;
import crust.explorer.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BlockServiceImpl extends ServiceImpl<BlockMapper, BlockPO> implements BlockService {

    @Autowired
    ChainSaoImpl chainSao;

    @Autowired
    Publisher publisher;

    @Autowired
    SysSyncLogService sysSyncLogService;

    @Autowired
    ChainServiceImpl chainService;

    @Autowired
    EventMapper eventMapper;

    @Autowired
    ExtrinsicMapper extrinsicMapper;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    CacheUtils cacheUtils;

    @Override
    public void publishSyncHistoryBlock(BlockVO block) {
        if (Objects.nonNull(block)) {
            log.info("BlockServiceImpl.publishSyncHistoryBlock start blockNum:{}", block.getBlockNum());
            int maxTableNo = TableUtils.getMaxTableNo(block.getBlockNum());
            for (int i = maxTableNo; i > 1; i--) {
                log.info("BlockServiceImpl.publishSyncHistoryBlock i:{}", i);
                SyncBlockEvent event = SyncBlockEvent.builder()
                        .syncMode(SyncModeEnum.AUTO.getType()).tableName(TableEnum.CE_BLOCK.getTableName())
                        .tableNo(i).maxTableNo(maxTableNo).networkBlockNum(block.getBlockNum()).build();
                publisher.publishSyncHistoryBlock(event);
            }
        }
        log.info("BlockServiceImpl.publishSyncHistoryBlock end");
    }

    @Override
    public List<TableVo> getBlockCountFromCache(List<Integer> tableNos) {
        List<TableVo> totalCount = cacheUtils.getChainCountFromCache(TableEnum.CE_BLOCK, null, tableNos);
        if (!CollectionUtils.isEmpty(totalCount)) {
            return totalCount;
        }
        return getAndPutBlockCountToCache(tableNos);
    }

    @Override
    public List<TableVo> getAndPutBlockCountToCache(List<Integer> tableNos) {
        long begin = System.currentTimeMillis();
        List<TableVo> totalCount = this.baseMapper.getTotalCount(tableNos);
        log.info("区块列表分页接口 耗时 count ：{}", (System.currentTimeMillis() - begin));
        cacheUtils.putChainCountToCache(TableEnum.CE_BLOCK, null, totalCount);
        return totalCount;
    }

    @Override
    public void syncHistoryBlock(SyncBlockEvent event) throws InterruptedException {
        log.info("BlockServiceImpl.syncHistoryBlock start,event:{}", event);
        if (MathUtils.gtZero(event.getNetworkBlockNum())) {
            SysSyncLogPO syncTo = sysSyncLogService.getSyncToLog(event);
            if (Objects.isNull(syncTo)) {
                throw new BusinessException(LogicEnum.NOT_SYNC_LOG);
            }
            int startBlock = TableUtils.getStartSyncBlockNum(event, syncTo);
            int lastBlock = syncTo.getBlockNum();
            boolean syncDone = TableUtils.judgeSyncDone(event.getTableNo(), lastBlock);
            int retry = 0;
            int next = startBlock;
            while (!syncDone) {
                long begin = System.currentTimeMillis();
                int offset = TableUtils.getOffset(event.getTableNo(), startBlock, MathUtils.TEN);
//                int sleep = (int) Math.pow(2, event.getTableNo() - 1);
//                Thread.sleep(sleep * Constants.SYNC_SLEEP_1S);
                if (event.getTableNo() == 1) {
                    Thread.sleep(Constants.SYNC_SLEEP_1S);
                } else {
                    Thread.sleep(Constants.SYNC_SLEEP_1S * event.getTableNo() - 1);
                }
                List<BlockVO> blocks = chainSao.queryBlockLimit(startBlock, offset, event.getDKey());
                if (!CollectionUtils.isEmpty(blocks)) {
                    SyncBlockVO syncBlockVO = ChainUtils.buildSyncBlockVO(blocks);
                    chainService.writeChainData(event, syncBlockVO);
                    lastBlock = blocks.get(blocks.size() - 1).getBlockNum();
                    syncDone = TableUtils.judgeSyncDone(event.getTableNo(), lastBlock);
                    startBlock -= offset;
                    next = startBlock;
                } else {
                    if (event.getMaxTableNo() == event.getTableNo() && event.getNetworkBlockNum() == startBlock) {
                        startBlock -= offset;
                        next = startBlock;
                        syncDone = false;
                        continue;
                    }
                    if (next == startBlock) {
                        retry++;
                    } else {
                        retry = 0;
                    }
                    if (retry > 3) {
                        throw new BusinessException(LogicEnum.RETRY_SYNC_BLOCK_ERROR);
                    }
                }
                long end = System.currentTimeMillis();
                log.info("BlockServiceImpl.syncHistoryBlock 耗时:{}", (end - begin));
            }
        }
        log.info("BlockServiceImpl.syncHistoryBlock end,event:{}", event);
    }

    @Override
    public void syncLastPage(List<BlockVO> blockVOS) {
        if (CollectionUtils.isEmpty(blockVOS)) {
            return;
        }
        long begin = System.currentTimeMillis();
        log.info("BlockServiceImpl.syncLastPage blockNum:{}", blockVOS.get(0).getBlockNum());
//        LambdaQueryWrapper<BlockPO> wrapper = Wrappers.lambdaQuery();
//        wrapper.orderByDesc(BlockPO::getBlockNum).last(Constants.LIMIT_10);
//        List<BlockPO> blockPOS = baseMapper.selectList(wrapper);
//        List<BlockVO> filteredBlocks = TableUtils.filterRepeatBlock(blockPOS, blockVOS);
//        log.info("BlockServiceImpl.syncLastPage filteredBlocks:{}", filteredBlocks);
        refreshBlock(blockVOS);
        long end = System.currentTimeMillis();
        log.info("BlockServiceImpl.syncLastPage 耗时:{}", (end - begin));
    }

    @Override
    public void syncLastTenDay() {
        long begin = System.currentTimeMillis();
        List<BlockVO> blockVOS = chainSao.queryLastBlockPage(MathUtils.TEN);
        if (CollectionUtils.isEmpty(blockVOS)) {
            int i = 0;
            for (; ; ) {
                blockVOS = chainSao.queryLastBlockPage(MathUtils.TEN);
                if (CollectionUtils.isEmpty(blockVOS) && i < 3) {
                    i++;
                } else {
                    break;
                }
                try {
                    Thread.sleep(Constants.SYNC_SLEEP_2S);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (CollectionUtils.isEmpty(blockVOS)) {
            log.info("BlockServiceImpl.syncLastTenDay 重试 3 次中断");
            return;
        }
        int startBlock = blockVOS.get(blockVOS.size() - 1).getBlockNum();
        for (int i = 0; i < 1000; i++) {//todo 3个线程，每个20000？
            try {
                long loopBegin = System.currentTimeMillis();
                blockVOS = chainSao.queryBlockLimit(startBlock, MathUtils.TEN, RedisKeys.CHAIN_REFRESH_BLOCK_DOMAIN);
                refreshBlock(blockVOS);
                if (!CollectionUtils.isEmpty(blockVOS)) {
                    startBlock = blockVOS.get(blockVOS.size() - 1).getBlockNum() - 1;
                } else {
                    startBlock -= MathUtils.TEN;
                }
                long loopEnd = System.currentTimeMillis();
                log.info("BlockServiceImpl.syncLastTenDay loop 耗时:{},i:{},next:{}", (loopEnd - loopBegin), i, startBlock);
                if (i > 0 && i % 10 == 0) {
                    Thread.sleep(Constants.SYNC_SLEEP_1S * (i % 2 + 1) + 1);
                } else {
                    Thread.sleep(Constants.SYNC_SLEEP_1S * (i % 2 + 1));
                }
            } catch (Exception e) {
                log.error("BlockServiceImpl.syncLastTenDay 出错:{}", e.getLocalizedMessage(), e);
            }
        }
        long end = System.currentTimeMillis();
        log.info("BlockServiceImpl.syncLastTenDay 耗时:{}", (end - begin));
    }

    @Override
    public void refreshBlock(List<BlockVO> blockVOS) {
        if (!CollectionUtils.isEmpty(blockVOS)) {
            Map<Integer, List<BlockVO>> collect = TableUtils.groupBlock(blockVOS);
            collect.forEach((table, blocks) -> {
                if (!CollectionUtils.isEmpty(blocks)) {
                    SyncBlockVO syncBlockVO = ChainUtils.buildSyncBlockVO(blocks);
                    chainService.refreshChainData(table, syncBlockVO);
                }
            });
        }
    }

    @Override
    public PageVo<BlockVO> listPage(List<Integer> tableNos, BaseQo qo) {
        List<TableVo> totalCount = this.getBlockCountFromCache(tableNos);
        List<PageTableDto> pageTables = TableUtils.buildPageTables(totalCount, qo);
        if (CollectionUtils.isEmpty(pageTables)) {
            return PageVo.initialized(qo, totalCount.get(0).getCount());
        }
        long begin = System.currentTimeMillis();
        List<BlockVO> blocks = baseMapper.listBlocksPage(pageTables);
        log.info("区块列表分页接口 耗时 list ：{}", (System.currentTimeMillis() - begin));
        return new PageVo<>(
                qo.getCurrent(),
                qo.getSize(),
                totalCount.get(0).getCount(),
                blocks
        );
    }

    @Override
    public BlockVO getByBlockNum(Integer tableNo, Integer blockNum) {
        BlockVO blockVO = redisUtils.get(RedisKeys.BLOCK_NUM_VO + blockNum, BlockVO.class);
        if (Objects.nonNull(blockVO)) {
            return blockVO;
        }
        BlockVO block = this.baseMapper.getByBlockNum(tableNo, blockNum);
        if (Objects.nonNull(block)) {
            putBlockToRedis(block);
        } else {
            redisUtils.set(RedisKeys.BLOCK_NUM + blockNum, -1, RedisKeys.CACHE_2S);
        }
        return block;
    }

    private void putBlockToRedis(BlockVO block) {
        if (Objects.nonNull(block)) {
            redisUtils.set(RedisKeys.BLOCK_HASH + block.getHash(), block.getBlockNum());
            redisUtils.set(RedisKeys.BLOCK_NUM + block.getBlockNum(), block.getBlockNum());
            redisUtils.set(RedisKeys.BLOCK_HASH_VO + block.getHash(), block, RedisKeys.CACHE_20S);
            redisUtils.set(RedisKeys.BLOCK_NUM_VO + block.getBlockNum(), block, RedisKeys.CACHE_20S);
        }
    }


    @Override
    public BlockVO getBlockByHash(String hash) {
        BlockVO blockVO = redisUtils.get(RedisKeys.BLOCK_HASH_VO + hash, BlockVO.class);
        if (Objects.nonNull(blockVO)) {
            return blockVO;
        }
        List<Integer> tableNos = TableUtils.getTableNos();
        BlockVO block = this.baseMapper.getBlockByHash(tableNos, hash);
        if (Objects.nonNull(block)) {
            putBlockToRedis(block);
        } else {
            redisUtils.set(RedisKeys.BLOCK_HASH + hash, -1, RedisKeys.CACHE_2S);
        }
        return block;
    }

    @Override
    public Integer getCountBlock(List<Integer> tableNos, long begin, long end) {
        return this.baseMapper.getCountBlock(tableNos, begin, end);
    }


    private List<BlockVO> filterRepeatBlock(List<BlockVO> blockVOS) {
        if (CollectionUtils.isEmpty(blockVOS)) {
            return blockVOS;
        }
        List<Integer> blockNums = blockVOS.stream().map(BlockVO::getBlockNum).collect(Collectors.toList());
        LambdaQueryWrapper<BlockPO> query = Wrappers.lambdaQuery();
        query.in(BlockPO::getBlockNum, blockNums);
        List<BlockPO> blockPOS = this.baseMapper.selectList(query);
        if (!CollectionUtils.isEmpty(blockPOS)) {
            List<Integer> repeatBlockNums = blockPOS.stream().map(BlockPO::getBlockNum).collect(Collectors.toList());
            blockVOS = blockVOS.stream().filter(block -> repeatBlockNums.contains(block.getBlockNum())).collect(Collectors.toList());
        }
        return blockVOS;
    }

}
