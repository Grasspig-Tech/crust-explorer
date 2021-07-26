package crust.explorer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import crust.explorer.pojo.po.NetworkOverviewPO;

@Mapper
public interface NetworkOverviewMapper extends BaseMapper<NetworkOverviewPO> {


    void saveOrUpdate(NetworkOverviewPO networkOverview);
}
