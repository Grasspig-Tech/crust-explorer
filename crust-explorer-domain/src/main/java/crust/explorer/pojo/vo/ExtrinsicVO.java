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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("交易出参")
public class ExtrinsicVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Long id;
    /**
     * lifetime
     */
    @ApiModelProperty("lifetime")
    private String lifetime;
    /**
     * 参数
     */
    @ApiModelProperty("参数")
    private String params;
    /**
     * 交易idx:2
     */
    @ApiModelProperty("交易idx:2")
    private Integer extrinsicIdx;
    /**
     * 交易hash
     */
    @ApiModelProperty("交易hash")
    private String extrinsicHash;
    /**
     * 交易index:2242969-2
     */
    @ApiModelProperty("交易index:2242969-2")
    private String extrinsicIndex;
    /**
     * 交易sort:22429690002
     */
    @ApiModelProperty("交易sort:22429690002")
    private String extrinsicSort;
    /**
     * 区块hash
     */
    @ApiModelProperty("区块hash")
    private String blockHash;
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
     * 操作（组件）:swork
     */
    @ApiModelProperty("操作（组件）:swork")
    private String callModule;
    /**
     * 操作方法（调用）:report_works
     */
    @ApiModelProperty("操作方法（调用）:report_works")
    private String callModuleFunction;
    /**
     * 发送账户账号地址
     */
    @ApiModelProperty("发送账户账号地址")
    private String accountId;
    /**
     * 发送账号json
     */
    @ApiModelProperty("发送账号json")
    private String accountDisplay;
    /**
     * 手续费:56642301
     */
    @ApiModelProperty("手续费:56642301")
    private BigDecimal fee;
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
     * 是否签名：1-是，0-否
     */
    @ApiModelProperty("是否签名：1-是，0-否")
    private Integer signed;
    /**
     * 随机数:3840
     */
    @ApiModelProperty("随机数:3840")
    private Integer nonce;
    /**
     * 签名
     */
    @ApiModelProperty("签名")
    private String signature;
    /**
     * 转账json
     */
    @ApiModelProperty("转账json")
    private String transfer;
    /**
     * 事件list
     */
    @ApiModelProperty("事件list")
    private List<EventVO> events;
    /**
     * 选中的事件
     */
    @ApiModelProperty("选中的事件")
    private String checkEvent;
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
