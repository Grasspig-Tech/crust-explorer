package crust.explorer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import crust.explorer.mapper.NominatorMapper;
import crust.explorer.pojo.po.NominatorPO;
import crust.explorer.pojo.query.BaseQo;
import crust.explorer.pojo.vo.NominatorVO;
import crust.explorer.pojo.vo.PageVo;
import crust.explorer.service.NominatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NominatorServiceImpl extends ServiceImpl<NominatorMapper, NominatorPO> implements NominatorService {


    @Override
    public void removeLongAgoData() {
        this.baseMapper.removeLongAgoData();
    }

    @Override
    public PageVo<NominatorVO> listPageByValidateAddress(String address, BaseQo qo) {
        return this.baseMapper.listPageByValidateAddress(PageVo.initialized(qo), address);
    }
}
