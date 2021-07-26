package crust.explorer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import crust.explorer.mapper.EraStatCountMapper;
import crust.explorer.pojo.po.EraStatCountPO;
import crust.explorer.pojo.vo.EraStatCountVO;
import crust.explorer.service.EraStatCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EraStatCountServiceImpl extends ServiceImpl<EraStatCountMapper, EraStatCountPO> implements EraStatCountService {


    @Override
    public List<EraStatCountVO> getEraStatCounts(String validator) {
        return null;
    }

    @Override
    public void countEras() {
        List<EraStatCountVO> eras = new ArrayList<>();
        this.baseMapper.syncUpdateEraCounts(eras);
    }
}
