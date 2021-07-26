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
@ApiModel("纪元出参")
public class EraStatVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Long id;
    /**
     * 纪元：760
     */
    @ApiModelProperty("纪元：760")
    private Integer era;
    /**
     * 起始块打包时间:1625120364
     */
    @ApiModelProperty("起始块打包时间:1625120364")
    private Integer startBlockTimestamp;
    /**
     * 结束块打包时间:1625120364
     */
    @ApiModelProperty("结束块打包时间:1625120364")
    private Integer endBlockTimestamp;
    /**
     * 状态：1-已结束；0-未结束
     */
    @ApiModelProperty("状态：1-已结束；0-未结束")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date timeCreated;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date timeUpdated;

}
