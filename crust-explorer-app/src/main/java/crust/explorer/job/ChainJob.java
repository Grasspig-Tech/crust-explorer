package crust.explorer.job;

import crust.explorer.enums.CountEnum;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.runner.Publisher;
import crust.explorer.sao.ChainSaoImpl;
import crust.explorer.service.SysSyncLogService;
import crust.explorer.service.impl.ChainServiceImpl;
import crust.explorer.service.impl.CountServiceImpl;
import crust.explorer.util.TableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class ChainJob {

    @Autowired
    ChainServiceImpl chainService;

    @Autowired
    CountServiceImpl countService;

    @Autowired
    ChainSaoImpl chainSao;

    @Autowired
    SysSyncLogService sysSyncLogService;

    @Autowired
    Publisher publisher;

    @Scheduled(cron = "0 0 12 ? * 1")
    public void init() {
        BlockVO block = chainSao.queryLastBlock();
        chainService.initTable(Boolean.TRUE, block);
        sysSyncLogService.initSyncToLog(Boolean.TRUE, block);
    }

    @Scheduled(cron = "0 0 0/2 * * ?")
    public void extrinsicTrendLine_1h() {
        try {
            long begin = System.currentTimeMillis();
            List<Integer> tableNos = TableUtils.getTableNos();
            countService.refreshTrendLine(CountEnum.EXTRINSIC_COUNT_1H, tableNos);
            long end = System.currentTimeMillis();
            log.info("extrinsicTrendLine_1h 耗时：{}", (end - begin));
        } catch (Exception e) {
            log.error("extrinsicTrendLine_1h 失败：", e);
        }
    }

    @Scheduled(cron = "0 20 0/2 * * ?")
    public void extrinsicTrendLine_6h() {
        try {
            long begin = System.currentTimeMillis();
            List<Integer> tableNos = TableUtils.getTableNos();
            countService.refreshTrendLine(CountEnum.EXTRINSIC_COUNT_6H, tableNos);
            long end = System.currentTimeMillis();
            log.info("extrinsicTrendLine_6h 耗时：{}", (end - begin));
        } catch (Exception e) {
            log.error("extrinsicTrendLine_6h 失败：", e);
        }
    }

    @Scheduled(cron = "0 40 0/2 * * ?")
    public void extrinsicTrendLine_1d() {
        try {
            long begin = System.currentTimeMillis();
            List<Integer> tableNos = TableUtils.getTableNos();
            countService.refreshTrendLine(CountEnum.EXTRINSIC_COUNT_1D, tableNos);
            long end = System.currentTimeMillis();
            log.info("extrinsicTrendLine_1d 耗时：{}", (end - begin));
        } catch (Exception e) {
            log.error("extrinsicTrendLine_1d 失败：", e);
        }
    }

    @Scheduled(cron = "0 05 0/2 * * ?")
    public void transferTrendLine_1h() {
        try {
            long begin = System.currentTimeMillis();
            List<Integer> tableNos = TableUtils.getTableNos();
            countService.refreshTrendLine(CountEnum.TRANSFER_COUNT_1H, tableNos);
            long end = System.currentTimeMillis();
            log.info("transferTrendLine_1h 耗时：{}", (end - begin));
        } catch (Exception e) {
            log.error("transferTrendLine_1h 失败：", e);
        }
    }

    @Scheduled(cron = "0 25 0/2 * * ?")
    public void transferTrendLine_6h() {
        try {
            long begin = System.currentTimeMillis();
            List<Integer> tableNos = TableUtils.getTableNos();
            countService.refreshTrendLine(CountEnum.TRANSFER_COUNT_6H, tableNos);
            long end = System.currentTimeMillis();
            log.info("transferTrendLine_6h 耗时：{}", (end - begin));
        } catch (Exception e) {
            log.error("transferTrendLine_6h 失败：", e);
        }
    }

    @Scheduled(cron = "0 45 0/2 * * ?")
    public void transferTrendLine_1d() {
        try {
            long begin = System.currentTimeMillis();
            List<Integer> tableNos = TableUtils.getTableNos();
            countService.refreshTrendLine(CountEnum.TRANSFER_COUNT_1D, tableNos);
            long end = System.currentTimeMillis();
            log.info("transferTrendLine_1d 耗时：{}", (end - begin));
        } catch (Exception e) {
            log.error("transferTrendLine_1d 失败：", e);
        }
    }

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void removeLongAgoData() {
        try {
            publisher.publishRemoveLongAgoData();
        } catch (Exception e) {
            log.error("BlockJob.removeLongAgoData 时间未到");
        }
    }
}
