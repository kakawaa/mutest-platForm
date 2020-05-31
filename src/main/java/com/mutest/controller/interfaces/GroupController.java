package com.mutest.controller.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.JsonResult;
import com.mutest.service.interfaces.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author muguozheng
 * @date 2020/5/18 19:33
 * @Description: 接口测试权限
 * @modify
 */
@RestController
@RequestMapping(value = "/interface")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/groupList", method = RequestMethod.GET)
    public JsonResult getGroupList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return groupService.getGroupList(pageNum, pageSize);
    }

    @RequestMapping(value = "/updateGroup", method = RequestMethod.PUT)
    public JsonResult updateGroup(@RequestBody JSONObject request) {
        return groupService.updateGroup(request);
    }

    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    public JsonResult addGroup(@RequestBody JSONObject request) {
        return groupService.addGroup(request);
    }

    @RequestMapping(value = "/searchGroup", method = RequestMethod.POST)
    public JsonResult searchGroup(@RequestBody JSONObject request) {
        return groupService.searchGroup(request);
    }

    @RequestMapping(value = "/groupUserList", method = RequestMethod.GET)
    public JsonResult getGroupUserList(@RequestParam("groupId") Long groupId){
        return groupService.getGroupUserList(groupId);
    }

    @RequestMapping(value = "/updateGroupUsers", method = RequestMethod.POST)
    public JsonResult updateGroupUsers(@RequestBody JSONObject request){
        return groupService.updateGroupUser(request);
    }

    @RequestMapping(value = "/groupProjectList", method = RequestMethod.GET)
    public JsonResult getGroupProjectList(@RequestParam("groupId") Long groupId){
        return groupService.getGroupProjectList(groupId);
    }

    @RequestMapping(value = "/updateGroupProject", method = RequestMethod.POST)
    public JsonResult updateGroupProject(@RequestBody JSONObject request){
        return groupService.updateGroupProject(request);
    }
}
