package crust.explorer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import crust.explorer.pojo.po.EraStatPO;

public interface EraStatService extends IService<EraStatPO> {


    void refreshEra();

}
