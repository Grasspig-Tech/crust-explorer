package crust.explorer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import crust.explorer.pojo.po.NominatorPO;
import crust.explorer.pojo.vo.NominatorVO;
import crust.explorer.pojo.vo.PageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NominatorMapper extends BaseMapper<NominatorPO> {


    int syncUpdateNominators(@Param("nominators") List<NominatorVO> nominators);

    void removeLongAgoData();

    PageVo<NominatorVO> listPageByValidateAddress(IPage<NominatorVO> page, @Param("address") String address);

    int getNumberGuarantee();
}
