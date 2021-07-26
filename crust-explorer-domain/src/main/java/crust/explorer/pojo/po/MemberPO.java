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
@TableName("ce_member")
public class MemberPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 纪元:809
     */
    private Integer era;
    /**
     * 角色:1-validator,2-waiting，3-提名人
     */
    private Integer role;
    /**
     * 排名：1
     */
    private Integer memberRank;
    /**
     * 提名人数：24
     */
    private Integer countNominators;
    /**
     * 祖选举数：0
     */
    private Integer grandpaVote;
    /**
     * Session Key{babe: "9421487f2f2efeead5ae82a3c73d15005e59ed625a32e389f6ccc599b47e2202",…}
     */
    private String sessionKey;
    /**
     * 最新出块号：2243773
     */
    private Integer latestMining;
    /**
     * 时代指数：820
     */
    private Integer rewardPoint;
    /**
     * 账号地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW
     */
    private String accountAddress;
    /**
     * 控制账户地址：5FBs3aqYmyBREyrW9CDsUKARbwsPPffniPPCDmQonTaMLqb2
     */
    private String controllerAccountAddress;
    /**
     * 账户json{address: "5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW", display: "德坤数矿(DKSK
     */
    private String accountDisplay;
    /**
     * 控制账户json{address: "5FBs3aqYmyBREyrW9CDsUKARbwsPPffniPPCDmQonTaMLqb2", display: "", judg
     */
    private String controllerAccountDisplay;
    /**
     * 创建时间
     */
    private Date timeCreated;
    /**
     * 修改时间
     */
    private Date timeUpdated;

}
