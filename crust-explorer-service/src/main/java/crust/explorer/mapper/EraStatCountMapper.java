package crust.explorer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import crust.explorer.pojo.po.EraStatCountPO;
import crust.explorer.pojo.vo.EraStatCountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EraStatCountMapper extends BaseMapper<EraStatCountPO> {

    int syncUpdateEraCounts(@Param("eras") List<EraStatCountVO> eras);

}
