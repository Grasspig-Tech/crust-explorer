package crust.explorer.controller;

import crust.explorer.pojo.Result;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.ValidatorBondedPledgeVO;
import crust.explorer.service.BondedPledgeService;
import crust.explorer.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/pledges")
@Api(tags = "质押接口")
public class PledgeController {

    @Autowired
    BondedPledgeService bondedPledgeService;

    @Autowired
    MemberService memberService;

    @ApiOperation(value = "质押额度列表分页接口")
    @GetMapping("/quota")
    public Result<PageVo<ValidatorBondedPledgeVO>> listPledgeQuotaPage(BaseQo qo) {
        PageVo<ValidatorBondedPledgeVO> page = memberService.getPledgeQuotas(qo);
        return Result.success(page);
    }

    @ApiOperation(value = "有效质押列表分页接口")
    @GetMapping("/active")
    public Result<PageVo<ValidatorBondedPledgeVO>> listActivePledgePage(BaseQo qo) {
        PageVo<ValidatorBondedPledgeVO> page = memberService.getActivePledges(qo);
        return Result.success(page);
    }
}
