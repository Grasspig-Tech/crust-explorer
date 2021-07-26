package crust.explorer.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import crust.explorer.pojo.Result;
import crust.explorer.pojo.copier.BlockCopier;
import crust.explorer.pojo.po.NetworkOverviewPO;
import crust.explorer.pojo.vo.NetworkOverviewVO;
import crust.explorer.service.NetworkOverviewService;
import crust.explorer.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/network")
@Api(value = "信息概览", tags = "信息概览")
public class NetworkOverviewController {

    @Autowired
    NetworkOverviewService networkOverviewService;

    @GetMapping("/overview")
    @ApiOperation(value = "加载首页第一次获取信息概览，以后取websocket")
    public Result<NetworkOverviewVO> getNetworkOverview() {
        NetworkOverviewPO only = networkOverviewService.getOnly();
        return Result.success(BlockCopier.INSTANCE.toNetworkOverviewVO(only));
    }


}
