package crust.explorer.controller;

import crust.explorer.enums.CountEnum;
import crust.explorer.enums.hint.ValidateEnum;
import crust.explorer.pojo.Result;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.CountHistoryVO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.TransferVO;
import crust.explorer.service.TransferService;
import crust.explorer.service.impl.CountServiceImpl;
import crust.explorer.util.RedisUtils;
import crust.explorer.util.TableUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/transfer")
@Api(tags = "转账接口")
public class TransferController {

    @Autowired
    TransferService transferService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    CountServiceImpl countService;

    @ApiOperation(value = "转账列表分页接口")
    @GetMapping("/page")
    @ApiImplicitParam(name = "address", value = "账户地址", paramType = "query", dataType = "String", example = "5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW")
    public Result<PageVo<TransferVO>> listPage(BaseQo qo, String address) {
        long begin = System.currentTimeMillis();
        List<Integer> tableNos = TableUtils.getTableNos();
        PageVo<TransferVO> transfers = transferService.listPage(tableNos, address, qo);
        if (Objects.nonNull(transfers) && !CollectionUtils.isEmpty(transfers.getRecords())) {
            transfers.getRecords().forEach(transfer ->
                    transfer.setAmountTxt(transfer.getAmount().stripTrailingZeros().toPlainString())
            );
        }
        long end = System.currentTimeMillis();
        log.info("转账列表分页接口 耗时：{}", (end - begin));
        return Result.success(transfers);
    }

    //    @ApiOperation(value = "根据块高查转账列表分页接口")
//    @GetMapping("/page/{blockNum}")
    public Result<PageVo<TransferVO>> listPage(@PathVariable Integer blockNum, BaseQo qo) {
        PageVo<TransferVO> events = transferService.listPage(blockNum, qo);
        return Result.success(events);
    }

    //    @ApiOperation(value = "转账详情接口")//实际是交易详情接口
//    @GetMapping("/info/{blockNum}")
    public Result<TransferVO> get(@PathVariable Integer blockNum, String eventIndex) {
        int tableNo = TableUtils.getTableNo(blockNum);
        TransferVO event = transferService.getInfo(tableNo, blockNum, eventIndex);
        return Result.success(event);
    }

    @ApiOperation(value = "转账走势图")
    @GetMapping("/trendLine/{key}")
    @ApiImplicitParam(name = "key", value = "[transfer_count_1h,transfer_count_6h,transfer_count_1d]取其一", required = true, paramType = "path", dataType = "String", example = "transfer_count_1h")
    public Result<List<CountHistoryVO>> get(@PathVariable String key) {
        CountEnum countEnum = CountEnum.getEnum(key);
        if (Objects.isNull(countEnum) || !"transfer".equals(countEnum.getType())) {
            Result.failure(ValidateEnum.COUNT_ENUM_ERROR);
        }
        long begin = System.currentTimeMillis();
        List<CountHistoryVO> trendLine = countService.getTrendLine(countEnum);
        long end = System.currentTimeMillis();
        log.info("getTransferTrendLine 耗时：{}", (end - begin));
        return Result.success(trendLine);
    }
}
