package crust.explorer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import crust.explorer.pojo.po.NominatorPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.NominatorVO;
import crust.explorer.pojo.vo.PageVo;

public interface NominatorService extends IService<NominatorPO> {


    void removeLongAgoData();

    PageVo<NominatorVO> listPageByValidateAddress(String address, BaseQo qo);
}
