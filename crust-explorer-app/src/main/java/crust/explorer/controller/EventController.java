package crust.explorer.controller;

import crust.explorer.pojo.Result;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.EventVO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.service.EventService;
import crust.explorer.util.RedisUtils;
import crust.explorer.util.TableUtils;
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

@RestController
@Slf4j
@RequestMapping("/events")
@Api(tags = "事件接口")
public class EventController {

    @Autowired
    EventService eventService;
    @Autowired
    RedisUtils redisUtils;

    //    @ApiOperation(value = "事件列表分页接口")// 暂时原型不提供
//    @GetMapping("/page")
    public Result<PageVo<EventVO>> listPage(BaseQo qo) {
        List<Integer> tableNos = TableUtils.getTableNos();
        PageVo<EventVO> events = eventService.listPage(tableNos, qo);
        return Result.success(events);
    }

    @ApiOperation(value = "根据块高查事件列表分页接口")
    @GetMapping("/page/{blockNum}")
    @ApiImplicitParam(name = "blockNum", value = "区块高度", required = true, paramType = "path", dataType = "int", example = "2347170")
    public Result<PageVo<EventVO>> listPage(@PathVariable Integer blockNum, BaseQo qo) {
        PageVo<EventVO> events = eventService.listPage(blockNum, qo);
        return Result.success(events);
    }

    //    @ApiOperation(value = "事件详情接口")//实际是交易详情接口
//    @GetMapping("/info/{blockNum}")
    public Result<EventVO> get(@PathVariable Integer blockNum, String eventIndex) {
        int tableNo = TableUtils.getTableNo(blockNum);
        EventVO event = eventService.getInfo(tableNo, blockNum, eventIndex);
        return Result.success(event);
    }
}
