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
@ApiModel("奖励惩罚出参")
public class RewardSlashVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Long id;
    /**
     * 区块号
     */
    @ApiModelProperty("区块号")
    private Integer blockNum;
    /**
     * 区块打包时间
     */
    @ApiModelProperty("参数")
    private Integer blockTimestamp;
    /**
     * 事件id:WorksReportSuccess
     */
    @ApiModelProperty("事件id:WorksReportSuccess")
    private String eventId;
    /**
     * 事件idx:1
     */
    @ApiModelProperty("事件idx:1")
    private Integer eventIdx;
    /**
     * 事件index:2242969-1
     */
    @ApiModelProperty("事件index:2242969-1")
    private String eventIndex;
    /**
     * 事件sort:22281170001
     */
    @ApiModelProperty("事件sort:22281170001")
    private String eventSort;
    /**
     * 交易idx:1
     */
    @ApiModelProperty("交易idx:1")
    private String extrinsicIdx;
    /**
     * 交易hash
     */
    @ApiModelProperty("交易hash")
    private String extrinsicHash;
    /**
     * 验证人账户地址
     */
    @ApiModelProperty("验证人账户地址")
    private String validatorStash;
    @ApiModelProperty("奖惩者地址")
    private String accountId;
    /**
     * 操作id:staking
     */
    @ApiModelProperty("操作id:staking")
    private String moduleId;
    /**
     * 数量:9196868377476
     */
    @ApiModelProperty("数量:9196868377476")
    private BigDecimal amount;
    /**
     * 数量:9196868377476
     */
    @ApiModelProperty("数量:前端显示用")
    private String amountTxt;
    /**
     * 块状态：1-已确认，0-确认中
     */
    @ApiModelProperty("块状态：1-已确认，0-确认中")
    private Integer finalized;
    /**
     * 结果：1-成功，0-失败
     */
    @ApiModelProperty("结果：1-成功，0-失败")
    private Integer success;
    /**
     * 参数
     */
    @ApiModelProperty("参数")
    private String params;

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
