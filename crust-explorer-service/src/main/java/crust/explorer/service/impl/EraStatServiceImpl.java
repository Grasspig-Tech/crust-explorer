package crust.explorer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import crust.explorer.mapper.EraStatMapper;
import crust.explorer.pojo.po.EraStatPO;
import crust.explorer.pojo.vo.EraVO;
import crust.explorer.sao.ChainSaoImpl;
import crust.explorer.service.EraStatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class EraStatServiceImpl extends ServiceImpl<EraStatMapper, EraStatPO> implements EraStatService {

    @Autowired
    ChainSaoImpl chainSao;
    @Autowired
    ChainServiceImpl chainService;

    @Override
    public void refreshEra() {
        EraVO eraVO = chainSao.queryCurrentEra();
        chainService.writeEraData(eraVO);
    }

}
