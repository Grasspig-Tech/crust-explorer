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
@ApiModel("账户出参")
public class AccountVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Long id;
    /**
     * 1-validator,2-waiting,3-提名人,4-其他
     */
    @ApiModelProperty("1-validator,2-waiting,3-提名人,4-其他")
    private Integer role;
    /**
     * 账户类型:1-（存储）账号,2-控制账户
     */
    @ApiModelProperty("账户类型:1-（存储）账号,2-控制账户")
    private Integer accountType;
    /**
     * 账户hash地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW
     */
    @ApiModelProperty("账户hash地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW")
    private String address;
    /**
     * 账户邮箱：Crustnetwork@gmail.com
     */
    @ApiModelProperty("账户邮箱：Crustnetwork@gmail.com")
    private String email;
    /**
     * twitter：@CrustNetwork
     */
    @ApiModelProperty("twitter：@CrustNetwork")
    private String twitter;
    /**
     * web：https://crust.network/
     */
    @ApiModelProperty("web：https://crust.network/")
    private String web;
    /**
     * 账户显示（昵称）：德坤数矿(DKSK)星城
     */
    @ApiModelProperty("账户显示（昵称）：德坤数矿(DKSK)星城")
    private String display;
    /**
     * 账户json:{account_index: ""; address: "5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW";…}
     */
    @ApiModelProperty("账户json:{account_index: \"\"; address: \"5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW\";…}")
    private String accountDisplay;
    /**
     * 不为null就是身份等级[{index: 1; judgement: "Reasonable"}]
     */
    @ApiModelProperty("不为null就是身份等级[{index: 1; judgement: \"Reasonable\"}]")
    private String judgements;
    /**
     * 余额:8499.134625110269
     */
    @ApiModelProperty("余额:8499.134625110269")
    private BigDecimal balance;
    @ApiModelProperty("余额:前端用")
    private String balanceTxt;
    /**
     * 已锁定余额:8282.301101002825
     */
    @ApiModelProperty("已锁定余额:8282.301101002825")
    private BigDecimal balanceLock;
    @ApiModelProperty("已锁定余额:前端用")
    private String balanceLockTxt;
    /**
     * 已冻结余额:8282.301101002825
     */
    @ApiModelProperty("已冻结余额:8282.301101002825")
    private BigDecimal bonded;
    @ApiModelProperty("已冻结余额:前端用")
    private String bondedTxt;
    /**
     * 保留余额:5（可转账=余额-保留余额-已锁定余额）
     */
    @ApiModelProperty("保留余额:5（可转账=余额-保留余额-已锁定余额）")
    private BigDecimal reserved;
    @ApiModelProperty("保留余额:前端用")
    private String reservedTxt;
    /**
     * 民主锁定:0
     */
    @ApiModelProperty("民主锁定:0")
    private BigDecimal democracyLock;
    @ApiModelProperty("保留余额:前端用")
    private String democracyLockTxt;
    /**
     * 选举锁定:0
     */
    @ApiModelProperty("选举锁定:0")
    private BigDecimal electionLock;
    @ApiModelProperty("选举锁定:前端用")
    private String electionLockTxt;
    /**
     * 解冻中:0
     */
    @ApiModelProperty("解冻中:0")
    private BigDecimal unbonding;
    @ApiModelProperty("解冻中:前端用")
    private String unbondingTxt;
    /**
     * 是否议会成员:1-Y,0-N
     */
    @ApiModelProperty("是否议会成员:1-Y,0-N")
    private Integer isCouncilMember;
    /**
     * 是否evm合约:1-Y,0-N
     */
    @ApiModelProperty("是否evm合约:1-Y,0-N")
    private Integer isEvmContract;
    /**
     * 是否身份注册商:1-Y,0-N
     */
    @ApiModelProperty("是否身份注册商:1-Y,0-N")
    private Integer isRegistrar;
    /**
     * 是否技术委员会成员:1-Y,0-N
     */
    @ApiModelProperty("是否技术委员会成员:1-Y,0-N")
    private Integer isTechcommMember;
    /**
     * 法律身份:Withdraw to Ethereum ERC-20
     */
    @ApiModelProperty("法律身份:Withdraw to Ethereum ERC-20")
    private String legal;
    /**
     * 随机数:171
     */
    @ApiModelProperty("随机数:171")
    private Integer nonce;
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
