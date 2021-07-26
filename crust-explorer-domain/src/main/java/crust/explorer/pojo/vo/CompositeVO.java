package crust.explorer.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("综合查询出参")
public class CompositeVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("返回结果类型：1-区块，2-交易，3-账号详情")
    private Integer type;

    @ApiModelProperty("区块详情")
    private BlockVO blockVO;

    @ApiModelProperty("交易详情")
    private ExtrinsicVO extrinsicVO;

    @ApiModelProperty("账号详情")
    private AccountVO accountVO;

}
