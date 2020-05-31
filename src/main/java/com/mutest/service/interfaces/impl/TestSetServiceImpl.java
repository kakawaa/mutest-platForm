package com.mutest.service.interfaces.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mutest.advice.BusinessErrorException;
import com.mutest.advice.BusinessMsgEnum;
import com.mutest.dao.base.InterfaceCaseDao;
import com.mutest.dao.base.InterfaceProjectDao;
import com.mutest.dao.base.InterfaceTestSetDao;
import com.mutest.model.JsonResult;
import com.mutest.model.interfaces.CaseInfo;
import com.mutest.model.interfaces.CaseResult;
import com.mutest.model.interfaces.TestSetInfo;
import com.mutest.model.interfaces.TestSetResult;
import com.mutest.service.interfaces.TestSetService;
import com.mutest.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/7/11 20:42
 * @description
 * @modify
 */
@Service(value = "TestSetService")
@Slf4j
public class TestSetServiceImpl implements TestSetService {
    @Resource
    private InterfaceTestSetDao interfaceTestSetDao;
    @Resource
    private InterfaceCaseDao interfaceCaseDao;
    @Resource
    private InterfaceProjectDao interfaceProjectDao;

    /**
     * 查询测试场景集合
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult getTestSetList(int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<TestSetInfo> pageInfo = new PageInfo(interfaceTestSetDao.getTestSetList());
            return new JsonResult<>(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("获取测试集合列表失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    /**
     * 根据条件搜索测试集合
     *
     * @param request 搜索条件
     * @return
     */
    @Override
    public JsonResult searchTestSet(JSONObject request) {
        try {
            int pageNum = request.getInteger("pageNum");
            int pageSize = request.getInteger("pageSize");
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<TestSetInfo> pageInfo = new PageInfo(interfaceTestSetDao.searchTestSet(request));
            return new JsonResult<>(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("获取测试集合列表失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    /**
     * 编辑测试集合
     *
     * @param request 前端请求信息
     * @return
     */
    @Override
    public JsonResult updateTestSet(JSONObject request) {
        System.out.println("request:--------------------------" + request);
        try {
            String projectName = request.getString("projectName");
            Long projectId = interfaceProjectDao.getProjectIdByName(projectName);
            String description = request.getString("description");
            Long testSetId = request.getLong("id");

            JSONArray caseInfoList = ParamHandler.removeNull(request.getJSONArray("casesInfo"));
            // caseId队列
            StringBuilder caseIdQueueIni = new StringBuilder();
            // caseName队列
            StringBuilder caseNameQueueIni = new StringBuilder();

            for (int i = 0; i < caseInfoList.size(); i++) {
                JSONObject caseInfo = caseInfoList.getJSONObject(i);
                int id = caseInfo.getInteger("id");
                String interfaceName = caseInfo.getString("interfaceName");
                if (id > 0) {
                    caseIdQueueIni.append(id).append("-");
                    caseNameQueueIni.append(interfaceName).append("-");
                }
            }
            if (caseIdQueueIni.length() <= 0 || caseNameQueueIni.length() <= 0) {
                log.error("编辑测试集合失败：未添加用例");
                return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.NULLCASE_EXCEPTION));
            }

            String caseIdQueue = caseIdQueueIni.substring(0, caseIdQueueIni.length() - 1);
            String caseNameQueue = caseNameQueueIni.substring(0, caseNameQueueIni.length() - 1);

            // 判重
            int count;
            // 前端传入的testSetId如果是正数，说明是编辑，否则是新增
            if (testSetId > 0) {
                count = interfaceTestSetDao.testSetUpdateCount(testSetId, projectId, caseIdQueue);
                if (count > 0)
                    return new JsonResult<>("400", "该测试集合已存在，请勿重复添加！");
                interfaceTestSetDao.updateTestSet(projectId, caseIdQueue, caseNameQueue, description, testSetId);
            } else {
                count = interfaceTestSetDao.testSetInsertCount(projectId, caseIdQueue);
                if (count > 0)
                    return new JsonResult<>("400", "该测试集合已存在，请勿重复添加！");
                String creator = UserUtil.getLoginUser().getNickname();
                interfaceTestSetDao.addTestSet(projectId, caseIdQueue, caseNameQueue, creator, description);
            }
            return new JsonResult<>("0", "操作成功！");
        } catch (Exception e) {
            log.error("编辑测试集合失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    /**
     * 执行一条测试集合
     *
     * @param testSetInfo 测试集合信息
     * @return
     */
    @Override
    public JsonResult executeTestSet(TestSetInfo testSetInfo) {
        // 失败用例列表
        List<String> failedCases = new ArrayList<>();
        // 关联关系池
        Map<String, String> correlations = new HashMap<>();
        String executor = UserUtil.getLoginUser().getNickname();
        String caseIdQueue;
        Long testSetId;
        String[] caseIdArr;
        try {
            caseIdQueue = testSetInfo.getCaseIdQueue();
            testSetId = testSetInfo.getId();
            caseIdArr = caseIdQueue.split("-");
        } catch (Exception e) {
            log.error("执行测试集合-解析测试集合信息失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }

        // 以当前时间戳+随机数为执行id
        long executeId = System.currentTimeMillis() + new Random().nextInt(10);
        if (caseIdArr.length > 0) {
            StringBuilder saveCaseResultSql = new StringBuilder();
            for (String caseId : caseIdArr) {
                String host = " error host";
                String path = " error path";
                String interfaceName = " error interfaceName";
                try {
                    CaseInfo caseInfo = interfaceCaseDao.getCaseInfoById(Long.parseLong(caseId));
                    interfaceName = caseInfo.getInterfaceName();
                    host = caseInfo.getUrl();
                    path = caseInfo.getPath();

                    JsonResult caseResult = executeCaseById(correlations, caseInfo);
                    if (caseResult.getCode() != null || caseResult.getCode().equals("0")) {
                        JSONObject caseData = (JSONObject) executeCaseById(correlations, caseInfo).getData();
                        String caseAssertion = caseData.getString("caseAssertion");
                        String caseRequest = caseData.getString("caseRequest");
                        String headerData = caseData.getString("headerData");
                        String bodyData = caseData.getString("bodyData");
                        String caseResponse = caseData.getString("caseResponse");
                        String assertionDetail = caseData.getString("assertionDetail");

                        // 对响应结果做长度处理
                        if (caseResponse.length() > 200) {
                            caseResponse = caseResponse.substring(0, 180);
                        }

                        // 拼接用例执行结果
                        saveCaseResultSql.append("('" + executeId + "','" + caseId + "','" + interfaceName + "','" + caseRequest + "','" + headerData
                                + "','" + bodyData + "','" + caseResponse + "','" + caseAssertion + "','" + assertionDetail + "'),");

                        if (caseAssertion.contains("失败")) {
                            failedCases.add(caseId);
                        }
                    } else {
                        failedCases.add(caseId);
                        // 拼接用例执行结果
                        saveCaseResultSql.append("('" + executeId + "','" + caseId + "','" + interfaceName + "','" + host + path + "','Error headerData','Error bodyData','Error','caseAssertion','Error'),");
                    }
                } catch (Exception e) {
                    failedCases.add(caseId);
                    // 拼接用例执行结果
                    saveCaseResultSql.append("('" + executeId + "','" + caseId + "','" + interfaceName + "','" + host + path + "','Error headerData','Error bodyData','Error caseResponse','Error caseAssertion','Error detail'),");
                }
            }
            interfaceTestSetDao.saveCaseResult(saveCaseResultSql.substring(0, saveCaseResultSql.length() - 1));
        }
        int caseCount = caseIdArr.length;
        int failedCount = failedCases.size();
        interfaceTestSetDao.savaTestSetResult(testSetId, String.valueOf(executeId), caseCount, failedCount, failedCases.toString(), executor);
        return new JsonResult<>("0", "操作成功！");
    }

    /**
     * 根据用例id执行一条测试用例
     *
     * @param correlations 关联信息池
     * @param caseInfo     用例信息
     * @return
     */
    public JsonResult executeCaseById(Map<String, String> correlations, CaseInfo caseInfo) {
        int delay;
        String interfaceName;
        String host;
        String path;
        String headerData;
        String bodyData;
        String paramType;
        String method;
        String shellStatus;
        String correlation;
        String assertionIni;
        String assertionShell;
        try {
            delay = caseInfo.getDelay();
            interfaceName = caseInfo.getInterfaceName();
            host = caseInfo.getUrl();
            path = caseInfo.getPath();
            headerData = caseInfo.getHeaderData();
            bodyData = caseInfo.getBodyData();
            paramType = caseInfo.getParamType();
            method = caseInfo.getMethod();
            shellStatus = caseInfo.getShellStatus();
            correlation = caseInfo.getCorrelation();
            assertionIni = caseInfo.getAssertion();
            assertionShell = caseInfo.getAssertionShell();
        } catch (Exception e) {
            log.error("执行用例时获取用例要素失败：" + e.getMessage());
            return new JsonResult<>("10091", "解析用例要素失败！");
        }

        // 延迟
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            log.error("用例执行-前置延迟执行失败：" + e.getMessage());
        }
        // 默认使用标准响应结果
        String caseResponse;
        // 处理参数
        Map<String, String> header;
        String body;
        try {
            header = ParamHandler.arrayToMap(correlations, headerData, "name", "value");
            body = ParamHandler.arrayToString(correlations, bodyData, "name", "value");
        } catch (Exception e) {
            log.error("用例执行-header或body解析失败：" + e.getMessage());
            return new JsonResult<>("4001", "header或body解析失败！");
        }

        if (paramType != null) {
            if (paramType.equals("form")) {
                if (method.equals("GET")) {
                    if (body.length() > 0) {
                        caseResponse = URLConnection.httpGet(host + path + "?" + body, header);
                    } else {
                        caseResponse = URLConnection.httpGet(host + path, header);
                    }
                } else {
                    caseResponse = URLConnection.httpPost(host + path, body, header);
                }
            } else {
                JSONObject jsonBody = ParamHandler.arrayToJson(correlations, bodyData, "name", "value");
                caseResponse = URLConnection.httpPost(host + path, jsonBody, header);
            }
        } else {
            log.error("用例执行-paramType为null");
            return new JsonResult<>("4001", "paramType为空！");
        }
        // 关联信息有具体值时，才执行处理方法
        try {
            ParamHandler.correlationHandler(correlations, correlation, caseResponse);
        } catch (Exception e) {
            log.error("用例执行-处理关联关系失败：" + e.getMessage());
        }
        /**
         * 断言部分，分为常规断言与beanshell断言
         */
        // 初始断言
        String caseAssertion = "未校验";
        int failCount = 0;
        int passCount = 0;
        // 断言详情
        StringBuilder assertionDetail = new StringBuilder();

        JSONArray assertionArr;
        try {
            assertionArr = JSON.parseArray(assertionIni);
        } catch (Exception e) {
            assertionArr = JSON.parseArray("[]");
        }

        // 常规断言
        String status;
        String assertRule;
        String expectedValue;

        for (int i = 0; i < assertionArr.size(); i++) {
            JSONObject assertion;
            try {
                assertion = assertionArr.getJSONObject(i);
                expectedValue = assertion.getString("expectedValue");
                assertRule = assertion.getString("assertRule");
                status = assertion.getString("status");
            } catch (Exception e) {
                log.error("用例执行-第" + i + "条常规断言解析失败：" + e.getMessage());
                assertionDetail.append("第" + i + "条常规断言解析失败！<br/>");
                continue;
            }

            if (status != null) {
                if (status.equals("Y")) {
                    // 根据断言规则进行校验
                    if (assertRule.equals("包含")) {
                        if (!caseResponse.contains(expectedValue)) {
                            failCount++;
                            assertionDetail.append("包含断言失败，预期值：" + expectedValue + "<br/>");
                        } else {
                            passCount++;
                            assertionDetail.append("包含断言成功，预期值：" + expectedValue + "<br/>");
                        }
                    } else if (assertRule.equals("等于")) {
                        if (!caseResponse.equals(expectedValue)) {
                            failCount++;
                            assertionDetail.append("等于断言失败，预期值：" + expectedValue + "<br/>");
                        } else {
                            passCount++;
                            assertionDetail.append("等于断言成功，预期值：" + expectedValue + "<br/>");
                        }
                    } else {
                        String jsonPath = assertion.getString("jsonPath");
                        String actualValue = JSONExtractor.jsonExtractor(caseResponse, jsonPath);
                        if (!actualValue.equals(expectedValue)) {
                            failCount++;
                            assertionDetail.append("JSON断言失败，预期值：" + expectedValue + "<br/>");
                        } else {
                            passCount++;
                            assertionDetail.append("JSON断言成功，预期值：" + expectedValue + "<br/>");
                        }
                    }
                }
            }
        }

        // beanshell断言
        // 1代表beanshell断言校验开启
        if (shellStatus != null) {
            if (shellStatus.equals("1") && assertionShell.length() > 0) {
                String assertByScript = "n";
                try {
                    assertByScript = ResponseUtil.assertByScript(assertionShell, caseResponse);
                } catch (Exception e) {
                    log.error("用例执行-Beanshell断言解析失败：" + e.getMessage());
                    assertionDetail.append("Beanshell断言解析失败<br/>");
                }

                if (assertByScript.contains("y")) {
                    assertionDetail.append("Beanshell断言成功<br/>");
                    passCount++;
                } else if (assertByScript.contains("n")) {
                    failCount++;
                    assertionDetail.append("Beanshell断言失败<br/>");
                } else {
                    assertionDetail.append("Beanshell断言错误<br/>");
                }
            }
        } else {
            log.error("用例执行-shellStatus为null");
        }

        if (assertionDetail.length() == 0) {
            assertionDetail.append("无断言信息");
        }

        if (failCount > 0) {
            caseAssertion = "失败";
        } else if (failCount == 0 && passCount > 0) {
            caseAssertion = "成功";
        }

        StringBuilder headerStr = new StringBuilder();
        for (Map.Entry<String, String> entry : header.entrySet()) {
            headerStr.append(entry.getKey() + ":" + entry.getValue() + "<br/>");
        }

        JSONObject data = new JSONObject();
        data.put("interfaceName", interfaceName);
        data.put("caseRequest", method + "  " + host + path + "  " + interfaceName);
        data.put("headerData", headerStr);
        data.put("bodyData", body);
        data.put("caseResponse", caseResponse);
        data.put("caseAssertion", caseAssertion);
        data.put("assertionDetail", assertionDetail);

        return new JsonResult<>(data);
    }

    /**
     * 获取测试集合执行结果列表
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return
     */
    @Override
    public JsonResult getTestSetResultList(int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<TestSetResult> pageInfo = new PageInfo(interfaceTestSetDao.getTestSetResultList());
            return new JsonResult<>(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    /**
     * 搜索测试集合结果
     *
     * @param request 前端查询条件
     * @return
     */
    @Override
    public JsonResult searchTestSetResult(JSONObject request) {
        try {
            int pageNum = request.getInteger("pageNum");
            int pageSize = request.getInteger("pageSize");
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<TestSetInfo> pageInfo = new PageInfo(interfaceTestSetDao.searchTestSetResult(request));
            return new JsonResult<>(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("搜索测试集合失败，搜索条件：" + request + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    /**
     * 获取用例执行结果列表
     *
     * @param executeId 执行编号
     * @return
     */
    @Override
    public JsonResult getCaseResultList(String executeId) {
        try {
            List<CaseResult> caseResults = interfaceTestSetDao.getCaseResultList(executeId);
            return new JsonResult<>(caseResults, "操作成功！", caseResults.size());
        } catch (Exception e) {
            log.error("获取测试集合" + executeId + "下的测试用例结果失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    /**
     * 根据id获取测试集合
     *
     * @param testSetId 测试集合id
     * @return
     */
    @Override
    public JsonResult getTestSetById(Long testSetId) {
        try {
            TestSetInfo testSetInfo = interfaceTestSetDao.getTestSetById(testSetId);
            String caseIdQueue = testSetInfo.getCaseIdQueue();
            String[] caseIds = caseIdQueue.split("-");
            JSONArray caseInfoList = new JSONArray();
            for (String caseId : caseIds) {
                CaseInfo caseInfo = interfaceCaseDao.getCaseMainById(Long.parseLong(caseId));
                caseInfoList.add(caseInfo);
            }
            JSONObject data = new JSONObject();
            data.put("caseInfoList", caseInfoList);
            data.put("testSetInfo", testSetInfo);
            return new JsonResult<>(data);
        } catch (Exception e) {
            log.error("获取测试集合" + testSetId + "失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }
}
