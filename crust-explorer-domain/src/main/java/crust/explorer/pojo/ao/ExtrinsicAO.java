package crust.explorer.pojo.ao;

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
@ApiModel("查询交易入参")
public class ExtrinsicAO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("交易hash(extrinsicHash和extrinsicIndex必传一个字段)")
    private String extrinsicHash;
    @ApiModelProperty("交易index:2242969-2(extrinsicHash和extrinsicIndex必传一个字段)")
    private String extrinsicIndex;
    @ApiModelProperty("事件index:2242969-1")
    private String eventIndex;

}
