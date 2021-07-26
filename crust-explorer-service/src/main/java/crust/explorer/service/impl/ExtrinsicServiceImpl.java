package crust.explorer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import crust.explorer.enums.TableEnum;
import crust.explorer.mapper.EventMapper;
import crust.explorer.mapper.ExtrinsicMapper;
import crust.explorer.pojo.dto.PageTableDto;
import crust.explorer.pojo.po.ExtrinsicPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.EventVO;
import crust.explorer.pojo.vo.ExtrinsicVO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.TableVo;
import crust.explorer.service.ExtrinsicService;
import crust.explorer.util.CacheUtils;
import crust.explorer.util.RedisKeys;
import crust.explorer.util.RedisUtils;
import crust.explorer.util.TableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ExtrinsicServiceImpl extends ServiceImpl<ExtrinsicMapper, ExtrinsicPO> implements ExtrinsicService {

    @Autowired
    EventMapper eventMapper;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    CacheUtils cacheUtils;

    @Override
    public PageVo<ExtrinsicVO> listPage(List<Integer> tableNos, String address, BaseQo qo) {
        List<TableVo> totalCount = this.getExtrinsicCountFromCache(tableNos, address);
        List<PageTableDto> pageTables = TableUtils.buildPageTables(totalCount, qo);
        if (CollectionUtils.isEmpty(pageTables)) {
            return PageVo.initialized(qo, totalCount.get(0).getCount());
        }
        long begin = System.currentTimeMillis();
        List<ExtrinsicVO> extrinsics = baseMapper.listExtrinsicsPage(pageTables, address);
        log.info("交易列表分页接口 耗时 list ：{}", (System.currentTimeMillis() - begin));
        return new PageVo<>(
                qo.getCurrent(),
                qo.getSize(),
                totalCount.get(0).getCount(),
                extrinsics
        );
    }

    @Override
    public PageVo<ExtrinsicVO> listPage(Integer blockNum, BaseQo qo) {
        int tableNo = TableUtils.getTableNo(blockNum);
        qo = TableUtils.rectifyBaseQo(qo);
        PageVo<ExtrinsicVO> page = PageVo.initialized(qo);
        PageVo<ExtrinsicVO> pageVo = this.baseMapper.listExtrinsicsAutoPage(page, tableNo, blockNum);
        return pageVo;
    }

    @Override
    public ExtrinsicVO getInfo(Integer tableNo, Integer blockNum, String extrinsicIndex) {
        ExtrinsicVO extrinsic = redisUtils.get(RedisKeys.EXTRINSIC_BLOCK_NUM_VO + extrinsicIndex, ExtrinsicVO.class);
        if (Objects.nonNull(extrinsic)) {
            return extrinsic;
        }
        ExtrinsicVO extrinsicVO = this.baseMapper.getInfo(tableNo, blockNum, extrinsicIndex);
        if (Objects.isNull(extrinsicVO)) {
            redisUtils.set(RedisKeys.EXTRINSIC_BLOCK_NUM + blockNum, -1, RedisKeys.CACHE_2S);
        }
        fillEvents(extrinsicVO);
        return extrinsicVO;
    }

    @Override
    public List<TableVo> getExtrinsicCountFromCache(List<Integer> tableNos, String address) {
        List<TableVo> totalCount = cacheUtils.getChainCountFromCache(TableEnum.CE_EXTRINSIC, address, tableNos);
        if (!CollectionUtils.isEmpty(totalCount)) {
            return totalCount;
        }
        return getAndPutExtrinsicCountToCache(tableNos, address);
    }

    @Override
    public List<TableVo> getAndPutExtrinsicCountToCache(List<Integer> tableNos, String address) {
        long begin = System.currentTimeMillis();
        List<TableVo> totalCount = this.baseMapper.getTotalCount(tableNos, address);
        log.info("交易列表分页接口 耗时 count ：{}", (System.currentTimeMillis() - begin));
        cacheUtils.putChainCountToCache(TableEnum.CE_EXTRINSIC, address, totalCount);
        return totalCount;
    }


//    @Override
//    public ExtrinsicVO getInfoByHash(int tableNo, Integer blockNum, String extrinsicHash) {
//        ExtrinsicVO extrinsicVO = this.baseMapper.getInfoByHash(tableNo, blockNum, extrinsicHash);
//        if (Objects.isNull(extrinsicVO)) {
//            redisUtils.set(RedisKeys.EXTRINSIC_HASH + extrinsicHash, -1, RedisUtils.CACHE_2S);
//            redisUtils.set(RedisKeys.EXTRINSIC_BLOCK_NUM + blockNum, -1, RedisUtils.CACHE_2S);
//        }
//        fillEvents(extrinsicVO);
//        return extrinsicVO;
//    }

    @Override
    public ExtrinsicVO getExtrinsicByHash(String extrinsicHash) {
        ExtrinsicVO extrinsic = redisUtils.get(RedisKeys.EXTRINSIC_HASH_VO + extrinsicHash, ExtrinsicVO.class);
        if (Objects.nonNull(extrinsic)) {
            return extrinsic;
        }
        List<Integer> tableNos = TableUtils.getTableNos();
        ExtrinsicVO extrinsicVO = this.baseMapper.getExtrinsicByHash(tableNos, extrinsicHash);
        if (Objects.isNull(extrinsicVO)) {
            redisUtils.set(RedisKeys.EXTRINSIC_HASH + extrinsicHash, -1, RedisKeys.CACHE_2S);
        }
        fillEvents(extrinsicVO);
        return extrinsicVO;
    }

    private void fillEvents(ExtrinsicVO extrinsicVO) {
        if (Objects.nonNull(extrinsicVO)) {
            int tableNo = TableUtils.getTableNo(extrinsicVO.getBlockNum());
            List<EventVO> events = eventMapper.getEvents(tableNo, extrinsicVO.getBlockNum(), extrinsicVO.getExtrinsicIndex());
            extrinsicVO.setEvents(events);
            redisUtils.set(RedisKeys.EXTRINSIC_HASH + extrinsicVO.getExtrinsicHash(), extrinsicVO.getBlockNum());
            redisUtils.set(RedisKeys.EXTRINSIC_BLOCK_NUM + extrinsicVO.getExtrinsicIndex(), extrinsicVO.getBlockNum());
            redisUtils.set(RedisKeys.EXTRINSIC_HASH_VO + extrinsicVO.getExtrinsicHash(), extrinsicVO, RedisKeys.CACHE_20S);
            redisUtils.set(RedisKeys.EXTRINSIC_BLOCK_NUM_VO + extrinsicVO.getExtrinsicIndex(), extrinsicVO, RedisKeys.CACHE_20S);
        }
    }
}
