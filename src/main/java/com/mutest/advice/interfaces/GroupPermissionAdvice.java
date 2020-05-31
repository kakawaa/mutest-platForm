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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author muguozheng
 * @date 2020/5/28 15:57
 * @Description: 接口自动化项目权限校验
 * @modify
 */
@Aspect
@Component
@Order(2)
public class GroupPermissionAdvice {
    @Resource
    private InterfacePermissionDao interfacePermissionDao;

    @Value("${interface.permission.groupPermissionRoleId}")
    String groupPermissionRoleIdStr;

    @Pointcut("@annotation(com.mutest.annotation.interfaces.GroupPermissions)")
    private void groupPermissionPoint() {
    }


    @Before("groupPermissionPoint()")
    public void groupPermission(JoinPoint joinPoint) {
        SysUser user = UserUtil.getLoginUser();
        Long userId = user.getId();
        String nickName = user.getNickname();
        List<Long> roleIdList = interfacePermissionDao.roleListByUserId(userId);

        //获取请求参数
        Object[] objects = joinPoint.getArgs();
        String creator = ((JSONObject) objects[0]).getString("creator");

        // 有项目权限，角色权限不足且操作非本人数据，抛出异常
        if (!listCheck(groupPermissionRoleIdStr, roleIdList) && !nickName.equals(creator))
            throw new BusinessErrorException("403", "您不能操作非本人数据，请联系管理员！");
    }

    /**
     * @param groupPermissionRoleIdStr 配置文件拥有组内全部数据操作权限的角色id
     * @param roleIdList               当前登录用户所拥有的roleId
     * @return 当前用户是否拥有操作项目的非本人数据操作权限
     */
    public boolean listCheck(String groupPermissionRoleIdStr, List<Long> roleIdList) {
        String[] groupPermissionRoleIdArr = groupPermissionRoleIdStr.split(",");

        List<Long> groupPermissionRoleIdList = Arrays.stream(groupPermissionRoleIdArr)
                .map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());

        for (Long roleId : roleIdList) {
            // 对于多重角色者，有一个角色满足条件即可
            return groupPermissionRoleIdList.contains(roleId);
        }
        return false;
//        return accessAllRoleIdList.containsAll(roleIds);
    }
}
