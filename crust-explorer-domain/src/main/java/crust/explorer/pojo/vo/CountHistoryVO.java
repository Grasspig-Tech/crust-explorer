package crust.explorer.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("历史记录出参")
public class CountHistoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("时间")
    private Date time;
    @ApiModelProperty("时间字符串")
    private String timeStr;
    @ApiModelProperty("次数")
    private Integer totalTimes;
    @ApiModelProperty("转账金额")
    private String amount;
}
