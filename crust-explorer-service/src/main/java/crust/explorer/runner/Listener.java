package crust.explorer.runner;


import crust.explorer.enums.SyncLogTypeEnum;
import crust.explorer.enums.TableEnum;
import crust.explorer.event.PutChainCountCacheEvent;
import crust.explorer.event.RefreshBlockEvent;
import crust.explorer.event.RemoveLongAgoDataEvent;
import crust.explorer.event.SyncBlockEvent;
import crust.explorer.pojo.po.NetworkOverviewPO;
import crust.explorer.pojo.po.SysSyncLogPO;
import crust.explorer.service.*;
import crust.explorer.service.impl.CompositeServiceImpl;
import crust.explorer.util.ChainUtils;
import crust.explorer.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
@EnableAsync
public class Listener {

    @Autowired
    BlockService blockService;
    @Autowired
    ExtrinsicService extrinsicService;
    @Autowired
    TransferService transferService;
    @Autowired
    RewardSlashService rewardSlashService;
    @Autowired
    NetworkOverviewService networkOverviewService;
    @Autowired
    CompositeServiceImpl compositeService;
    @Autowired
    SysSyncLogService sysSyncLogService;

    @Async("syncDataExecutor")
    @EventListener(SyncBlockEvent.class)
    public void listenSyncHistoryBlockEvent(SyncBlockEvent event) {
        try {
            log.info("Listener.listenSyncBlockEvent :{}", event);
            Object begin = ChainUtils.SYNC_DATA.get(Constants.SYNC_BLOCK_BEGIN_TIME);
            if (Objects.isNull(begin)) {
                ChainUtils.SYNC_DATA.put(Constants.SYNC_BLOCK_BEGIN_TIME, System.currentTimeMillis());
            }
            blockService.syncHistoryBlock(event);
            SysSyncLogPO logPO = ChainUtils.buildSysSyncLogPO(null, event, SyncLogTypeEnum.DONE);
            sysSyncLogService.save(logPO);
        } catch (Exception e) {
            SysSyncLogPO logPO = ChainUtils.buildSysSyncLogPO(e, event, SyncLogTypeEnum.ERROR);
            sysSyncLogService.save(logPO);
            log.error("Listener.listenSyncBlockEvent 异常:", e);
        }
    }

    @Async("syncDataExecutor")
    @EventListener(RefreshBlockEvent.class)
    public void listenRefreshBlockEvent(RefreshBlockEvent event) {
        try {
            log.info("Listener.listenRefreshBlockEvent blockNum :{}", event.getBlocks().get(0).getBlockNum());
            blockService.syncLastPage(event.getBlocks());
        } catch (Exception e) {
            log.error("Listener.listenRefreshBlockEvent 异常:", e);
        }
    }

    @Async("queryExecutor")
    @EventListener(RemoveLongAgoDataEvent.class)
    public void listenRemoveLongAgoDataEvent(RemoveLongAgoDataEvent event) {
        try {
            log.info("Listener.listenRemoveLongAgoDataEvent :{}", event);
            compositeService.removeLongAgoData();
        } catch (Exception e) {
            log.error("Listener.listenRemoveLongAgoDataEvent 异常:", e);
        }
    }

    @Async("queryExecutor")
    @EventListener(PutChainCountCacheEvent.class)
    public void listenPutChainCountCacheEvent(PutChainCountCacheEvent event) {
        try {
            log.info("Listener.PutChainCountCacheEvent :{}", event);
            if (TableEnum.CE_BLOCK.equals(event.getTableEnum())) {
                blockService.getAndPutBlockCountToCache(event.getTableNos());
            } else if (TableEnum.CE_EXTRINSIC.equals(event.getTableEnum())) {
                extrinsicService.getAndPutExtrinsicCountToCache(event.getTableNos(), event.getSecondKey());
            } else if (TableEnum.CE_TRANSFER.equals(event.getTableEnum())) {
                transferService.getAndPutTransferCountToCache(event.getTableNos(), event.getSecondKey());
            } else if (TableEnum.CE_REWARD_SLASH.equals(event.getTableEnum())) {
                rewardSlashService.getAndPutRewardSlashCountToCache(event.getTableNos(), event.getSecondKey());
            }
        } catch (Exception e) {
            log.error("Listener.PutChainCountCacheEvent 异常:", e);
        }
    }

    @Async("queryExecutor")
    @EventListener(NetworkOverviewPO.class)
    public void listenRefreshNetworkOverview(NetworkOverviewPO event) {
        try {
            log.info("Listener.listenRefreshNetworkOverview :{}", event);
            networkOverviewService.updateById(event);
        } catch (Exception e) {
            log.error("Listener.listenRefreshNetworkOverview 异常:", e);
        }
    }

}
