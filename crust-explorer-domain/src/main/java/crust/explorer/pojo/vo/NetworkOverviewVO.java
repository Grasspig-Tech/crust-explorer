package crust.explorer.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ApiModel("信息概览出参")
public class NetworkOverviewVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @ApiModelProperty(value = "id (前端不用)", hidden = true)
    private Long id;
    /**
     * 总存储量
     */
    @ApiModelProperty("总存储量")
    private String totalStorage;
    /**
     * 总发行量
     */
    @ApiModelProperty("总发行量")
    private String totalCirculation;
    /**
     * 近24h总产出
     */
    @ApiModelProperty("近24h总产出")
    private String totalOutputLast24;
    /**
     * 区块高度
     */
    @ApiModelProperty("区块高度")
    private String blockHeight;
    /**
     * 已确认块高
     */
    @ApiModelProperty("已确认块高")
    private String blockHeightConfirmed;
    /**
     * 最后出块时间
     */
    @ApiModelProperty("最后出块时间")
    private String blockLastTime;
    /**
     * 最低质押量
     */
    @ApiModelProperty("最低质押量")
    private String pledgeMinimum;
    /**
     * 平均质押量
     */
    @ApiModelProperty("平均质押量")
    private String pledgeAvg;
    /**
     * 每T质押量
     */
    @ApiModelProperty("每T质押量")
    private String pledgePer;
    /**
     * 有效质押总量
     */
    @ApiModelProperty("有效质押总量")
    private String pledgeTotalActive;
    /**
     * 可质押总量
     */
    @ApiModelProperty("可质押总量")
    private String pledgeAbleNum;
    /**
     * 纪元：760
     */
    @JsonIgnore
    @ApiModelProperty(value = "纪元：760 (前端不用)", hidden = true)
    private Integer era;
    /**
     * 纪元起始时间:1625120364
     */
    @JsonIgnore
    @ApiModelProperty(value = "纪元起始时间:1625120364 (前端不用)", hidden = true)
    private Integer eraStartTimestamp;
    /**
     * Era倒计时
     */
    @ApiModelProperty("Era倒计时")
    private String countdownEra;
    /**
     * session倒计时
     */
    @ApiModelProperty("session倒计时")
    private String countdownSession;
    /**
     * 流通率
     */
    @JsonIgnore
    @ApiModelProperty(value = "流通率 (待定)", hidden = true)
    private String rateFlow;
    /**
     * 通胀率
     */
    @JsonIgnore
    @ApiModelProperty(value = "通胀率 (待定)", hidden = true)
    private String rateInflation;
    /**
     * 当前手续费
     */
    @JsonIgnore
    @ApiModelProperty(value = "当前手续费 (待定)", hidden = true)
    private String baseFee;
    /**
     * 持有账号数
     */
    @JsonIgnore
    @ApiModelProperty(value = "持有账号数 (待定)", hidden = true)
    private Integer accountHold;
    /**
     * 担保人数
     */
    @ApiModelProperty("担保人数")
    private Integer numberGuarantee;
    /**
     * 验证人数
     */
    @ApiModelProperty("验证人数")
    private Integer numberValidator;
    /**
     * 转账次数
     */
    @ApiModelProperty("转账次数")
    private Integer numberTransfer;
    /**
     * 交易次数
     */
    @ApiModelProperty("交易次数")
    private Integer numberTrade;
    /**
     * 有效否：1-生效；0-失效
     */
    @JsonIgnore
    @ApiModelProperty(value = "有效否：1-生效；0-失效", hidden = true)
    private Integer status;
    /**
     * 创建时间
     */
    @JsonIgnore
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date timeCreated;
    /**
     * 修改时间
     */
    @JsonIgnore
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Date timeUpdated;

}
