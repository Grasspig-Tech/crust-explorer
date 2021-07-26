package crust.explorer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import crust.explorer.pojo.po.EraStatCountPO;
import crust.explorer.pojo.po.EraStatPO;
import crust.explorer.pojo.vo.EraStatCountVO;
import crust.explorer.pojo.vo.EraStatVO;

import java.util.List;

public interface EraStatCountService extends IService<EraStatCountPO> {


    List<EraStatCountVO> getEraStatCounts(String validator);

    void countEras();

}
