package crust.explorer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import crust.explorer.pojo.po.NetworkOverviewPO;

public interface NetworkOverviewService extends IService<NetworkOverviewPO> {


    void removeLongAgoData();

    NetworkOverviewPO getOnly();

    void putOnly(NetworkOverviewPO only);
}
