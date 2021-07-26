package crust.explorer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import crust.explorer.pojo.po.MemberPO;
import crust.explorer.pojo.vo.MemberVO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.pojo.vo.ValidatorBondedPledgeVO;
import crust.explorer.pojo.vo.ValidatorDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper extends BaseMapper<MemberPO> {
    int syncUpdateMembers(@Param("members") List<MemberVO> members);

    PageVo<ValidatorBondedPledgeVO> getValidators(IPage<ValidatorBondedPledgeVO> page, @Param("role") Integer role);

    PageVo<ValidatorBondedPledgeVO> getPledgeQuotas(PageVo<ValidatorBondedPledgeVO> page);

    PageVo<ValidatorBondedPledgeVO> getActivePledges(PageVo<ValidatorBondedPledgeVO> page);

    ValidatorDetailVO getValidatorInfoByHah(String hash);

    void removeLongAgoData();

    int getNumberValidator();
}
