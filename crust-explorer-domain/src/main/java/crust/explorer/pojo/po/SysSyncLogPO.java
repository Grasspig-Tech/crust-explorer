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
@TableName("sys_sync_log")
public class SysSyncLogPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 同步模式：auto,manual
     */
    private String syncMode;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表号
     */
    private Integer tableNo;
    /**
     * 日志类型：1-同步完成；2-同步出错；3-已同步到？
     */
    private Integer type;
    /**
     * 日志类型描述：1-done；2-error；3-已同步到？
     */
    private String typeDesc;
    /**
     * 区块号
     */
    private Integer blockNum;
    /**
     * 错误详情
     */
    private String errorInfo;
    /**
     * 创建时间
     */
    private Date timeCreated;
    /**
     * 修改时间
     */
    private Date timeUpdated;

}
