package crust.explorer.job;

import crust.explorer.service.BlockService;
import crust.explorer.util.ChainUtils;
import crust.explorer.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
@EnableScheduling
public class BlockJob {

    private AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    BlockService blockService;

    @Scheduled(cron = "0 0 0/12 * * ?")
    public void syncLastTenDay() {
        int i = counter.get();
        if (i > 0 || ChainUtils.syncPassTenSecond(Constants.SYNC_BLOCK_BEGIN_TIME)) {
            try {
                if (i < 3) {
                    counter.incrementAndGet();
                }
                blockService.syncLastTenDay();
            } catch (Exception e) {

            }
        } else {
            log.info("BlockJob.syncLastTenDay 时间未到");
        }
    }


}
