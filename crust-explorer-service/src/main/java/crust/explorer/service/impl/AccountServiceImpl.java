package crust.explorer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import crust.explorer.mapper.AccountMapper;
import crust.explorer.pojo.po.AccountPO;
import crust.explorer.pojo.vo.AccountVO;
import crust.explorer.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountPO> implements AccountService {


    @Override
    public AccountVO getAccountByHash(String hash) {
        return this.baseMapper.getAccountByHash(hash);
    }

}
