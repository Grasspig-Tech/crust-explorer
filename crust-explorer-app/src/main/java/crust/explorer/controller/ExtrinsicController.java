package crust.explorer.controller;

import crust.explorer.enums.CountEnum;
import crust.explorer.enums.hint.LogicEnum;
import crust.explorer.enums.hint.ValidateEnum;
import crust.explorer.exception.BusinessException;
import crust.explorer.pojo.Result;
import crust.explorer.pojo.ao.ExtrinsicAO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.CountHistoryVO;
import crust.explorer.pojo.vo.ExtrinsicVO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.service.ExtrinsicService;
import crust.explorer.service.impl.CompositeServiceImpl;
import crust.explorer.service.impl.CountServiceImpl;
import crust.explorer.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/extrinsic")
@Api(tags = "交易接口")
public class ExtrinsicController {

    @Autowired
    ExtrinsicService extrinsicService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    CountServiceImpl countService;
    @Autowired
    CompositeServiceImpl compositeService;

    @ApiOperation(value = "交易列表分页接口")
    @GetMapping("/page")
    @ApiImplicitParam(name = "address", value = "账户地址", paramType = "query", dataType = "String", example = "5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW")
    public Result<PageVo<ExtrinsicVO>> listPage(BaseQo qo, String address) {
        long begin = System.currentTimeMillis();
        List<Integer> tableNos = TableUtils.getTableNos();
        PageVo<ExtrinsicVO> events = extrinsicService.listPage(tableNos, address, qo);
        long end = System.currentTimeMillis();
        log.info("交易列表分页接口 耗时：{}", (end - begin));
        return Result.success(events);
    }

    @ApiOperation(value = "根据块高查交易列表分页接口")
    @GetMapping("/page/{blockNum}")
    @ApiImplicitParam(name = "blockNum", value = "区块高度", required = true, paramType = "path", dataType = "int", example = "2347170")
    public Result<PageVo<ExtrinsicVO>> listPage(@PathVariable Integer blockNum, BaseQo qo) {
        PageVo<ExtrinsicVO> events = extrinsicService.listPage(blockNum, qo);
        return Result.success(events);
    }

    //    @ApiImplicitParams({
//            @ApiImplicitParam(name = "extrinsicIndex", value = "交易index", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "eventIndex", value = "选中的事件index", dataType = "String")
//    }
//    )
    @ApiOperation(value = "交易详情接口")
    @GetMapping("/info")
    public Result<ExtrinsicVO> get(ExtrinsicAO ao) {
        Assert.isNotNull(ao, ValidateEnum.PARAM_HAS_NULL);
        if (StringUtils.isBlank(ao.getExtrinsicHash()) && StringUtils.isBlank(ao.getExtrinsicIndex())) {
            throw new BusinessException(ValidateEnum.EXTRINSIC_HASH_AND_INDEX_IS_NULL);
        }
        Integer blockNum = ChainUtils.getBlockNum(ao.getExtrinsicIndex());
        if (Objects.nonNull(blockNum)) {
            Integer blockHeight = ChainUtils.getBlockNum(ao.getEventIndex());
            if (Objects.nonNull(blockHeight) && !blockHeight.equals(blockNum)) {
                throw new BusinessException(ValidateEnum.BLOCK_NUM_IS_ERROR);
            }
            blockNum = redisUtils.get(RedisKeys.EXTRINSIC_BLOCK_NUM + blockNum, Integer.class);
            if (Integer.valueOf(-1).equals(blockNum)) {
                throw new BusinessException(LogicEnum.DATA_IS_NULL);
            }
            ExtrinsicVO extrinsic = compositeService.getExtrinsicByBlockNum(ao.getExtrinsicIndex());
            fillCheckEvent(ao, extrinsic);
            return Result.success(extrinsic);
        } else {
            blockNum = redisUtils.get(RedisKeys.EXTRINSIC_HASH + ao.getExtrinsicHash(), Integer.class);
            if (Integer.valueOf(-1).equals(blockNum)) {
                throw new BusinessException(LogicEnum.DATA_IS_NULL);
            }
            ExtrinsicVO extrinsic = compositeService.getExtrinsicByExtrinsicHash(ao.getExtrinsicHash());
            fillCheckEvent(ao, extrinsic);
            return Result.success(extrinsic);
        }
    }

    private void fillCheckEvent(ExtrinsicAO ao, ExtrinsicVO extrinsic) {
        if (Objects.isNull(extrinsic)) {
            throw new BusinessException(LogicEnum.DATA_IS_NULL);
        }
        if (StringUtils.isNotBlank(ao.getEventIndex())) {
            extrinsic.setCheckEvent(ao.getEventIndex());
        }
    }

    @ApiOperation(value = "交易走势图")
    @GetMapping("/trendLine/{key}")
    @ApiImplicitParam(name = "key", value = "[extrinsic_count_1h,extrinsic_count_6h,extrinsic_count_1d]取其一", required = true, paramType = "path", dataType = "String", example = "extrinsic_count_1h")
    public Result<List<CountHistoryVO>> get(@PathVariable String key) {
        CountEnum countEnum = CountEnum.getEnum(key);
        if (Objects.isNull(countEnum) || !"extrinsic".equals(countEnum.getType())) {
            Result.failure(ValidateEnum.COUNT_ENUM_ERROR);
        }
        long begin = System.currentTimeMillis();
        List<CountHistoryVO> trendLine = countService.getTrendLine(countEnum);
        long end = System.currentTimeMillis();
        log.info("getExtrinsicTrendLine 耗时：{}", (end - begin));
        return Result.success(trendLine);
    }
}
