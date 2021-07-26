package crust.explorer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import crust.explorer.pojo.po.AccountPO;
import crust.explorer.pojo.vo.AccountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountMapper extends BaseMapper<AccountPO> {

    int syncUpdateAccounts(@Param("accounts") List<AccountVO> accounts);

    AccountVO getAccountByHash(String hash);
}
