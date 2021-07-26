package crust.explorer.job;

import crust.explorer.service.BondedPledgeService;
import crust.explorer.service.EraStatService;
import crust.explorer.service.impl.ChainServiceImpl;
import crust.explorer.service.impl.CompositeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
public class EraJob {

    @Autowired
    BondedPledgeService bondedPledgeService;

    @Autowired
    EraStatService eraStatService;

    @Autowired
    ChainServiceImpl chainService;

    @Autowired
    CompositeServiceImpl compositeService;

    /**
     * 1小时
     */
    @Scheduled(cron = "0 0/10 * * * ?")
//    @Scheduled(cron = "0/5 * * * * ?")
//    @Scheduled(cron = "0 0/5 * * * ?")
    public void refreshEra() {
        try {
            long begin = System.currentTimeMillis();
            eraStatService.refreshEra();
            long end = System.currentTimeMillis();
            log.info("EraJob.refreshEra 耗时：{}", (end - begin));
        } catch (Exception e) {
            log.error("EraJob.refreshEra 出错：", e);
        }
    }

    @Scheduled(cron = "0 2 0/1 * * ?")
    public void countPledgeMap() {
        try {
            long begin = System.currentTimeMillis();
            bondedPledgeService.countWorkCapabilityAndPledgeMap();
            long end = System.currentTimeMillis();
            log.info("EraJob.countPledgeMap 耗时：{}", (end - begin));
        } catch (Exception e) {
            log.error("EraJob.countPledgeMap 出错：", e);
        }
    }

    @Scheduled(cron = "0 2 0/1 * * ?")
    public void countNetworkOverview() {
        try {
            long begin = System.currentTimeMillis();
            chainService.countNetworkOverview();
            long end = System.currentTimeMillis();
            log.info("EraJob.countNetworkOverview 耗时：{}", (end - begin));
        } catch (Exception e) {
            log.error("EraJob.countNetworkOverview 出错：", e);
        }
    }

    @Scheduled(cron = "0 4 0/1 * * ?")
    public void countNetworkOverviewChain() {
        try {
            long begin = System.currentTimeMillis();
            compositeService.countNetworkOverviewChain();
            long end = System.currentTimeMillis();
            log.info("EraJob.countNetworkOverviewChain 耗时：{}", (end - begin));
        } catch (Exception e) {
            log.error("EraJob.countNetworkOverviewChain 出错：", e);
        }
    }


}

