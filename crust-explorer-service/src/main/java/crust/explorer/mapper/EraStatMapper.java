package crust.explorer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import crust.explorer.pojo.po.EraStatPO;
import crust.explorer.pojo.vo.EraStatVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EraStatMapper extends BaseMapper<EraStatPO> {

    int syncUpdateEra(@Param("era") EraStatVO era);

    int syncUpdateEras(@Param("eras") List<EraStatVO> eras);

}
