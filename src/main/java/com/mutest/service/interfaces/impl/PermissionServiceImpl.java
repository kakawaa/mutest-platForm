package com.mutest.service.interfaces.impl;

import com.mutest.dao.base.InterfacePermissionDao;
import com.mutest.dao.base.InterfaceProjectDao;
import com.mutest.service.interfaces.PermissionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author muguozheng
 * @date 2020/5/21 15:49
 * @Description: 用户权限校验
 * @modify
 */
@Service(value = "PermissionService")
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private InterfacePermissionDao interfacePermissionDao;
    @Resource
    private InterfaceProjectDao interfaceProjectDao;

    @Value("${interface.permission.groupPermissionRoleId}")
    String groupPermissionRoleIdStr;

    /**
     * 接口测试用例编辑鉴权
     *
     * @param projectId 项目id
     * @param creator   创建者
     */
    @Override
    public void interfaceCaseEditAuth(Long projectId, String creator) {
//        SysUser user = UserUtil.getLoginUser();
//        Long userId = user.getId();
//        String nickName = user.getNickname();
//        List<Long> roleIdList = interfacePermissionDao.roleListByUserId(userId);
//        dataRangeCheck(userId, projectId);
//
//        // 有项目权限，角色权限不足且操作非本人数据，抛出异常
//        if (!listCheck(groupPermissionRoleIdStr, roleIdList) && !nickName.equals(creator))
//            throw new BusinessErrorException("403", "您不能操作非本人数据，请联系管理员！");
    }

    @Override
    public void interfaceCaseAddAuth(String projectName) {
//        Long projectId = interfaceProjectDao.getProjectIdByName(projectName);
//        SysUser user = UserUtil.getLoginUser();
//        Long userId = user.getId();
//
//        dataRangeCheck(userId, projectId);
    }

    /**
     * 根据用户id和项目id判断用户是否有该项目权限
     *
     * @param userId    用户id
     * @param projectId 项目id
     */
    public void dataRangeCheck(Long userId, Long projectId) {
//        List<Long> projectIds = interfacePermissionDao.projectListByUserId(userId);
//        // 没有项目权限直接抛出异常
//        if (!projectIds.contains(projectId)) {
//            throw new BusinessErrorException("403", "您没有该项目权限，请联系管理员！");
//        }
    }

    /**
     * @param groupPermissionRoleIdStr 配置文件拥有组内全部数据操作权限的角色id
     * @param roleIdList               当前登录用户所拥有的roleId
     * @return 当前用户是否拥有操作项目的非本人数据操作权限
     */
    public boolean listCheck(String groupPermissionRoleIdStr, List<Long> roleIdList) {
//        String[] groupPermissionRoleIdArr = groupPermissionRoleIdStr.split(",");
//
//        List<Long> groupPermissionRoleIdList = Arrays.stream(groupPermissionRoleIdArr)
//                .map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
//
//        for (Long roleId : roleIdList) {
//            // 对于多重角色者，有一个角色满足条件即可
//            return groupPermissionRoleIdList.contains(roleId);
//        }
        return false;
//        return accessAllRoleIdList.containsAll(roleIds);
    }
}
