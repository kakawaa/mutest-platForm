package com.mutest.annotation.interfaces;

import java.lang.annotation.*;

/**
 * @author muguozheng
 * @date 2020/5/28 15:54
 * @Description: 接口自动化模块鉴权
 * @modify
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProjectPermissions {
}
