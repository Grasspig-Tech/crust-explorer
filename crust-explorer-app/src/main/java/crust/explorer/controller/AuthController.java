package crust.explorer.controller;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import crust.explorer.pojo.Result;
import crust.explorer.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/runtime")
@Api(value = "鉴权相关", tags = "鉴权相关")
public class AuthController {

    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/metadata")
    @ApiOperation(value = "获取websocket token")
    public Result<String> getToken() {
        String timestamp = ServletUtils.getHeader("timestamp");
        String sign = ServletUtils.getHeader("sign");
        if (StringUtils.isBlank(timestamp)) {
            log.error("Authentication failed! timestamp is empty");
//            return Result.failure("timestamp is empty");
            return Result.failure();
        }
        if (StringUtils.isBlank(timestamp) || StringUtils.isBlank(sign)) {
            log.error("Authentication failed! sign is empty");
//            return Result.failure("sign is empty");
            return Result.failure();
        }
        // 时间戳是否过期 （60s）
        long end = System.currentTimeMillis();
        long out = end - Long.parseLong(timestamp);
        if (out > Constants.TIMEOUT) {
            // 超时
            log.error("Authentication failed! timestamp timeout, timestamp:{},end:{},out:{}", timestamp, end, out);
//            return Result.failure("timestamp timeout");
            return Result.failure();
        }
        String signStr = sign(timestamp);
        if (!sign.equalsIgnoreCase(signStr)) {
            log.error("Authentication failed!,signStr:{},sign:{}", signStr, sign);
//            return Result.failure("Authentication failed!");
            return Result.failure();
        }
        String nextId = String.valueOf(new DefaultIdentifierGenerator().nextId(null));
        log.info("Authentication nextId:{}", nextId);
        redisUtils.set(RedisKeys.AUTH_KEY.concat(nextId), nextId, RedisKeys.CACHE_5M);
        return Result.success(nextId);
    }

    private String sign(String timestamp) {
        StringBuilder paramValues = new StringBuilder();
        paramValues.append("timestamp=" + timestamp);
        return SignUtils.md5Password(paramValues.toString());
    }
}
