package com.mutest.service;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.JsonResult;
import com.mutest.model.SysUser;

public interface UserService {
    JsonResult getUserList(int pageNum, int pageSize);

    JsonResult addUser(JSONObject userInfo);

    JsonResult updateUser(JSONObject userInfo);

    JsonResult deleteUser(Long userId);

    JsonResult searchUser(JSONObject request);

    SysUser getUser(String username);

    JsonResult changePassword(JSONObject userPasswordInfo);
}
