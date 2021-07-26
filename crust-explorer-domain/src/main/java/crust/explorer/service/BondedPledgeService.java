package crust.explorer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import crust.explorer.enums.CountMapEnum;
import crust.explorer.pojo.po.BondedPledgePO;
import crust.explorer.pojo.vo.WorkPledgeMapVO;

import java.util.List;

public interface BondedPledgeService extends IService<BondedPledgePO> {

    void removeLongAgoData();

    void countWorkCapabilityAndPledgeMap();

    List<WorkPledgeMapVO> countWorkCapabilityAndPledgeMap(CountMapEnum countEnum);

    List<WorkPledgeMapVO> getWorkPledgeMap(CountMapEnum countEnum);
}
