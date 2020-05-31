package com.mutest.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mutest.advice.BusinessErrorException;
import com.mutest.advice.BusinessMsgEnum;
import com.mutest.dao.base.UserDao;
import com.mutest.model.JsonResult;
import com.mutest.model.SysUser;
import com.mutest.model.SysUser.Status;
import com.mutest.model.interfaces.CaseInfo;
import com.mutest.service.UserService;
import com.mutest.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public JsonResult getUserList(int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<SysUser> pageInfo = new PageInfo(userDao.getUserList());
            return new JsonResult(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("获取用户列表失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public JsonResult updateUser(@RequestBody JSONObject userInfo) {
        try {
            userDao.updateUser(userInfo);
            Long roleId = userDao.getRoleIdByName(userInfo.getString("role"));

            saveUserRole(userInfo.getLong("id"), roleId);
            return new JsonResult("0", "修改成功！");
        } catch (Exception e) {
            log.error("修改用户失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.DATABASE_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public JsonResult addUser(@RequestBody JSONObject userInfo) {
        try {
            userInfo.put("password", passwordEncoder.encode(userInfo.getString("password")));
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userInfo.put("status", Status.VALID);
//            user.setStatus(Status.VALID);
            userDao.addUser(userInfo);
            Long roleId = userDao.getRoleIdByName(userInfo.getString("role"));
            saveUserRole(userInfo.getLong("id"), roleId);

            log.info("新增用户{}", userInfo.getString("nickname"));
            return new JsonResult("0", "新增用户成功！");
        } catch (Exception e) {
            log.error("新增用户失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.DATABASE_EXCEPTION);
        }
    }

    @Override
    public JsonResult deleteUser(Long userId) {
        try {
            userDao.deleteUser(userId);
            userDao.deleteUserRole(userId);
        } catch (Exception e) {
            log.error("删除用户失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.DATABASE_EXCEPTION);
        }
        return null;
    }

    @Override
    public JsonResult searchUser(JSONObject request) {
        try {
            int pageNum = request.getInteger("pageNum");
            int pageSize = request.getInteger("pageSize");
            PageHelper.startPage(pageNum, pageSize);

            PageInfo<CaseInfo> pageInfo = new PageInfo(userDao.searchUser(request));
            return new JsonResult(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("查询用户失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.DATABASE_EXCEPTION);
        }
    }

    private void saveUserRoles(Long userId, List<Long> roleIds) {
        if (roleIds != null) {
            userDao.deleteUserRole(userId);
            if (!CollectionUtils.isEmpty(roleIds)) {
                userDao.saveUserRoles(userId, roleIds);
            }
        }
    }

    private void saveUserRole(Long userId, Long roleId) {
        userDao.saveUserRole(userId, roleId);
    }

    @Override
    public SysUser getUser(String username) {
        return userDao.getUser(username);
    }

    @Override
    public JsonResult changePassword(JSONObject userPasswordInfo) {
        String passwordOld = userPasswordInfo.getString("passwordOld");
        String passwordNew = userPasswordInfo.getString("passwordNew");
        String passwordConfirm = userPasswordInfo.getString("passwordConfirm");

        if (!passwordNew.equals(passwordConfirm)) {
            return new JsonResult<>("400", "新密码和确认密码不一致！");
        }

        SysUser user = UserUtil.getLoginUser();
        if (user == null) {
            return new JsonResult<>("400", "用户不存在！");
        }

        String username = UserUtil.getLoginUser().getUsername();
        if (!passwordEncoder.matches(passwordOld, user.getPassword())) {
            return new JsonResult<>("400", "旧密码错误！");
        }

        userDao.changePassword(user.getId(), passwordEncoder.encode(passwordNew));
        log.debug("修改{}的密码", username);
        return new JsonResult<>("0", "修改成功！");
    }
}
