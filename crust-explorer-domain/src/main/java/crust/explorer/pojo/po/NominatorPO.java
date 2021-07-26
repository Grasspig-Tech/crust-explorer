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
@TableName("ce_nominator")
public class NominatorPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 纪元：760
     */
    private Integer era;
    /**
     * 排名：1
     */
    private Integer nominatorRank;
    /**
     * 投票冻结CRU：117000000000000
     */
    private BigDecimal bonded;
    /**
     * 提名人账户地址
     */
    private String nominatorAddress;
    /**
     * 验证人账户地址
     */
    private String validatorAddress;
    /**
     * 提名人账户json
     */
    private String accountDisplay;
    /**
     * 份额（bonded/总数 * 100%)
     */
    private BigDecimal quotient;
    /**
     * 创建时间
     */
    private Date timeCreated;
    /**
     * 修改时间
     */
    private Date timeUpdated;

}
