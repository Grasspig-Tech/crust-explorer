package crust.explorer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import crust.explorer.pojo.po.SysConfigPO;

public interface SysConfigService extends IService<SysConfigPO> {

    String getChainDomain(String domainKey);
}
