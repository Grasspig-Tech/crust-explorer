package crust.explorer.controller;

import crust.explorer.pojo.Result;
import crust.explorer.pojo.vo.EraStatCountVO;
import crust.explorer.service.EraStatCountService;
import crust.explorer.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/eraStat")
@Api(tags = "纪元接口")
public class EraStatController {

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    EraStatCountService eraStatCountService;

    @GetMapping("/list")
    @ApiOperation(value = "获取纪元列表")
    public Result<List<EraStatCountVO>> getEraStats(String validator) {
        List<EraStatCountVO> eraStats = eraStatCountService.getEraStatCounts(validator);
        return Result.success(eraStats);
    }


}
