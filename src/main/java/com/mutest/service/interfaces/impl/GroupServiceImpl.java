package com.mutest.service.interfaces.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mutest.advice.BusinessErrorException;
import com.mutest.advice.BusinessMsgEnum;
import com.mutest.dao.base.InterfaceGroupDao;
import com.mutest.model.JsonResult;
import com.mutest.model.SysUser;
import com.mutest.model.interfaces.InterfaceInfo;
import com.mutest.model.interfaces.ModuleInfo;
import com.mutest.model.interfaces.ProjectInfo;
import com.mutest.service.interfaces.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author muguozheng
 * @date 2020/5/14 21:23
 * @Description: 权限控制实现类
 * @modify
 */
@Service(value = "GroupService")
@Slf4j
public class GroupServiceImpl implements GroupService {
    @Resource
    private InterfaceGroupDao interfaceGroupDao;

    @Override
    public JsonResult getGroupList(int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<InterfaceInfo> pageInfo = new PageInfo(interfaceGroupDao.getGroupList());
            return new JsonResult<>(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("获取分组列表失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    @Override
    public JsonResult updateGroup(JSONObject groupInfo) {
        try {
            Long id = groupInfo.getLong("id");
            String groupName = groupInfo.getString("groupName");

            int count = interfaceGroupDao.groupUpdateCount(id, groupName);
            if (count > 0) {
                return new JsonResult<>("400", "该组已存在，请勿重复添加！");
            }
            interfaceGroupDao.updateGroup(groupInfo);
            return new JsonResult<>("0", "修改成功！");
        } catch (Exception e) {
            log.error("修改分组失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    @Override
    public JsonResult addGroup(JSONObject groupInfo) {
        try {
            String groupName = groupInfo.getString("groupName");

            int count = interfaceGroupDao.groupAddCount(groupName);
            if (count > 0) {
                return new JsonResult<>("400", "该组已存在，请勿重复添加！");
            }
            interfaceGroupDao.addGroup(groupInfo);
            return new JsonResult<>("0", "添加成功！");
        } catch (Exception e) {
            log.error("添加分组失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    @Override
    public JsonResult searchGroup(JSONObject request) {
        try {
            int pageNum = request.getInteger("pageNum");
            int pageSize = request.getInteger("pageSize");

            PageHelper.startPage(pageNum, pageSize);
            PageInfo<ModuleInfo> pageInfo = new PageInfo(interfaceGroupDao.searchGroup(request));
            return new JsonResult<>(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("查找分组失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    @Override
    public JsonResult getGroupUserList(Long groupId) {
        try {
            // 获取所有用户的list
            List<SysUser> sysUsers = interfaceGroupDao.getUserList();
            // 根据组id获取改组用户的id集合
            List<String> groupUserIds = interfaceGroupDao.getUserIdsByGroup(groupId);

            JSONObject data = new JSONObject();
            data.put("sysUsers", sysUsers);
            data.put("groupUserIds", groupUserIds);
            return new JsonResult<>(data, "获取组成员信息成功！");
        } catch (Exception e) {
            log.error("获取组成员列表失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    @Override
    public JsonResult updateGroupUser(JSONObject request) {
        try {
            Long groupId = request.getLong("groupId");
            int index = request.getInteger("index");
            JSONArray userList = request.getJSONArray("userList");

            // index=0 为左向右移动，即向组内添加用户
            if (index == 0) {
                interfaceGroupDao.addGroupUserBatch(groupId, userList);
            } else {
                interfaceGroupDao.deleteGroupUserBatch(groupId, userList);
            }
            return new JsonResult<>("0", "修改组成员信息成功！");
        } catch (Exception e) {
            log.error("修改组成员失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    @Override
    public JsonResult getGroupProjectList(Long groupId) {
        try {
            // 获取所有项目的list
            List<ProjectInfo> projectList = interfaceGroupDao.getProjectList();
            // 根据组id获取改组项目的id集合
            List<String> groupProjectIds = interfaceGroupDao.getProjectIdsByGroup(groupId);

            JSONObject data = new JSONObject();
            data.put("projectList", projectList);
            data.put("groupProjectIds", groupProjectIds);
            return new JsonResult<>(data, "获取组项目信息成功！");
        } catch (Exception e) {
            log.error("获取组项目列表失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    @Override
    public JsonResult updateGroupProject(JSONObject request) {
        try {
            Long groupId = request.getLong("groupId");
            int index = request.getInteger("index");
            JSONArray projectList = request.getJSONArray("projectList");

            // index=0 为左向右移动，即向组内添加用户
            if (index == 0) {
                interfaceGroupDao.addGroupProjectBatch(groupId, projectList);
            } else {
                interfaceGroupDao.deleteGroupProjectBatch(groupId, projectList);
            }
            return new JsonResult<>("0", "修改组项目信息成功！");
        } catch (Exception e) {
            log.error("修改组成员失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }
}
