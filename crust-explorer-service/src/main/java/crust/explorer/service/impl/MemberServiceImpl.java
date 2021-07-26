package crust.explorer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import crust.explorer.mapper.MemberMapper;
import crust.explorer.pojo.po.MemberPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.ValidatorBondedPledgeVO;
import crust.explorer.pojo.vo.ValidatorDetailVO;
import crust.explorer.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberPO> implements MemberService {


    @Override
    public PageVo<ValidatorBondedPledgeVO> getValidators(Integer role, BaseQo qo) {
        PageVo<ValidatorBondedPledgeVO> page = PageVo.initialized(qo);
        PageVo<ValidatorBondedPledgeVO> pageVo = this.baseMapper.getValidators(page, role);
        return pageVo;
    }

    @Override
    public PageVo<ValidatorBondedPledgeVO> getPledgeQuotas(BaseQo qo) {
        PageVo<ValidatorBondedPledgeVO> page = PageVo.initialized(qo);
        PageVo<ValidatorBondedPledgeVO> pageVo = this.baseMapper.getPledgeQuotas(page);
        return pageVo;
    }

    @Override
    public PageVo<ValidatorBondedPledgeVO> getActivePledges(BaseQo qo) {
        PageVo<ValidatorBondedPledgeVO> page = PageVo.initialized(qo);
        PageVo<ValidatorBondedPledgeVO> pageVo = this.baseMapper.getActivePledges(page);
        return pageVo;
    }

    @Override
    public ValidatorDetailVO getValidatorInfoByHah(String hash) {
        return this.baseMapper.getValidatorInfoByHah(hash);
    }

    @Override
    public void removeLongAgoData() {
        this.baseMapper.removeLongAgoData();
    }

}
