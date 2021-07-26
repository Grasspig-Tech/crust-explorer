package crust.explorer.ws;

import crust.explorer.enums.ChannelEnum;
import crust.explorer.event.RefreshBlockEvent;
import crust.explorer.pojo.po.NetworkOverviewPO;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.runner.Publisher;
import crust.explorer.sao.ChainSaoImpl;
import crust.explorer.service.NetworkOverviewService;
import crust.explorer.service.impl.CompositeServiceImpl;
import crust.explorer.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Configuration
@EnableScheduling
public class PushMessageTask {

    @Autowired
    ChainSaoImpl chainSao;

    @Autowired
    NetworkOverviewService networkOverviewService;

    @Autowired
    CompositeServiceImpl compositeService;

    @Autowired
    Publisher publisher;

    @Scheduled(cron = "0/1 * * * * ?")
    public void rectifyCountdownEra() {
        Object obj = ChainUtils.getObj(Constants.RECTIFY_NETWORK_OVERVIEW);
        if (Objects.nonNull(obj) && (boolean) obj) {
            NetworkOverviewPO only = networkOverviewService.getOnly();
            if (Objects.isNull(only)) {
                return;
            }
            ChainUtils.rectifyCountdownEra(only);
            compositeService.skipRectifyCountdownEra(only);
            ModuleMessage moduleMessage = WebSocketUtils.buildModuleMessage(
                    StringUtils.EMPTY, ChannelEnum.NETWORK.getModule(),
                    ChannelEnum.NETWORK.getChannel(), only);
            log.info("broadCastNetWork, rectifyCountdownEra : {}", moduleMessage);
            Collections.broadCastMessage(moduleMessage);
        }
    }

    /**
     * 频率 30 s
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void broadCastNetWork() {
        try {
            NetworkOverviewPO only = networkOverviewService.getOnly();
            if (Objects.nonNull(only)) {
                try {
                    NetworkOverviewPO lastOverview = chainSao.queryNetworkOverview();
                    ChainUtils.fillLastOverview(lastOverview, only);
                    ChainUtils.rectifyCountdownEra(only);
                    compositeService.refreshLastBlockToOverview(only, null);
                } catch (Exception e) {

                }
                ModuleMessage moduleMessage = WebSocketUtils.buildModuleMessage(
                        StringUtils.EMPTY, ChannelEnum.NETWORK.getModule(),
                        ChannelEnum.NETWORK.getChannel(), only);
                log.info("broadCastNetWork, moduleMessage : {}", moduleMessage);
                Collections.broadCastMessage(moduleMessage);
            }
        } catch (Exception e) {
            log.error("broadCastNetWork, 失败", e);
        }
    }

    /**
     * 频率 30 s
     */
    @Scheduled(cron = "0/6 * * * * ?")
    public void refreshTenBlock() {
        try {
            List<BlockVO> blockVOS = chainSao.queryLastBlockPage(MathUtils.TEN);
            log.info("refreshTenBlock, publishRefreshBlock");
            if (!CollectionUtils.isEmpty(blockVOS)) {
                ModuleMessage moduleMessage = WebSocketUtils.buildModuleMessage(
                        StringUtils.EMPTY, ChannelEnum.LAST_BLOCKS.getModule(),
                        ChannelEnum.LAST_BLOCKS.getChannel(), blockVOS);
//                log.info("refreshTenBlock, moduleMessage : {}", moduleMessage);
                Collections.broadCastMessage(moduleMessage);
                try {
                    NetworkOverviewPO only = networkOverviewService.getOnly();
                    compositeService.refreshLastBlockToOverview(only, blockVOS);
                    if (ChainUtils.syncPassTenSecond(Constants.SYNC_BLOCK_BEGIN_TIME)) {
                        RefreshBlockEvent refreshBlockEvent = RefreshBlockEvent.builder().blocks(blockVOS).build();
                        publisher.publishRefreshBlock(refreshBlockEvent);
                    } else {
                        log.info("refreshTenBlock, publishRefreshBlock : 时间未到");
                    }
                } catch (Exception e) {
                }
            }else {
                log.info("refreshTenBlock, publishRefreshBlock is empty");
            }
        } catch (Exception e) {
            log.error("refreshTenBlock, 失败", e);
        }
    }

}
