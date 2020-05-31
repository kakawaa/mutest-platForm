package com.mutest.service.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.JsonResult;
import org.springframework.stereotype.Service;

/**
 * @author muguozheng
 * @date 2020/5/14 21:21
 * @Description: 权限控制
 * @modify
 */
@Service
public interface GroupService {
    JsonResult getGroupList(int pageNum, int pageSize);

    JsonResult updateGroup(JSONObject request);

    JsonResult addGroup(JSONObject request);

    JsonResult searchGroup(JSONObject request);

    JsonResult getGroupUserList(Long groupId);

    JsonResult updateGroupUser(JSONObject request);

    JsonResult getGroupProjectList(Long groupId);

    JsonResult updateGroupProject(JSONObject request);
}
