package crust.explorer.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("ce_network_overview")
public class NetworkOverviewPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 总存储量
     */
    private String totalStorage;
    /**
     * 总发行量
     */
    private String totalCirculation;
    /**
     * 近24h总产出
     */
    private String totalOutputLast24;
    /**
     * 区块高度
     */
    private String blockHeight;
    /**
     * 已确认块高
     */
    private String blockHeightConfirmed;
    /**
     * 最后出块时间
     */
    private String blockLastTime;
    /**
     * 最低质押量
     */
    private String pledgeMinimum;
    /**
     * 平均质押量
     */
    private String pledgeAvg;
    /**
     * 每T质押量
     */
    private String pledgePer;
    /**
     * 有效质押总量
     */
    private String pledgeTotalActive;
    /**
     * 可质押总量
     */
    private String pledgeAbleNum;
    /**
     * 纪元：760
     */
    private Integer era;
    /**
     * 纪元起始时间:1625120364
     */
    private Integer eraStartTimestamp;
    /**
     * Era倒计时
     */
    private String countdownEra;
    /**
     * session倒计时
     */
    private String countdownSession;
    /**
     * 流通率
     */
    private String rateFlow;
    /**
     * 通胀率
     */
    private String rateInflation;
    /**
     * 当前手续费
     */
    private String baseFee;
    /**
     * 持有账号数
     */
    private Integer accountHold;
    /**
     * 担保人数
     */
    private Integer numberGuarantee;
    /**
     * 验证人数
     */
    private Integer numberValidator;
    /**
     * 转账次数
     */
    private Integer numberTransfer;
    /**
     * 交易次数
     */
    private Integer numberTrade;
    /**
     * 有效否：1-生效；0-失效
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date timeCreated;
    /**
     * 修改时间
     */
    private Date timeUpdated;

}
