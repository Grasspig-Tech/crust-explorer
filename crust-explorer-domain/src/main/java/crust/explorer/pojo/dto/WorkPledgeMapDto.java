package crust.explorer.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("算力质押饼图dto")
public class WorkPledgeMapDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("账户hash地址")
    private String address;
    @ApiModelProperty("昵称")
    private String display;
    @ApiModelProperty("比例")
    private BigDecimal mapRatio;
    @ApiModelProperty("总量")
    private String total;
}
