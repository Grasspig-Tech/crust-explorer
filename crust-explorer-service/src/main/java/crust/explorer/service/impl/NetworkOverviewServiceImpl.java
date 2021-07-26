package crust.explorer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import crust.explorer.mapper.NetworkOverviewMapper;
import crust.explorer.pojo.po.NetworkOverviewPO;
import crust.explorer.service.NetworkOverviewService;
import crust.explorer.util.ChainUtils;
import crust.explorer.util.Constants;
import crust.explorer.util.MathUtils;
import crust.explorer.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class NetworkOverviewServiceImpl extends ServiceImpl<NetworkOverviewMapper, NetworkOverviewPO> implements NetworkOverviewService {

    @Autowired
    RedisUtils redisUtils;

    @Override
    public void removeLongAgoData() {
        LambdaQueryWrapper<NetworkOverviewPO> query = Wrappers.lambdaQuery();
        query.orderByDesc(NetworkOverviewPO::getTimeCreated).last(Constants.LIMIT_1);
        NetworkOverviewPO one = this.getOne(query);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 3);
        Date time = calendar.getTime();
        LambdaQueryWrapper<NetworkOverviewPO> remove = Wrappers.lambdaQuery();
        remove.le(NetworkOverviewPO::getTimeCreated, time);
        if (Objects.nonNull(one)) {
            remove.ne(NetworkOverviewPO::getId, one.getId());
        }
        this.remove(remove);
    }

    @Override
    public NetworkOverviewPO getOnly() {
        NetworkOverviewPO only = ChainUtils.getObj(Constants.LAST_NETWORK_OVERVIEW);
        if (Objects.nonNull(only)) {
            return only;
        }
        NetworkOverviewPO overview = redisUtils.get(Constants.LAST_NETWORK_OVERVIEW, NetworkOverviewPO.class);
        if (Objects.nonNull(overview)) {
            return overview;
        }
        return this.baseMapper.selectById(MathUtils.ONE);
    }

    @Override
    public void putOnly(NetworkOverviewPO only) {
        only.setTimeUpdated(null);
        ChainUtils.putObj(Constants.LAST_NETWORK_OVERVIEW, only);
        redisUtils.set(Constants.LAST_NETWORK_OVERVIEW, only);
        this.baseMapper.updateById(only);
    }
}
