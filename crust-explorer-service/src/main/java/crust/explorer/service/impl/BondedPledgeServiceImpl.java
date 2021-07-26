package crust.explorer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.reflect.TypeToken;
import crust.explorer.enums.CountMapEnum;
import crust.explorer.mapper.BondedPledgeMapper;
import crust.explorer.pojo.copier.BlockCopier;
import crust.explorer.pojo.dto.WorkPledgeMapDto;
import crust.explorer.pojo.po.BondedPledgePO;
import crust.explorer.pojo.vo.WorkPledgeMapVO;
import crust.explorer.service.BondedPledgeService;
import crust.explorer.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class BondedPledgeServiceImpl extends ServiceImpl<BondedPledgeMapper, BondedPledgePO> implements BondedPledgeService {

    @Autowired
    RedisUtils redisUtils;

    @Override
    public void removeLongAgoData() {
        this.baseMapper.removeLongAgoData();
    }

    @Override
    public void countWorkCapabilityAndPledgeMap() {
        // 有效质押分布 取 pledge_total
        countWorkCapabilityAndPledgeMap(CountMapEnum.ACTIVE_PLEDGE_MAP);
        // 有效算力分布 取 pledge_max
        countWorkCapabilityAndPledgeMap(CountMapEnum.WORK_CAPABILITY_MAP);
    }

    @Override
    public List<WorkPledgeMapVO> countWorkCapabilityAndPledgeMap(CountMapEnum countEnum) {
        List<WorkPledgeMapDto> pledgeMap = null;
        if (CountMapEnum.ACTIVE_PLEDGE_MAP.equals(countEnum)) {
            pledgeMap = this.baseMapper.countActivePledgeMap();
        } else {
            pledgeMap = this.baseMapper.countWorkCapabilityMap();
        }
        if (!CollectionUtils.isEmpty(pledgeMap)) {
            BigDecimal before = BigDecimal.ZERO;
            for (WorkPledgeMapDto d : pledgeMap) {
                before = before.add(d.getMapRatio());
            }
            BigDecimal leftRatio = new BigDecimal(MathUtils.ONE).subtract(before);
            WorkPledgeMapDto others = WorkPledgeMapDto.builder()
                    .address(Constants.OTHERS).display(Constants.OTHERS)
                    .mapRatio(leftRatio).total(pledgeMap.get(0).getTotal()).build();
            pledgeMap.add(others);
        }
        List<WorkPledgeMapVO> workPledgeMapVOS = BlockCopier.INSTANCE.toWorkPledgeMapVOs(pledgeMap);
        if (!CollectionUtils.isEmpty(workPledgeMapVOS)) {
            redisUtils.set(countEnum.getMapKey(), workPledgeMapVOS);
        }
        return workPledgeMapVOS;
    }

    public List<WorkPledgeMapVO> getWorkPledgeMap(CountMapEnum countEnum) {
        String workPledgeMap = redisUtils.get(countEnum.getMapKey());
        if (StringUtils.isNotBlank(workPledgeMap)) {
            List<WorkPledgeMapVO> list = GsonUtils.getInstance().fromJson(workPledgeMap, new TypeToken<List<WorkPledgeMapVO>>() {
            }.getType());
            if (!CollectionUtils.isEmpty(list)) {
                return list;
            }
        }
        return this.countWorkCapabilityAndPledgeMap(countEnum);
    }
}
