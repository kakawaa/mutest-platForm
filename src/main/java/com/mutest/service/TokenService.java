package com.mutest.service;

import com.mutest.dto.LoginUser;
import com.mutest.dto.Token;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/4/12 11:27
 * @description token管理器，可存储到redis或数据库，具体看实现类，默认基于redis，实现类为 com.mutest.service.impl.TokenServiceJWTImpl
 * 如要换成数据库存储，将TokenServiceImpl类上的注解@Primary挪到com.mutest.service.impl.TokenServiceDbImpl
 * @modify
 */
public interface TokenService {

    Token saveToken(LoginUser loginUser);

    void refresh(LoginUser loginUser);

    LoginUser getLoginUser(String token);

    boolean deleteToken(String token);

}
