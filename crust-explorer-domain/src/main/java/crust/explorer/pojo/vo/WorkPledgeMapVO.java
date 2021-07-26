package crust.explorer.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("算力质押饼图出参")
public class WorkPledgeMapVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("账户hash地址")
    private String address;
    @ApiModelProperty("昵称")
    private String display;
    @ApiModelProperty("比例")
    private String mapRatio;
    @ApiModelProperty("总量")
    private String total;
}
