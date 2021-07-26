package crust.explorer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import crust.explorer.mapper.SysConfigMapper;
import crust.explorer.pojo.po.SysConfigPO;
import crust.explorer.service.SysConfigService;
import crust.explorer.util.RedisKeys;
import crust.explorer.util.RedisUtils;
import crust.explorer.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfigPO> implements SysConfigService {

    @Autowired
    RedisUtils redisUtils;

    @Override
    public String getChainDomain(String domainKey) {
        String domain = redisUtils.get(domainKey);
        if (StringUtils.isBlank(domain)) {
            LambdaQueryWrapper<SysConfigPO> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(SysConfigPO::getConfigKey, domainKey);
            SysConfigPO sysConfig = this.baseMapper.selectOne(queryWrapper);
            if (Objects.nonNull(sysConfig) && StringUtils.isNotBlank(sysConfig.getConfigValue())) {
                domain = sysConfig.getConfigValue();
                redisUtils.set(domainKey, domain);
            }
        }
        return domain;
    }
}
