package crust.explorer.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("提名人出参")
public class NominatorVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Long id;
    /**
     * 纪元：760
     */
    private Integer era;
    /**
     * 排名：1
     */
    @ApiModelProperty("排名：1")
    private Integer nominatorRank;
    /**
     * 投票冻结CRU：117000000000000
     */
    @ApiModelProperty("投票冻结CRU：117000000000000")
    private BigDecimal bonded;
    /**
     * 投票冻结CRU：117000000000000
     */
    @ApiModelProperty("投票冻结CRU：前端显示用")
    private String bondedTxt;
    /**
     * 提名人账户地址
     */
    @ApiModelProperty("提名人账户地址")
    private String nominatorAddress;
    /**
     * 验证人账户地址
     */
    @ApiModelProperty("验证人账户地址")
    private String validatorAddress;
    /**
     * 提名人账户json
     */
    @ApiModelProperty("提名人账户json")
    private String accountDisplay;
    /**
     * 份额（bonded/总数 * 100%
     */
    @ApiModelProperty("份额（bonded/总数 * 100%)")
    private BigDecimal quotient;
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
