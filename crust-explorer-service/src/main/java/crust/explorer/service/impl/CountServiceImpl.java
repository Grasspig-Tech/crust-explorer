package crust.explorer.service.impl;

import com.google.gson.reflect.TypeToken;
import crust.explorer.enums.CountEnum;
import crust.explorer.mapper.*;
import crust.explorer.pojo.vo.CountHistoryVO;
import crust.explorer.service.SysSyncLogService;
import crust.explorer.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class CountServiceImpl {

    @Autowired
    BlockMapper blockMapper;
    @Autowired
    EventMapper eventMapper;
    @Autowired
    ExtrinsicMapper extrinsicMapper;
    @Autowired
    RewardSlashMapper rewardSlashMapper;
    @Autowired
    TransferMapper transferMapper;
    @Autowired
    SysSyncLogService sysSyncLogService;
    @Autowired
    RedisUtils redisUtils;

    public void refreshExtrinsicTrendLine() {
        List<Integer> tableNos = TableUtils.getTableNos();
        refreshTrendLine(CountEnum.EXTRINSIC_COUNT_1H, tableNos);
        refreshTrendLine(CountEnum.EXTRINSIC_COUNT_6H, tableNos);
        refreshTrendLine(CountEnum.EXTRINSIC_COUNT_1D, tableNos);
    }

    public List<CountHistoryVO> refreshTrendLine(CountEnum countEnum, List<Integer> tableNos) {
        List<CountHistoryVO> countHistory = new ArrayList<>();
        if (countEnum.equals(CountEnum.EXTRINSIC_COUNT_1H) || countEnum.equals(CountEnum.TRANSFER_COUNT_1H)) {
            countHistory = DateUtils.buildCountHistory1h();
        } else if (countEnum.equals(CountEnum.EXTRINSIC_COUNT_6H) || countEnum.equals(CountEnum.TRANSFER_COUNT_6H)) {
            countHistory = DateUtils.buildCountHistory6h();
        } else if (countEnum.equals(CountEnum.EXTRINSIC_COUNT_1D) || countEnum.equals(CountEnum.TRANSFER_COUNT_1D)) {
            countHistory = DateUtils.buildCountHistory1d();
        }
        countHistory.forEach(countHistoryVO -> {
            Calendar calendar = Calendar.getInstance();
            Date begin = countHistoryVO.getTime();
            calendar.setTime(begin);
            calendar.set(countEnum.getOffset(), calendar.get(countEnum.getOffset()) + countEnum.getRange());
            Date end = calendar.getTime();

            System.out.println(begin);
            System.out.println(end);
            System.out.println(begin.getTime());
            System.out.println(end.getTime());

            long beginTime = begin.getTime() / 1000;
            long endTime = end.getTime() / 1000;
            long beginQ = System.currentTimeMillis();
            if ("extrinsic".equals(countEnum.getType())) {
                Integer countExtrinsic = extrinsicMapper.getCountExtrinsic(tableNos, beginTime, endTime);
                countHistoryVO.setTotalTimes(countExtrinsic);
            } else {
                Integer countTransfer = transferMapper.getCountTransfer(tableNos, beginTime, endTime);
                BigDecimal amount = transferMapper.getSumTransferAmount(tableNos, beginTime, endTime);
                countHistoryVO.setTotalTimes(countTransfer);
                countHistoryVO.setAmount(Objects.nonNull(amount) ? amount.toPlainString() : "0.00");
            }
            long endQ = System.currentTimeMillis();
            log.info("refreshTrendLine key:{},耗时:{},timeRange:{}", countEnum.getRedisKey(), (endQ - beginQ), DateUtils.format(begin, DateUtils.DATE_TIME_PATTERN));
        });
        redisUtils.set(countEnum.getRedisKey(), countHistory);
        return countHistory;
    }

    public List<CountHistoryVO> getTrendLine(CountEnum countEnum) {
        String extrinsic = redisUtils.get(countEnum.getRedisKey());
        if (StringUtils.isNotBlank(extrinsic)) {
            List<CountHistoryVO> list = GsonUtils.getInstance().fromJson(extrinsic, new TypeToken<List<CountHistoryVO>>() {
            }.getType());
            if (!CollectionUtils.isEmpty(list)) {
                return list;
            }
        }
        List<Integer> tableNos = TableUtils.getTableNos();
        return this.refreshTrendLine(countEnum, tableNos);
    }

    public void refreshTransferTrendLine() {
        List<Integer> tableNos = TableUtils.getTableNos();
        refreshTrendLine(CountEnum.TRANSFER_COUNT_1H, tableNos);
        refreshTrendLine(CountEnum.TRANSFER_COUNT_6H, tableNos);
        refreshTrendLine(CountEnum.TRANSFER_COUNT_1D, tableNos);
    }

    public static void main(String[] args) {
//        buildCountHistory1h();
//        System.out.println("===========================================================================================");
//        buildCountHistory1d();
//        System.out.println("===========================================================================================");
//        buildCountHistory6h();
    }
}
