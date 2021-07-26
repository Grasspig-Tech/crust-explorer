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
@TableName("ce_transfer")
public class TransferPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 数量:0.00018004716
     */
    private BigDecimal amount;
    /**
     * 币种
     */
    private String assetSymbol;
    /**
     * 事件index:2243376-1
     */
    private String eventIndex;
    /**
     * 事件sort:22433760001
     */
    private String eventSort;
    /**
     * 交易index:2243376-2
     */
    private String extrinsicIndex;
    /**
     * 区块号
     */
    private Integer blockNum;
    /**
     * 区块打包时间
     */
    private Integer blockTimestamp;
    /**
     * 操作:balances
     */
    private String module;
    /**
     * hash
     */
    private String hash;
    /**
     * from
     */
    private String from;
    /**
     * to
     */
    private String to;
    /**
     * fee
     */
    private BigDecimal fee;
    /**
     * nonce
     */
    private Integer nonce;
    /**
     * 块状态：1-已确认，0-确认中
     */
    private Integer finalized;
    /**
     * 结果：1-成功，0-失败
     */
    private Integer success;
    /**
     * 创建时间
     */
    private Date timeCreated;
    /**
     * 修改时间
     */
    private Date timeUpdated;

}
