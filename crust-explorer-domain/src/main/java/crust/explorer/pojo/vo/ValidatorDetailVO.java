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
@ApiModel("验证人或候选验证人详情出参")
public class ValidatorDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 纪元:809
     */
    @ApiModelProperty("纪元:809")
    private Integer era;
    /**
     * 角色:1-validator,2-waiting，3-提名人
     */
    @ApiModelProperty("角色:1-(验证人)validator,2-(候选验证人)waiting")
    private Integer role;
    /**
     * 排名：1
     */
    @ApiModelProperty("排名：1")
    private Integer memberRank;
    /**
     * 提名人数：24
     */
    @ApiModelProperty("提名人数：24")
    private Integer countNominators;
    /**
     * 祖选举数：0
     */
    @ApiModelProperty("祖选举数：0")
    private Integer grandpaVote;
    /**
     * Session Key{babe: "9421487f2f2efeead5ae82a3c73d15005e59ed625a32e389f6ccc599b47e2202",…}
     */
    @ApiModelProperty("Session Key{babe: \"9421487f2f2efeead5ae82a3c73d15005e59ed625a32e389f6ccc599b47e2202\",…}")
    private String sessionKey;
    /**
     * 最新出块号：2243773
     */
    @ApiModelProperty("最新出块号：2243773")
    private Integer latestMining;
    /**
     * 时代指数：820
     */
    @ApiModelProperty("时代指数：820")
    private Integer rewardPoint;
    /**
     * 账号地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW
     */
    @ApiModelProperty("账号地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW")
    private String accountAddress;
    /**
     * 控制账户地址：5FBs3aqYmyBREyrW9CDsUKARbwsPPffniPPCDmQonTaMLqb2
     */
    @ApiModelProperty("控制账户地址：5FBs3aqYmyBREyrW9CDsUKARbwsPPffniPPCDmQonTaMLqb2")
    private String controllerAccountAddress;
    /**
     * 账户json{address: "5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW", display: "德坤数矿(DKSK
     */
    @ApiModelProperty("账户json{address: \"5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW\", display: \"德坤数矿(DKSK")
    private String accountDisplay;
    /**
     * 控制账户json{address: "5FBs3aqYmyBREyrW9CDsUKARbwsPPffniPPCDmQonTaMLqb2", display: "", judg
     */
    @ApiModelProperty("控制账户json{address: \"5FBs3aqYmyBREyrW9CDsUKARbwsPPffniPPCDmQonTaMLqb2\", display: \"\", judg")
    private String controllerAccountDisplay;
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
     * 扣留（担保）费率
     */
    @ApiModelProperty("扣留（担保）费率")
    private BigDecimal guaranteeFee;

}
