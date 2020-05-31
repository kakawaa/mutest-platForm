package com.mutest.annotation.interfaces;

import java.lang.annotation.*;

/**
 * @author muguozheng
 * @date 2020/5/28 18:06
 * @Description: 组权限校验
 * @modify
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GroupPermissions {

}
