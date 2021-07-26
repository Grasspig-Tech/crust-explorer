package crust.explorer.controller;

import crust.explorer.pojo.Result;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.RewardSlashVO;
import crust.explorer.service.RewardSlashService;
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

import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/rewardSlashes")
@Api(tags = "奖励惩罚接口")
public class RewardSlashController {

    @Autowired
    RewardSlashService rewardSlashService;
    @Autowired
    RedisUtils redisUtils;

    //    @ApiOperation(value = "根据块高查奖励惩罚列表分页接口")
//    @GetMapping("/page/{blockNum}")
    public Result<PageVo<RewardSlashVO>> listPage(@PathVariable Integer blockNum, BaseQo qo) {
        PageVo<RewardSlashVO> events = rewardSlashService.listPage(blockNum, qo);
        return Result.success(events);
    }

    @ApiOperation(value = "根据验证人账户hash地址查奖惩列表分页接口")
    @GetMapping("/page")
    @ApiImplicitParam(name = "address", value = "验证人账户地址", paramType = "query", dataType = "String", example = "5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW")
    public Result<PageVo<RewardSlashVO>> listPageByValidateAddress(String address, BaseQo qo) {
        PageVo<RewardSlashVO> rewardSlashes = rewardSlashService.listPageByValidateAddress(address, qo);
        if (Objects.nonNull(rewardSlashes) && !CollectionUtils.isEmpty(rewardSlashes.getRecords())) {
            rewardSlashes.getRecords().forEach(rewardSlash ->
                    rewardSlash.setAmountTxt(rewardSlash.getAmount().stripTrailingZeros().toPlainString())
            );
        }
        return Result.success(rewardSlashes);
    }

    //    @ApiOperation(value = "奖励惩罚详情接口")//实际是交易详情接口
//    @GetMapping("/info/{blockNum}")
    public Result<RewardSlashVO> get(@PathVariable Integer blockNum, String eventIndex) {
        int tableNo = TableUtils.getTableNo(blockNum);
        RewardSlashVO event = rewardSlashService.getInfo(tableNo, blockNum, eventIndex);
        return Result.success(event);
    }
}
