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
@ApiModel("(候选)验证人冻结质押出参")
public class ValidatorBondedPledgeVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 角色:1-validator,2-waiting
     */
    @ApiModelProperty("角色:1-(验证人)validator,2-(候选验证人)waiting")
    private Integer role;
//    /**
//     * 排名：1
//     */
//    @ApiModelProperty("排名：1")
//    private Integer memberRank;
    /**
     * 提名人数：24
     */
    @ApiModelProperty("提名人数：24")
    private Integer countNominators;
    /**
     * 时代指数：820
     */
    @ApiModelProperty("时代指数：820")
    private Integer rewardPoint;
    /**
     * 账号地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW
     */
    @ApiModelProperty("当前（或候选）验证人账号地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW")
    private String accountAddress;
    /**
     * 账户json:{account_index: "", address: "5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW",…}
     */
    @ApiModelProperty("当前（或候选）验证人账户json:{account_index: \"\", address: \"5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW\",…}")
    private String accountDisplay;
    /**
     * 提名者冻结CRU（全网冻结=提名冻结+验证人冻结）
     */
    @ApiModelProperty("提名者冻结CRU（全网冻结=提名冻结+验证人冻结）")
    private BigDecimal bondedNominators;
    /**
     * 验证人冻结CRU
     */
    @ApiModelProperty("验证人冻结CRU")
    private BigDecimal bondedOwner;
    /**
     * 自身有效质押
     */
    @ApiModelProperty("自身（验证人）有效质押")
    private BigDecimal ownerActivePledge;
    /**
     * 他人有效质押
     */
    @ApiModelProperty("他人有效质押")
    private BigDecimal otherActivePledge;
    /**
     * 质押上限
     */
    @ApiModelProperty("质押上限")
    private BigDecimal pledgeMax;
    /**
     * 质押总量
     */
    @ApiModelProperty("质押总量")
    private BigDecimal pledgeTotal;
    /**
     * 扣留（担保）费率
     */
    @ApiModelProperty("扣留（担保）费率")
    private BigDecimal guaranteeFee;
    /**
     * 得分
     */
    @ApiModelProperty("得分")
    private Integer score;

}
