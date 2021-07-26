package crust.explorer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import crust.explorer.pojo.po.SysSyncLogPO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysSyncLogMapper extends BaseMapper<SysSyncLogPO> {

//    @Select("SELECT max(id) id from sys_sync_log where type = 3 and block_num !=-1  GROUP BY table_name,table_no")
    @Select("select max(id) id from sys_sync_log where type = 3 group by table_name,table_no")
    List<Integer> getNotRemoveId();
}
