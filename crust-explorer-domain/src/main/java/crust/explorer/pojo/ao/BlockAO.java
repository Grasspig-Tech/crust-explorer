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
@ApiModel("查区块入参")
public class BlockAO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("区块号")
    private Integer blockNum;

    @ApiModelProperty("区块hash")
    private String hash;
}
