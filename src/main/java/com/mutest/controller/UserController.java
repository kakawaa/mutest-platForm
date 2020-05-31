package com.mutest.controller;

import com.alibaba.fastjson.JSONObject;
import com.mutest.annotation.LogAnnotation;
import com.mutest.dao.base.UserDao;
import com.mutest.model.JsonResult;
import com.mutest.model.SysUser;
import com.mutest.service.UserService;
import com.mutest.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/3/26 14:15
 * @description 用户相关接口
 * @modify
 */
@Api(tags = "用户")

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @LogAnnotation
//    @PostMapping
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ApiOperation(value = "保存用户")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public JsonResult saveUser(@RequestBody JSONObject userInfo) {
        SysUser u = userService.getUser(userInfo.getString("username"));
        if (u != null) {
            throw new IllegalArgumentException(userInfo.getString("username") + "已存在");
        }
        return userService.addUser(userInfo);
    }

    @LogAnnotation
//    @PutMapping
    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    @ApiOperation(value = "修改用户")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public JsonResult updateUser(@RequestBody JSONObject userInfo) {
        return userService.updateUser(userInfo);
    }

    @LogAnnotation
//    @PutMapping
    @RequestMapping(value = "/deleteUser", method = RequestMethod.PUT)
    @ApiOperation(value = "删除用户")
    @PreAuthorize("hasAuthority('sys:user:del')")
    public JsonResult deleteUser(@RequestParam("caseId") Long userId) {
        return userService.deleteUser(userId);
    }

    @RequestMapping(value = "/searchUser", method = RequestMethod.POST)
    public JsonResult searchUser(@RequestBody JSONObject request) {
        return userService.searchUser(request);
    }

    @LogAnnotation
    @PutMapping(params = "headImgUrl")
    @ApiOperation(value = "修改头像")
    public void updateHeadImgUrl(String headImgUrl) {
        SysUser user = UserUtil.getLoginUser();
        Long userId = user.getId();

        userDao.updateHeadImgUrl(headImgUrl, userId);
        log.debug("{}修改了头像", user.getUsername());
    }

//    @LogAnnotation
    @PutMapping("/changePassword")
//    @ApiOperation(value = "修改密码")
//    @PreAuthorize("hasAuthority('sys:user:password')")
    public JsonResult changePassword(@RequestBody JSONObject userPasswordInfo) {
        return userService.changePassword(userPasswordInfo);
    }

    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('sys:user:query')")
    public JsonResult getUserList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return userService.getUserList(pageNum, pageSize);
    }

    @ApiOperation(value = "当前登录用户")
    @GetMapping("/current")
    public SysUser currentUser() {
        return UserUtil.getLoginUser();
    }

    @ApiOperation(value = "根据用户id获取用户")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:user:query')")
    public SysUser user(@PathVariable Long id) {
        return userDao.getById(id);
    }

}
