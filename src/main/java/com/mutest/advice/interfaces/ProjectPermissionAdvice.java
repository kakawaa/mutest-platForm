package com.mutest.advice.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.mutest.advice.BusinessErrorException;
import com.mutest.dao.base.InterfacePermissionDao;
import com.mutest.model.SysUser;
import com.mutest.utils.UserUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author muguozheng
 * @date 2020/5/28 15:57
 * @Description: 接口自动化项目权限校验
 * @modify
 */
@Aspect
@Component
@Order(1)
public class ProjectPermissionAdvice {
    @Resource
    private InterfacePermissionDao interfacePermissionDao;

    @Value("${interface.permission.groupPermissionRoleId}")
    String groupPermissionRoleIdStr;

    @Pointcut("@annotation(com.mutest.annotation.interfaces.ProjectPermissions)")
    private void projectPermissionPoint() {
    }

    @Before("projectPermissionPoint()")
    public void projectPermission(JoinPoint joinPoint) {
        System.out.println("开始项目权限校验===========================================" + System.currentTimeMillis());
        SysUser user = UserUtil.getLoginUser();
        Long userId = user.getId();

        //获取请求参数
        Object[] objects = joinPoint.getArgs();
        Long projectId = ((JSONObject) objects[0]).getLong("projectId");
        String creator = ((JSONObject) objects[0]).getString("creator");
        System.out.println("projectId===================" + projectId);
        System.out.println("creator===================" + creator);

        List<Long> projectIds = interfacePermissionDao.projectListByUserId(userId);
        // 没有项目权限直接抛出异常
        if (!projectIds.contains(projectId)) {
            throw new BusinessErrorException("403", "您没有该项目权限，请联系管理员！");
        }
    }
}
