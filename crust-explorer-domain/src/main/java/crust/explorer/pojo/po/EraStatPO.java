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
@TableName("ce_era_stat")
public class EraStatPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 纪元：760
     */
    private Integer era;
    /**
     * 起始块打包时间:1625120364
     */
    private Integer startBlockTimestamp;
    /**
     * 结束块打包时间:1625120364
     */
    private Integer endBlockTimestamp;
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
