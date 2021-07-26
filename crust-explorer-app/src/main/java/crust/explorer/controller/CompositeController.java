package crust.explorer.controller;

import crust.explorer.enums.CompositeEnum;
import crust.explorer.enums.hint.LogicEnum;
import crust.explorer.exception.BusinessException;
import crust.explorer.pojo.Result;
import crust.explorer.pojo.vo.AccountVO;
import crust.explorer.pojo.vo.BlockVO;
import crust.explorer.pojo.vo.CompositeVO;
import crust.explorer.pojo.vo.ExtrinsicVO;
import crust.explorer.runner.Publisher;
import crust.explorer.service.AccountService;
import crust.explorer.service.impl.CompositeServiceImpl;
import crust.explorer.util.ChainUtils;
import crust.explorer.util.RedisKeys;
import crust.explorer.util.RedisUtils;
import crust.explorer.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/composite")
@Api(tags = "综合查询接口")
public class CompositeController {

    @Autowired
    CompositeServiceImpl compositeService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    Publisher publisher;
    @Autowired
    AccountService accountService;

    @ApiOperation("综合查询区块（交易，账号）详情")
    @PostMapping("/info")
    public Result<CompositeVO> getInfo(String queryParam) {
        if (StringUtils.isBlank(queryParam)) {
            throw new BusinessException(LogicEnum.DATA_IS_NULL);
        }
        CompositeVO compositeVO = redisUtils.get(RedisKeys.COMPOSITE_QUERY_VO + queryParam, CompositeVO.class);
        if (Objects.nonNull(compositeVO)) {
            return Result.success(compositeVO);
        }
        Integer compositeQuery = redisUtils.get(RedisKeys.COMPOSITE_QUERY + queryParam, Integer.class);
        if (Integer.valueOf(-1).equals(compositeQuery)) {
            throw new BusinessException(LogicEnum.DATA_IS_NULL);
        }
        if (ChainUtils.isHash(queryParam)) {
            compositeVO = getByHsh(queryParam);
        } else {
            compositeVO = getByBlockNum(queryParam);
        }
        if (Objects.isNull(compositeVO)) {
            redisUtils.set(RedisKeys.COMPOSITE_QUERY + queryParam, -1, RedisKeys.CACHE_2S);
            throw new BusinessException(LogicEnum.DATA_IS_NULL);
        } else {
            redisUtils.set(RedisKeys.COMPOSITE_QUERY_VO + queryParam, compositeVO, RedisKeys.CACHE_20S);
        }
        return Result.success(compositeVO);
    }

    private CompositeVO getByHsh(String hash) {
        CompositeVO compositeVO = CompositeVO.builder().build();
        BlockVO blockByHash = compositeService.getBlockByHash(hash);
        if (Objects.nonNull(blockByHash)) {
            compositeVO.setType(CompositeEnum.BLOCK.getType());
            compositeVO.setBlockVO(blockByHash);
            return compositeVO;
        }
        ExtrinsicVO extrinsicByHash = compositeService.getExtrinsicByExtrinsicHash(hash);
        if (Objects.nonNull(extrinsicByHash)) {
            compositeVO.setType(CompositeEnum.EXTRINSIC.getType());
            compositeVO.setExtrinsicVO(extrinsicByHash);
            return compositeVO;
        }
        AccountVO account = accountService.getAccountByHash(hash);
        if (Objects.nonNull(account)) {
            compositeVO.setType(CompositeEnum.ACCOUNT.getType());
            compositeVO.setAccountVO(account);
            return compositeVO;
        }
        return null;
    }

    private CompositeVO getByBlockNum(String queryParam) {
        CompositeVO compositeVO = CompositeVO.builder().build();
        if (ChainUtils.isBlock(queryParam)) {
            BlockVO byBlockNum = compositeService.getByBlockNum(Integer.valueOf(queryParam));
            if (Objects.nonNull(byBlockNum)) {
                compositeVO.setType(CompositeEnum.BLOCK.getType());
                compositeVO.setBlockVO(byBlockNum);
                return compositeVO;
            }
        } else {
            ExtrinsicVO extrinsicByBlockNum = compositeService.getExtrinsicByBlockNum(queryParam);
            if (Objects.nonNull(extrinsicByBlockNum)) {
                compositeVO.setType(CompositeEnum.EXTRINSIC.getType());
                compositeVO.setExtrinsicVO(extrinsicByBlockNum);
                return compositeVO;
            }
        }
        return null;
    }

}
