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
@TableName("sys_config")
public class SysConfigPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * key
     */
    private String configKey;
    /**
     * value
     */
    private String configValue;
    /**
     * 状态：1-开；0-关
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 修改时间
     */
    private Date timeUpdated;

}
