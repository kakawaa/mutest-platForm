package com.mutest.service.interfaces.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mutest.advice.BusinessErrorException;
import com.mutest.advice.BusinessMsgEnum;
import com.mutest.dao.base.InterfaceModuleDao;
import com.mutest.model.JsonResult;
import com.mutest.model.interfaces.ModuleInfo;
import com.mutest.service.interfaces.ModuleService;
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
            return new JsonResult(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("获取模块列表失败：" + e.getMessage());
            return new JsonResult(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    /**
     * 搜索模块
     *
     * @param request 前端请求体
     * @return 模块列表
     */
    @Override
    public JsonResult searchModule(JSONObject request) {
        try {
            int pageNum = request.getInteger("pageNum");
            int pageSize = request.getInteger("pageSize");

            PageHelper.startPage(pageNum, pageSize);
            String platform = request.getString("platform");
            String project = request.getString("project");
            String module = request.getString("module");

            PageInfo<ModuleInfo> pageInfo = new PageInfo(interfaceModuleDao.searchModule(platform, project, module));
            return new JsonResult(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("搜索模块失败：" + e.getMessage());
            return new JsonResult(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    /**
     * 新增模块
     *
     * @param request 模块信息
     * @return
     */
    @Override
    public JsonResult addModule(JSONObject request) {
        try {
            interfaceModuleDao.addModule(request);
            return new JsonResult("0", "操作成功！");
        } catch (Exception e) {
            log.error("新增模块失败：" + e.getMessage());
            return new JsonResult(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    /**
     * 修改模块信息
     *
     * @param request 前端请求
     * @return
     */
    @Override
    public JsonResult updateModule(JSONObject request) {
        try {
            interfaceModuleDao.updateModule(request);
            interfaceModuleDao.updateHost(request);
            return new JsonResult("0", "操作成功！");
        } catch (Exception e) {
            log.error("修改模块失败：" + e.getMessage());
            return new JsonResult(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }
}
