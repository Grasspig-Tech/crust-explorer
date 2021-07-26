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
@TableName("ce_block")
public class BlockPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 块状态：1-已确认，0-确认中
     */
    private Integer finalized;
    /**
     * 区块号
     */
    private Integer blockNum;
    /**
     * 区块打包时间
     */
    private Integer blockTimestamp;
    /**
     * 区块hash
     */
    private String hash;
    /**
     * 父hash
     */
    private String parentHash;
    /**
     * 事件数
     */
    private Integer eventCount;
    /**
     * 交易数
     */
    private Integer extrinsicsCount;
    /**
     * 状态根
     */
    private String stateRoot;
    /**
     * 交易根
     */
    private String extrinsicsRoot;
    /**
     * 运行时版本
     */
    private Integer specVersion;
    /**
     * 验证人json
     */
    private String accountDisplay;
    /**
     * 验证人地址
     */
    private String validator;
    /**
     * 创建时间
     */
    private Date timeCreated;
    /**
     * 修改时间
     */
    private Date timeUpdated;

}
