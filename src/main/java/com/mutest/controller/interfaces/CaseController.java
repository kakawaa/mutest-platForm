package com.mutest.controller.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.mutest.annotation.interfaces.GroupPermissions;
import com.mutest.annotation.interfaces.ProjectPermissions;
import com.mutest.model.JsonResult;
import com.mutest.service.interfaces.CaseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/5/9 15:27
 * @description 用例接口
 * @modify
 */
@RestController
@RequestMapping(value = "/interface")
public class CaseController {
    @Autowired
    private CaseService caseService;

    @RequestMapping(value = "/caseList", method = RequestMethod.GET)
    public JsonResult getCaseList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @Param("projectId") Long projectId, @Param("interfaceId") Long interfaceId) {
        return caseService.getCaseList(pageNum, pageSize, projectId, interfaceId);
    }

    /**
     * 根据id获取用例详情
     *
     * @param id 用例id
     * @return 用例详情列表
     */
    @RequestMapping(value = "/caseInfo", method = RequestMethod.GET)
    public JsonResult getCaseListById(@RequestParam("id") Long id) {
        return caseService.getCaseInfoById(id);
    }

    /**
     * 添加用例
     *
     * @param request 前端信息
     * @return
     */
    @ProjectPermissions
    @RequestMapping(value = "/addCase", method = RequestMethod.POST)
    public JsonResult addCase(@RequestBody JSONObject request) {
        return caseService.addCase(request);
    }

    /**
     * 更新用例详情
     *
     * @param caseInfo 详情页信息
     * @return 更新结果
     */
    @ProjectPermissions
    @GroupPermissions
    @RequestMapping(value = "/updateCaseInfo", method = RequestMethod.POST)
    public JsonResult updateCaseInfo(@RequestBody JSONObject caseInfo) {
        return caseService.updateCaseInfo(caseInfo);
    }

    /**
     * 更新用例首页信息
     *
     * @param caseMainInfo 用例首页信息
     * @return 更新结果
     */
    @ProjectPermissions
    @GroupPermissions
    @RequestMapping(value = "/updateCaseMain", method = RequestMethod.POST)
    public JsonResult updateCaseMain(@RequestBody JSONObject caseMainInfo) {
        return caseService.updateCaseMain(caseMainInfo);
    }

    /**
     * 搜索用例
     *
     * @param request 搜索条件
     * @return
     */
    @RequestMapping(value = "searchCase", method = RequestMethod.POST)
    public JsonResult searchCase(@RequestBody JSONObject request) {
        return caseService.searchCase(request);
    }

    /**
     * 执行用例
     *
     * @param request 执行信息
     * @return
     */
    @RequestMapping(value = "/executeCase", method = RequestMethod.POST)
    public JsonResult executeCase(@RequestBody JSONObject request) {
        return caseService.executeCase(request);
    }

    /**
     * 获取创建者
     *
     * @param projectName   项目
     * @param moduleName    模块
     * @param interfaceName 接口名称
     * @return
     */
    @RequestMapping(value = "/getCreators", method = RequestMethod.GET)
    public List<String> getCreators(String projectName, String moduleName, String interfaceName) {
        return caseService.getCreators(projectName, moduleName, interfaceName);
    }

    /**
     * 获取用例描述
     *
     * @param request 请求信息
     * @return
     */
    @RequestMapping(value = "/getCaseDesc", method = RequestMethod.POST)
    public JsonResult getCaseDesc(@RequestBody JSONObject request) {
        return caseService.getCaseFeature(request);
    }

    /**
     * 根据用例描述获取用例
     *
     * @param caseDesc 用例描述
     * @return
     */
    @RequestMapping(value = "/getCaseByDesc", method = RequestMethod.GET)
    public JsonResult getCaseByDesc(@RequestParam String caseDesc) {
        return caseService.getCaseByDesc(caseDesc);
    }

    /**
     * 根据用例id删除用例
     *
     * @param caseId 用例id
     * @return
     */
    @RequestMapping(value = "/deleteCaseById", method = RequestMethod.GET)
    public JsonResult deleteCaseById(@RequestParam("caseId") int caseId) {
        return caseService.deleteCaseById(caseId);
    }
}
