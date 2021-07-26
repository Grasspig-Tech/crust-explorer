package crust.explorer.service.impl;

import crust.explorer.pojo.po.NetworkOverviewPO;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.pojo.vo.ExtrinsicVO;
import crust.explorer.pojo.vo.TableVo;
import crust.explorer.runner.Publisher;
import crust.explorer.sao.ChainSaoImpl;
import crust.explorer.service.*;
import crust.explorer.util.ChainUtils;
import crust.explorer.util.Constants;
import crust.explorer.util.RedisUtils;
import crust.explorer.util.TableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompositeServiceImpl {
    private AtomicInteger counter = new AtomicInteger(0);
    @Autowired
    ChainSaoImpl chainSao;
    @Autowired
    BlockService blockService;
    @Autowired
    EventService eventService;
    @Autowired
    ExtrinsicService extrinsicService;
    @Autowired
    RewardSlashService rewardSlashService;
    @Autowired
    TransferService transferService;
    @Autowired
    SysSyncLogService sysSyncLogService;
    @Autowired
    NetworkOverviewService networkOverviewService;
    @Autowired
    MemberService memberService;
    @Autowired
    BondedPledgeService bondedPledgeService;
    @Autowired
    NominatorService nominatorService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    Publisher publisher;

    public BlockVO getByBlockNum(Integer blockNum) {
        int tableNo = TableUtils.getTableNo(blockNum);
        return blockService.getByBlockNum(tableNo, blockNum);
    }

    public BlockVO getBlockByHash(String hash) {
        return blockService.getBlockByHash(hash);
    }

    public ExtrinsicVO getExtrinsicByBlockNum(String extrinsicIndex) {
        Integer blockNum = ChainUtils.getBlockNum(extrinsicIndex);
        int tableNo = TableUtils.getTableNo(blockNum);
        return extrinsicService.getInfo(tableNo, blockNum, extrinsicIndex);
    }

    public ExtrinsicVO getExtrinsicByExtrinsicHash(String extrinsicHash) {
        return extrinsicService.getExtrinsicByHash(extrinsicHash);
    }

    public void removeLongAgoData() {
        sysSyncLogService.removeLongAgoData();
        networkOverviewService.removeLongAgoData();
        memberService.removeLongAgoData();
        bondedPledgeService.removeLongAgoData();
        nominatorService.removeLongAgoData();
    }

    public void countNetworkOverviewChain() {
        NetworkOverviewPO only = networkOverviewService.getOnly();
        if (Objects.isNull(only)) {
            return;
        }
        List<Integer> tableNos = TableUtils.getTableNos();
        List<TableVo> extrinsicCount = extrinsicService.getAndPutExtrinsicCountToCache(tableNos, null);
        List<TableVo> transferCount = transferService.getAndPutTransferCountToCache(tableNos, null);
        TableUtils.fillTotalCount(extrinsicCount);
        TableUtils.fillTotalCount(transferCount);
        Date now = new Date();
        long end = now.getTime() / 1000;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 24);
        long begin = calendar.getTime().getTime() / 1000;
        List<Integer> blockTables = TableUtils.getFirstThreeTableNos();
        int numBlock = blockService.getCountBlock(blockTables, begin, end);
        int numberTrade = extrinsicCount.get(0).getCount();
        int numberTransfer = transferCount.get(0).getCount();
        only.setTotalOutputLast24(String.valueOf(numBlock));
        only.setNumberTransfer(numberTransfer);
        only.setNumberTrade(numberTrade);
        log.info("CompositeServiceImpl.countNetworkOverviewChain,only:{}", only);
        networkOverviewService.putOnly(only);
    }

    public void refreshLastBlockToOverview(NetworkOverviewPO only, List<BlockVO> blockVOS) {
        if (Objects.isNull(only)) {
            return;
        }
        int i = counter.get();
        if (i < 1) {
            try {
                counter.incrementAndGet();
                NetworkOverviewPO lastOverview = chainSao.queryNetworkOverview();
                ChainUtils.fillLastOverview(lastOverview, only);
                ChainUtils.rectifyCountdownEra(only);
                log.info("CompositeServiceImpl.refreshLastBlockToOverview lastOverview:{}", only);
            } catch (Exception e) {
            }
        }
        String countdownSession = only.getCountdownSession();
        if (!CollectionUtils.isEmpty(blockVOS)) {
            int blockHeight = blockVOS.get(0).getBlockNum();
            int blockLastTime = blockVOS.get(0).getBlockTimestamp();
            List<BlockVO> confirmedBlocks = blockVOS.stream().filter(block -> block.getFinalized() == 1).collect(Collectors.toList());
            int blockHeightConfirmed = confirmedBlocks.get(0).getBlockNum();
            only.setBlockHeight(String.valueOf(blockHeight));
            only.setBlockLastTime(String.valueOf(blockLastTime));
            only.setBlockHeightConfirmed(String.valueOf(blockHeightConfirmed));
        }
        ChainUtils.putObj(Constants.LAST_NETWORK_OVERVIEW, only);
        if (Integer.parseInt(countdownSession) <= 8) {
            log.info("CompositeServiceImpl.refreshLastBlockToOverview countdownSession:{}", countdownSession);
            ChainUtils.putObj(Constants.RECTIFY_NETWORK_OVERVIEW, true);
        } else {
            log.info("CompositeServiceImpl.refreshLastBlockToOverview false");
            ChainUtils.putObj(Constants.RECTIFY_NETWORK_OVERVIEW, false);
        }
    }

    public void skipRectifyCountdownEra(NetworkOverviewPO target) {
        if (Objects.isNull(target) || Objects.isNull(target.getCountdownSession())) {
            return;
        }
        String countdownSession = target.getCountdownSession();
        if (Integer.parseInt(countdownSession) > 8) {
            ChainUtils.putObj(Constants.RECTIFY_NETWORK_OVERVIEW, false);
            log.info("CompositeServiceImpl.skipRectifyCountdownEra 1");
            return;
        }
        if (Integer.parseInt(countdownSession) <= 2) {
            try {
                NetworkOverviewPO overview = chainSao.queryNetworkOverview();
                ChainUtils.fillLastOverview(overview, target);
                ChainUtils.rectifyCountdownEra(target);
                countdownSession = target.getCountdownSession();
                log.info("CompositeServiceImpl.skipRectifyCountdownEra countdownSession:{}", countdownSession);
                if (Integer.parseInt(countdownSession) > 8) {
                    ChainUtils.putObj(Constants.LAST_NETWORK_OVERVIEW, target);
                    ChainUtils.putObj(Constants.RECTIFY_NETWORK_OVERVIEW, false);
                    log.info("CompositeServiceImpl.skipRectifyCountdownEra 2");
                }
            } catch (Exception e) {
            }
        }
    }
}
