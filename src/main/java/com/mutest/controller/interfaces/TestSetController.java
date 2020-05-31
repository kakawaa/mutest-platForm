package com.mutest.controller.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.mutest.dao.base.InterfaceTestSetDao;
import com.mutest.model.JsonResult;
import com.mutest.model.interfaces.TestSetInfo;
import com.mutest.service.interfaces.TestSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/7/12 11:27
 * @description
 * @modify
 */
@RestController
@RequestMapping(value = "/interface")
public class TestSetController {
    @Autowired
    private TestSetService testSetService;
    @Resource
    private InterfaceTestSetDao interfaceTestSetDao;

    @RequestMapping(value = "/testSetList", method = RequestMethod.GET)
    public JsonResult getTestSetList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return testSetService.getTestSetList(pageNum, pageSize);
    }

    @RequestMapping(value = "/searchTestSet", method = RequestMethod.POST)
    public JsonResult searchTestSet(@RequestBody JSONObject request) {
        return testSetService.searchTestSet(request);
    }

    @RequestMapping(value = "/executeTestSet", method = RequestMethod.POST)
    public JsonResult executeTestSet(@RequestBody JSONObject request) {
        Long testSetId = request.getLong("testSetId");
        TestSetInfo testSetInfo = interfaceTestSetDao.getTestSetById(testSetId);
        return testSetService.executeTestSet(testSetInfo);
    }

    @RequestMapping(value = "/executeAllTestSet", method = RequestMethod.GET)
    public JSONObject executeAllTestSet() {
        JSONObject result = new JSONObject();
        List<TestSetInfo> testSetInfoList = interfaceTestSetDao.getTestSetList();
        for (TestSetInfo testSetInfo : testSetInfoList) {
            testSetService.executeTestSet(testSetInfo);
        }
        result.put("code", 0);
        result.put("message", "执行完成！");

        return result;
    }

    @RequestMapping(value = "/updateTestSet", method = RequestMethod.POST)
    public JsonResult updateTestSet(@RequestBody JSONObject request) {
        return testSetService.updateTestSet(request);
    }

    @RequestMapping(value = "/testSetResultList", method = RequestMethod.GET)
    public JsonResult getTestSetResultList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return testSetService.getTestSetResultList(pageNum, pageSize);
    }

    /**
     * 按条件搜索测试集合执行结果
     *
     * @param request 前端搜索条件
     * @return
     */
    @RequestMapping(value = "/searchTestSetResult", method = RequestMethod.POST)
    public JsonResult searchTestSetResult(@RequestBody JSONObject request) {
        return testSetService.searchTestSetResult(request);
    }

    @RequestMapping(value = "/caseResultList", method = RequestMethod.GET)
    public JsonResult getCaseResultList(@RequestParam("executeId") String executeId) {
        return testSetService.getCaseResultList(executeId);
    }

    @RequestMapping(value = "/getTestSetById", method = RequestMethod.GET)
    public JsonResult getTestSetById(@RequestParam("testSetId") Long testSetId) {
        return testSetService.getTestSetById(testSetId);
    }
}
