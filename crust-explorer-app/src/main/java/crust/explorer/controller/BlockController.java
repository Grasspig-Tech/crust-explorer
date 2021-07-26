package crust.explorer.controller;

import crust.explorer.enums.hint.LogicEnum;
import crust.explorer.exception.BusinessException;
import crust.explorer.pojo.Result;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.service.BlockService;
import crust.explorer.service.impl.CompositeServiceImpl;
import crust.explorer.util.RedisKeys;
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
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/blocks")
@Api(tags = "区块接口")
public class BlockController {

    @Autowired
    BlockService blockService;
    @Autowired
    CompositeServiceImpl compositeService;
    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/page")
    @ApiOperation(value = "区块列表分页接口")
    public Result<PageVo<BlockVO>> listPage(BaseQo qo) {
        long begin = System.currentTimeMillis();
        List<Integer> tableNos = TableUtils.getTableNos();
        PageVo<BlockVO> blocks = blockService.listPage(tableNos, qo);
        long end = System.currentTimeMillis();
        log.info("区块列表分页接口 耗时：{}", (end - begin));
        return Result.success(blocks);
    }

    @ApiOperation(value = "根据区块高度查区块详情接口")
    @GetMapping("/{blockNum}")
    @ApiImplicitParam(name = "blockNum", value = "区块高度", required = true, paramType = "path", dataType = "int", example = "2347170")
    public Result<BlockVO> getByBlockNum(@PathVariable Integer blockNum) {
        Integer blockHeight = redisUtils.get(RedisKeys.BLOCK_NUM + blockNum, Integer.class);
        if (Integer.valueOf(-1).equals(blockHeight)) {
            throw new BusinessException(LogicEnum.DATA_IS_NULL);
        }
        BlockVO block = compositeService.getByBlockNum(blockNum);
        if (Objects.isNull(block)) {
            throw new BusinessException(LogicEnum.DATA_IS_NULL);
        }
        return Result.success(block);
    }

    @ApiOperation(value = "根据区块hash查区块详情接口")
    @GetMapping("/info/{hash}")
    @ApiImplicitParam(name = "hash", value = "区块hash", required = true, paramType = "path", dataType = "String", example = "0x1f380ac63dc729c104b64572ea5434cb06a6db09adc0ce512c24e78916e18de3")
    public Result<BlockVO> getBlockByHash(@PathVariable String hash) {
        Integer blockNum = redisUtils.get(RedisKeys.BLOCK_HASH + hash, Integer.class);
        if (Integer.valueOf(-1).equals(blockNum)) {
            throw new BusinessException(LogicEnum.DATA_IS_NULL);
        }
        if (Objects.isNull(blockNum)) {
            BlockVO block = compositeService.getBlockByHash(hash);
            if (Objects.isNull(block)) {
                throw new BusinessException(LogicEnum.DATA_IS_NULL);
            }
            return Result.success(block);
        }
        BlockVO block = compositeService.getByBlockNum(blockNum);
        if (Objects.isNull(block)) {
            throw new BusinessException(LogicEnum.DATA_IS_NULL);
        }
        return Result.success(block);
    }


}
