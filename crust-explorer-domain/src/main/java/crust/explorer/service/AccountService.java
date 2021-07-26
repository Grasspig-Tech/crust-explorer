package crust.explorer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import crust.explorer.pojo.po.AccountPO;
import crust.explorer.pojo.vo.AccountVO;

public interface AccountService extends IService<AccountPO> {

    AccountVO getAccountByHash(String hash);

}
