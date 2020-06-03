package com.mutest.service.interfaces.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mutest.advice.BusinessErrorException;
import com.mutest.advice.BusinessMsgEnum;
import com.mutest.dao.base.InterfaceModuleDao;
import com.mutest.dao.base.InterfaceProjectDao;
import com.mutest.model.JsonResult;
import com.mutest.model.interfaces.ModuleInfo;
import com.mutest.service.interfaces.ModuleService;
import com.mutest.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/9/4 14:30
 * @description
 * @modify
 */
@Service(value = "ModuleService")
@Slf4j
public class ModuleServiceImpl implements ModuleService {
    @Resource
    private InterfaceModuleDao interfaceModuleDao;
    @Resource
    private InterfaceProjectDao interfaceProjectDao;

    /**
     * 获取模块列表
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 模块列表
     */
    @Override
    public JsonResult getModuleList(int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<ModuleInfo> pageInfo = new PageInfo(interfaceModuleDao.getModuleList());
            return new JsonResult<>(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("获取模块列表失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    /**
     * 搜索模块
     *
     * @param moduleInfo 模块查询条件
     * @return 模块列表
     */
    @Override
    public JsonResult searchModule(JSONObject moduleInfo) {
        try {
            int pageNum = moduleInfo.getInteger("pageNum");
            int pageSize = moduleInfo.getInteger("pageSize");

            PageHelper.startPage(pageNum, pageSize);
            String projectName = moduleInfo.getString("projectName");

            PageInfo<ModuleInfo> pageInfo = new PageInfo(interfaceModuleDao.searchModule(projectName));
            return new JsonResult<>(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("搜索模块失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    /**
     * 新增模块
     *
     * @param moduleInfo 模块信息
     * @return
     */
    @Override
    public JsonResult addModule(JSONObject moduleInfo) {
        try {
            String projectName = moduleInfo.getString("projectName");
            String moduleName = moduleInfo.getString("moduleName");
            int count = interfaceModuleDao.moduleAddCount(projectName, moduleName);
            if (count > 0) {
                return new JsonResult<>("400", "该模块已存在，请勿重复添加！");
            }
            Long projectId = interfaceProjectDao.getProjectIdByName(projectName);
            moduleInfo.put("projectId", projectId);
            moduleInfo.put("creator", UserUtil.getLoginUser().getNickname());
            interfaceModuleDao.addModule(moduleInfo);
            return new JsonResult<>("0", "操作成功！");
        } catch (Exception e) {
            log.error("新增模块失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    /**
     * 修改模块信息
     *
     * @param moduleInfo 前端请求
     * @return
     */
    @Override
    public JsonResult updateModule(JSONObject moduleInfo) {
        try {
            // 更新判重与新增判重不同的是，更新判重要排除自身
            Long id = moduleInfo.getLong("id");
            String projectName = moduleInfo.getString("projectName");
            String moduleName = moduleInfo.getString("moduleName");
            int count = interfaceModuleDao.moduleUpdateCount(id, projectName, moduleName);
            if (count > 0) {
                return new JsonResult<>("400", "该模块已存在，请勿重复添加！");
            }
            interfaceModuleDao.updateModule(moduleInfo);
            return new JsonResult<>("0", "操作成功！");
        } catch (Exception e) {
            log.error("修改模块失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }
}
