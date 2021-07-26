package crust.explorer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import crust.explorer.pojo.dto.WorkPledgeMapDto;
import crust.explorer.pojo.po.BondedPledgePO;
import crust.explorer.pojo.vo.BondedPledgeVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface BondedPledgeMapper extends BaseMapper<BondedPledgePO> {

    int syncUpdateBondedPledges(@Param("bondedPledges") List<BondedPledgeVO> bondedPledges);

    void removeLongAgoData();
    List<WorkPledgeMapDto> countActivePledgeMap();

    List<WorkPledgeMapDto> countWorkCapabilityMap();

    BigDecimal getPledgeMinimum();

    BigDecimal getPledgeAvg();

    BigDecimal getPledgeTotalActive();

    BigDecimal getPledgeAbleNum();

}
