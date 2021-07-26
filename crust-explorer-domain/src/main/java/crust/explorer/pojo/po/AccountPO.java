package crust.explorer.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("ce_account")
public class AccountPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 账户类型:1-（存储）账号,2-控制账户
     */
    private Integer accountType;
    /**
     * 账户hash地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW
     */
    private String address;
    /**
     * 账户邮箱：Crustnetwork@gmail.com
     */
    private String email;
    /**
     * twitter：@CrustNetwork
     */
    private String twitter;
    /**
     * web：https://crust.network/
     */
    private String web;
    /**
     * 账户显示（昵称）：德坤数矿(DKSK)星城
     */
    private String display;
    /**
     * 账户json:{account_index: ""; address: "5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW";…}
     */
    private String accountDisplay;
    /**
     * 不为null就是身份等级[{index: 1; judgement: "Reasonable"}]
     */
    private String judgements;
    /**
     * 余额:8499.134625110269
     */
    private BigDecimal balance;
    /**
     * 已锁定余额:8282.301101002825
     */
    private BigDecimal balanceLock;
    /**
     * 已冻结余额:8282301101002825
     */
    private BigDecimal bonded;
    /**
     * 保留余额:5000000000000（可转账=余额-保留余额-已锁定余额）
     */
    private BigDecimal reserved;
    /**
     * 民主锁定:0
     */
    private BigDecimal democracyLock;
    /**
     * 选举锁定:0
     */
    private BigDecimal electionLock;
    /**
     * 解冻中:0
     */
    private BigDecimal unbonding;
    /**
     * 是否议会成员:1-Y,0-N
     */
    private Integer isCouncilMember;
    /**
     * 是否evm合约:1-Y,0-N
     */
    private Integer isEvmContract;
    /**
     * 是否身份注册商:1-Y,0-N
     */
    private Integer isRegistrar;
    /**
     * 是否技术委员会成员:1-Y,0-N
     */
    private Integer isTechcommMember;
    /**
     * 法律身份:Withdraw to Ethereum ERC-20
     */
    private String legal;
    /**
     * 随机数:171
     */
    private Integer nonce;
    /**
     * 创建时间
     */
    private Date timeCreated;
    /**
     * 修改时间
     */
    private Date timeUpdated;

}
