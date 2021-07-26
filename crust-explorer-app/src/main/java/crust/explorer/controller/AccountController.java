package crust.explorer.controller;

import crust.explorer.enums.hint.ValidateEnum;
import crust.explorer.pojo.Result;
import crust.explorer.pojo.vo.AccountVO;
import crust.explorer.service.AccountService;
import crust.explorer.util.Assert;
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
@RequestMapping("/account")
@Api(tags = "账户接口")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/info/{hash}")
    @ApiOperation(value = "获取账户详情")
    @ApiImplicitParam(name = "hash", value = "账户地址", paramType = "path", required = true, dataType = "String", example = "5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW")
    public Result<AccountVO> getInfo(@PathVariable String hash) {
        Assert.isNotBlank(hash, ValidateEnum.PARAM_IS_ERROR);
        AccountVO accountVO = accountService.getAccountByHash(hash);
        if(Objects.nonNull(accountVO)){
            accountVO.setBalanceTxt(accountVO.getBalance().stripTrailingZeros().toPlainString());
            accountVO.setBalanceLockTxt(accountVO.getBalanceLock().stripTrailingZeros().toPlainString());
            accountVO.setBondedTxt(accountVO.getBonded().stripTrailingZeros().toPlainString());
            accountVO.setReservedTxt(accountVO.getReserved().stripTrailingZeros().toPlainString());
            accountVO.setDemocracyLockTxt(accountVO.getDemocracyLock().stripTrailingZeros().toPlainString());
            accountVO.setElectionLockTxt(accountVO.getElectionLock().stripTrailingZeros().toPlainString());
            accountVO.setUnbondingTxt(accountVO.getUnbonding().stripTrailingZeros().toPlainString());
        }
        return Result.success(accountVO);
    }

}
