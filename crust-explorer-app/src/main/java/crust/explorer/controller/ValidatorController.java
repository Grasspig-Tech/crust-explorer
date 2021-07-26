package crust.explorer.controller;

import crust.explorer.pojo.Result;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.ValidatorBondedPledgeVO;
import crust.explorer.pojo.vo.ValidatorDetailVO;
import crust.explorer.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/validators")
@Api(tags = "验证人接口")
public class ValidatorController {

    @Autowired
    MemberService memberService;

    @ApiOperation(value = "验证人或候选验证人列表分页接口")
    @GetMapping("/{role}")
    @ApiImplicitParam(name = "role", value = "角色:1-(验证人)validator,2-(候选验证人)waiting", required = true, paramType = "path", dataType = "int", example = "1")
    public Result<PageVo<ValidatorBondedPledgeVO>> listValidatorPage(@PathVariable Integer role, BaseQo qo) {
        PageVo<ValidatorBondedPledgeVO> pageVo = memberService.getValidators(role, qo);
        return Result.success(pageVo);
    }

    @ApiOperation(value = "根据验证人或候选验证人地址查其详情接口")
    @GetMapping("/info/{hash}")
    @ApiImplicitParam(name = "hash", value = "验证人或候选验证人账户（注意不是控制账户）hash地址", required = true, paramType = "path", dataType = "String", example = "5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW")
    public Result<ValidatorDetailVO> getInfo(@PathVariable String hash) {
        ValidatorDetailVO validatorDetail = memberService.getValidatorInfoByHah(hash);
        return Result.success(validatorDetail);
    }

}
