package crust.explorer.ws;

import crust.explorer.enums.ModuleEnum;
import crust.explorer.util.HttpClientHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

@Slf4j
//@Configuration
//@EnableScheduling
public class PushMessageTestTask {

    @Async("wsExecutor")
    @Scheduled(cron = "0/1 * * * * ?")
    public void broadCastOne() {
        log.info("========= broadCastOne start =========");
//        Collections.broadCastMessage(ModuleEnum.ONE_MODULE.getModule(), "refreshPerson|ONE_MODULE" + new Date().getTime());
        log.info("========= broadCastOne end =========");
        testRunLog();
    }

    @Scheduled(cron = "0/1 * * * * ?")
    public void broadCastTwo() {
        log.info("========= broadCastTwo start =========");
//        Collections.broadCastMessage(ModuleEnum.TWO_MODULE.getModule(), "refreshPerson|TWO_MODULE" + new Date().getTime());
        log.info("========= broadCastTwo end =========");
        testRunLog();
    }

    private void testRunLog() {
        for (int i = 0; i < 10; i++) {
            log.info("========= testRunLog info =========");
            log.error("========= testRunLog error =========");
            log.debug("========= testRunLog debug =========");
            log.warn("========= testRunLog warn =========");
        }
    }

    @Async("wsExecutor")
    @Scheduled(cron = "0/1 * * * * ?")
    public void testHttpScheduled() {
        testHttp();
    }

    private void testHttp() {
        long start = System.currentTimeMillis();
        try {
            String s = HttpClientHandler.get("https://www.baidu.com", null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("========= testHttp 耗时 : {} =========", (end - start));
    }
}
