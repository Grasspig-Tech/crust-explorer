package crust.explorer.controller;

import crust.explorer.pojo.Result;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.NominatorVO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.service.NominatorService;
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

@Slf4j
@RestController
@RequestMapping("/nominators")
@Api(tags = "提名人接口")
public class NominatorController {

    @Autowired
    NominatorService nominatorService;

    @ApiOperation(value = "根据验证人账户hash地址查提名人列表分页接口")
    @GetMapping("/{address}")
    @ApiImplicitParam(name = "address", value = "验证人账户地址", required = true, paramType = "path", dataType = "String", example = "5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW")
    public Result<PageVo<NominatorVO>> listPage(@PathVariable String address, BaseQo qo) {
        PageVo<NominatorVO> nominators = nominatorService.listPageByValidateAddress(address, qo);
        if (Objects.nonNull(nominators) && !CollectionUtils.isEmpty(nominators.getRecords())) {
            nominators.getRecords().forEach(nominatorVO ->
                    nominatorVO.setBondedTxt(nominatorVO.getBonded().stripTrailingZeros().toPlainString())
            );
        }
        return Result.success(nominators);
    }

}
