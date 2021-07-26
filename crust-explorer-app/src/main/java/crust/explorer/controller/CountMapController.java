package crust.explorer.controller;

import crust.explorer.enums.CountMapEnum;
import crust.explorer.enums.hint.ValidateEnum;
import crust.explorer.pojo.Result;
import crust.explorer.pojo.vo.WorkPledgeMapVO;
import crust.explorer.service.BondedPledgeService;
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
@RequestMapping("/maps")
@Api(tags = "饼图接口")
public class CountMapController {

    @Autowired
    BondedPledgeService bondedPledgeService;

    @GetMapping("/{code}")
    @ApiOperation(value = "获取饼图")
    @ApiImplicitParam(name = "code", value = "1-有效质押分布图;2-有效算分布图", required = true, paramType = "path", dataType = "int", example = "1")
    public Result<List<WorkPledgeMapVO>> getInfo(@PathVariable Integer code) {
        CountMapEnum countEnum = CountMapEnum.getEnum(code);
        if (Objects.isNull(countEnum)) {
            Result.failure(ValidateEnum.COUNT_ENUM_ERROR);
        }
        List<WorkPledgeMapVO> workPledgeMap = bondedPledgeService.getWorkPledgeMap(countEnum);
        return Result.success(workPledgeMap);
    }
}
