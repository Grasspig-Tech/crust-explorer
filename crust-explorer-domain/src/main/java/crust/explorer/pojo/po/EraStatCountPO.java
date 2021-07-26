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
@TableName("ce_era_stat_count")
public class EraStatCountPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 纪元：760
     */
    private Integer era;
    /**
     * 验证人账号地址：5DvTCFuPUXb6Vzg5NB4mFu3Hz95GUqXTMLXib66e4KhF9wxW
     */
    private String validatorStash;
    /**
     * 起始块：2242439
     */
    private Integer startBlockNum;
    /**
     * 结束块：2243832
     */
    private Integer endBlockNum;
    /**
     * 起始块打包时间:1625120364
     */
    private Integer startBlockTimestamp;
    /**
     * 结束块打包时间:1625120364
     */
    private Integer endBlockTimestamp;
    /**
     * 所出块
     */
    private String blockProduced;
    /**
     * 出块数量:74
     */
    private Integer blockProducedCount;
    /**
     * 状态：1-已结束；0-未结束
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
