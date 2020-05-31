package com.mutest.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import com.mutest.dto.LoginUser;
import com.mutest.dto.Token;
import com.mutest.service.SysLogService;
import com.mutest.service.TokenService;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/4/13 9:57
 * @description token存到redis的实现类，普通token，uuid
 * @modify
 */
@Deprecated
//@Service
public class TokenServiceImpl implements TokenService {

    /**
     * token过期秒数
     */
    @Value("${token.expire.seconds}")
    private Integer expireSeconds;
    @Autowired
    private RedisTemplate<String, LoginUser> redisTemplate;
    @Autowired
    private SysLogService logService;

    @Override
    public Token saveToken(LoginUser loginUser) {
        String token = UUID.randomUUID().toString();

        loginUser.setToken(token);
        cacheLoginUser(loginUser);
        // 登陆日志
        logService.save(loginUser.getId(), "登陆", true, null);

        return new Token(token, loginUser.getLoginTime());
    }

    private void cacheLoginUser(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds * 1000);
        // 缓存
        redisTemplate.boundValueOps(getTokenKey(loginUser.getToken())).set(loginUser, expireSeconds, TimeUnit.SECONDS);
    }

    /**
     * 更新缓存的用户信息
     */
    @Override
    public void refresh(LoginUser loginUser) {
        cacheLoginUser(loginUser);
    }

    @Override
    public LoginUser getLoginUser(String token) {
        return redisTemplate.boundValueOps(getTokenKey(token)).get();
    }

    @Override
    public boolean deleteToken(String token) {
        String key = getTokenKey(token);
        LoginUser loginUser = redisTemplate.opsForValue().get(key);
        if (loginUser != null) {
            redisTemplate.delete(key);
            // 退出日志
            logService.save(loginUser.getId(), "退出", true, null);

            return true;
        }
        return false;
    }

    private String getTokenKey(String token) {
        return "tokens:" + token;
    }
}
