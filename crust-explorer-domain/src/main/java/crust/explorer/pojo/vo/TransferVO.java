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
@ApiModel("转账出参")
public class TransferVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Long id;
    /**
     * 数量:0.00018004716
     */
    @ApiModelProperty("数量:0.00018004716")
    private BigDecimal amount;
    /**
     * 数量:0.00018004716
     */
    @ApiModelProperty("数量:前端显示用")
    private String amountTxt;
    /**
     * 币种
     */
    @ApiModelProperty("币种")
    private String assetSymbol;
    /**
     * 事件index:2243376-1
     */
    @ApiModelProperty("事件index:2243376-1")
    private String eventIndex;
    /**
     * 事件sort:22433760001
     */
    @ApiModelProperty("事件sort:22433760001")
    private String eventSort;
    /**
     * 交易index:2243376-2
     */
    @ApiModelProperty("交易index:2243376-2")
    private String extrinsicIndex;
    /**
     * 区块号
     */
    @ApiModelProperty("区块号")
    private Integer blockNum;
    /**
     * 区块打包时间
     */
    @ApiModelProperty("区块打包时间")
    private Integer blockTimestamp;
    /**
     * 操作:balances
     */
    @ApiModelProperty("操作:balances")
    private String module;
    /**
     * hash
     */
    @ApiModelProperty("hash")
    private String hash;
    /**
     * from
     */
    @ApiModelProperty("from")
    private String from;
    /**
     * to
     */
    @ApiModelProperty("to")
    private String to;
    /**
     * fee
     */
    @ApiModelProperty("fee")
    private BigDecimal fee;
    /**
     * nonce
     */
    @ApiModelProperty("nonce")
    private Integer nonce;
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
