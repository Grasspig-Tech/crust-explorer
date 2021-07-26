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
@ApiModel("综合查询入参")
public class CompositeAO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("区块，验证人，交易hash地址")
    private String hash;

    @ApiModelProperty("区块高度或者交易ID")
    private String blockNum;

}
