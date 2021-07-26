package crust.explorer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import crust.explorer.pojo.po.MemberPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.ValidatorBondedPledgeVO;
import crust.explorer.pojo.vo.ValidatorDetailVO;

public interface MemberService extends IService<MemberPO> {

    PageVo<ValidatorBondedPledgeVO> getValidators(Integer role, BaseQo qo);

    PageVo<ValidatorBondedPledgeVO> getPledgeQuotas(BaseQo qo);

    PageVo<ValidatorBondedPledgeVO> getActivePledges(BaseQo qo);

    ValidatorDetailVO getValidatorInfoByHah(String hash);

    void removeLongAgoData();
}
