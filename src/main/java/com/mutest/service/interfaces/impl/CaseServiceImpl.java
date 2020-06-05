package com.mutest.service.interfaces.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mutest.advice.BusinessErrorException;
import com.mutest.advice.BusinessMsgEnum;
import com.mutest.dao.base.InterfaceCaseDao;
import com.mutest.dao.base.InterfaceListDao;
import com.mutest.dao.base.InterfaceTestSetDao;
import com.mutest.model.JsonResult;
import com.mutest.model.interfaces.CaseInfo;
import com.mutest.model.interfaces.TestSetInfo;
import com.mutest.service.interfaces.CaseService;
import com.mutest.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/5/9 14:46
 * @description
 * @modify
 */
@Service(value = "CaseService")
@Slf4j
public class CaseServiceImpl implements CaseService {
    @Resource
    private InterfaceCaseDao interfaceCaseDao;
    @Resource
    private InterfaceListDao interfaceListDao;
    @Resource
    private InterfaceTestSetDao interfaceTestSetDao;

    /**
     * 获取用例列表
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return
     */
    @Override
    public JsonResult getCaseList(int pageNum, int pageSize, Long projectId, Long interfaceId) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<CaseInfo> pageInfo = new PageInfo(interfaceCaseDao.getCaseList(projectId, interfaceId));
            return new JsonResult<>(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("获取用例列表失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    /**
     * 根据用例id获取用例详情
     *
     * @param id 用例id
     * @return 用例详情
     */
    @Override
    public JsonResult getCaseInfoById(Long id) {
        try {
            JSONObject data = new JSONObject();
            CaseInfo caseInfo = interfaceCaseDao.getCaseInfoById(id);

            JSONArray header = JSON.parseArray(caseInfo.getHeaderData());
            JSONArray body = JSON.parseArray(caseInfo.getBodyData());
            JSONArray correlation = JSON.parseArray(caseInfo.getCorrelation());
            JSONArray assertion = JSON.parseArray(caseInfo.getAssertion());

            data.put("caseInfo", caseInfo);
            data.put("header", header);
            data.put("body", body);
            data.put("correlation", correlation);
            data.put("assertion", assertion);
            return new JsonResult<>(data);
        } catch (Exception e) {
            log.error("获取用例详情失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    /**
     * 修改接口用例详情
     *
     * @param request 前端请求
     * @return
     */
    @Override
    public JsonResult updateCaseInfo(JSONObject request) {
        try {
            String headerData = removeNull(request.getJSONArray("headerData")).toString();
            String bodyData = removeNull(request.getJSONArray("bodyData")).toString();
            String correlation = removeNull(request.getJSONArray("correlation")).toString();
            String assertion = removeNull(request.getJSONArray("assertion")).toString();

            request.put("headerData", headerData);
            request.put("bodyData", bodyData);
            request.put("correlation", correlation);
            request.put("assertion", assertion);

            try {
                interfaceCaseDao.updateCaseInfo(request);
                return new JsonResult<>("0", "操作成功！");
            } catch (Exception e) {
                log.error("修改用例详情，update执行失败：" + e.getMessage());
                throw new BusinessErrorException(BusinessMsgEnum.DATABASE_EXCEPTION);
            }
        } catch (Exception e) {
            log.error("修改用例详情失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.NULLPOINTER_EXCEPTION);
        }
    }

    /**
     * 修改用例首页
     *
     * @param request 前端请求
     * @return
     */
    @Override
    public JsonResult updateCaseMain(JSONObject request) {
        try {
            String projectName = request.getString("projectName");
            String moduleName = request.getString("moduleName");
            String interfaceName = request.getString("interfaceName");
            Long caseId = request.getLong("id");
            String caseType = request.getString("caseType");
            String description = request.getString("description");
            int delay = request.getInteger("delay");

            // 项目名称、模块名称获取模块id
//            Long moduleId = interfaceModuleDao.getModuleIdByName(projectName, moduleName);
            // 通过项目名称、模块名称和接口名称获取接口id
            Long interfaceId = interfaceListDao.getInterfaceIdByNames(projectName, moduleName, interfaceName);
            interfaceCaseDao.updateCaseMain(interfaceId, caseType, delay, description, caseId);
            return new JsonResult<>("0", "操作成功！");
        } catch (Exception e) {
            log.error("修改用例首页失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    /**
     * 新增接口测试用例
     *
     * @param request 前端请求
     * @return
     */
    @Override
    public JsonResult addCase(JSONObject request) {
        try {
            String projectName = request.getString("projectName");
            String moduleName = request.getString("moduleName");
            String interfaceName = request.getString("interfaceName");
            String caseType = request.getString("caseType");
            int delay = request.getInteger("delay");
            String creator = UserUtil.getLoginUser().getNickname();
            String description = request.getString("description");

            Long interfaceId = interfaceListDao.getInterfaceIdByNames(projectName, moduleName, interfaceName);

            // 查询接口下标准用例的数量
            int caseCount = interfaceCaseDao.getCaseCount(interfaceId);
            if (caseType.equals("标准用例") && caseCount > 0)
                return new JsonResult<>("400", "该接口已存在标准用例，请勿重复添加！");

            String headerData = "[]";
            String bodyData = "[]";
            String correlation = "[]";
            String assertion = "[]";
            String resultDemo = "";
            String shellStatus = "0";
            String assertionShell = "";

            // 存在标准用例，则将信息复制过来
            if (caseCount > 0) {
                CaseInfo caseInfo = interfaceCaseDao.getStandardCaseByInterfaceId(interfaceId);
                headerData = caseInfo.getHeaderData();
                bodyData = caseInfo.getBodyData();
                correlation = caseInfo.getCorrelation();
                assertion = caseInfo.getAssertion();
                resultDemo = caseInfo.getResultDemo();
                shellStatus = caseInfo.getShellStatus();
                assertionShell = caseInfo.getAssertionShell();

                if (headerData.equals(""))
                    headerData = "[]";
                if (bodyData.equals(""))
                    bodyData = "[]";
                if (correlation.equals(""))
                    correlation = "[]";
                if (assertion.equals(""))
                    assertion = "[{\"assertRule\":\"选择断言规则\",\"expectedValue\":\"填入预期值\",\"jsonPath\":\"做JSON校验时填此项\"}]";
            }

            request.put("interfaceId", interfaceId);
            request.put("headerData", headerData);
            request.put("bodyData", bodyData);
            request.put("correlation", correlation);
            request.put("assertion", assertion);
            request.put("shellStatus", shellStatus);
            request.put("assertionShell", assertionShell);
            request.put("resultDemo", resultDemo);


            CaseInfo caseInfo = new CaseInfo();
            caseInfo.setInterfaceId(interfaceId);
            caseInfo.setCaseType(caseType);
            caseInfo.setCreator(creator);
            caseInfo.setDelay(delay);
            caseInfo.setHeaderData(headerData);
            caseInfo.setBodyData(bodyData);
            caseInfo.setDescription(description);
            caseInfo.setAssertion(assertion);
            caseInfo.setCorrelation(correlation);
            caseInfo.setAssertionShell(assertionShell);
            caseInfo.setShellStatus(shellStatus);
            caseInfo.setAssertionShell(assertionShell);
            caseInfo.setResultDemo(resultDemo);

            interfaceCaseDao.addCase(caseInfo);

            return new JsonResult<>(caseInfo);
//            return new JsonResult<>("0","新增成功！");
        } catch (Exception e) {
            log.error("获取用例列表失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    /**
     * 按条件搜索用例
     *
     * @param request 前端请求
     * @return
     */
    @Override
    public JsonResult searchCase(JSONObject request) {
        try {
            int pageNum = request.getInteger("pageNum");
            int pageSize = request.getInteger("pageSize");
            PageHelper.startPage(pageNum, pageSize);

            PageInfo<CaseInfo> pageInfo = new PageInfo(interfaceCaseDao.searchCase(request));
            return new JsonResult<>(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("搜索用例失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    /**
     * 执行接口用例
     *
     * @param request 前端请求
     * @return
     */
    @Override
    public JsonResult executeCase(JSONObject request) {
        Map<String, String> correlations = new HashMap<>();
        TestSetServiceImpl sceneService = new TestSetServiceImpl();
        Long caseId = request.getLong("caseId");
        // 根据用例id获取用例详情
        CaseInfo caseInfo = interfaceCaseDao.getCaseInfoById(caseId);
        return sceneService.executeCaseById(correlations, caseInfo);
    }

    /**
     * 根据接口信息获取创建者
     *
     * @param project       项目
     * @param module        模块
     * @param interfaceName 接口
     * @return 去重的创建者
     */
    @Override
    public List<String> getCreators(String project, String module, String interfaceName) {
        try {
            return interfaceCaseDao.getCreators(project, module, interfaceName);
        } catch (Exception e) {
            log.error("获取用例创建者失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    /**
     * 获取用例的特征：aseId + "-" + caseType + "-" + creator + "-" + description
     *
     * @param request 前端请求
     * @return
     */
    @Override
    public JsonResult getCaseFeature(JSONObject request) {
        try {
            List<CaseInfo> caseInfoList = interfaceCaseDao.getCaseFeature(request);
            List<String> caseFeatureList = new ArrayList<>();
            for (CaseInfo caseInfo : caseInfoList) {
                Long caseId = caseInfo.getId();
                String description = caseInfo.getDescription();
                caseFeatureList.add(caseId + "-" + caseInfo.getCaseType() + "-" + caseInfo.getCreator() + "-" + description);
            }
            return new JsonResult<>(caseFeatureList);
        } catch (Exception e) {
            log.error("获取用例特征失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    /**
     * 根据用例特征获取用例
     *
     * @param caseDesc 用例特征
     * @return
     */
    @Override
    public JsonResult getCaseByDesc(String caseDesc) {
        try {
            String[] caseIds = caseDesc.split("-");
            CaseInfo caseInfo = interfaceCaseDao.getCaseInfoById(Long.parseLong(caseIds[0]));
            return new JsonResult<>(caseInfo);
        } catch (Exception e) {
            log.error("根据用例特征获取用例：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    /**
     * 根据用例id删除测试用例并删除含有该用例的测试集合
     *
     * @param caseId 测试用例id
     * @return
     */
    public JsonResult deleteCaseById(int caseId) {
        try {
            // 获取所有的测试用例id队列
            List<TestSetInfo> testSetInfos = interfaceTestSetDao.getTestSetList();
            List<Long> testSetIds = new ArrayList<>();

            for (TestSetInfo testSetInfo : testSetInfos) {
                String caseIdQueue = "-" + testSetInfo.getCaseIdQueue() + "-";
                if (caseIdQueue.contains("-" + caseId + "-")) {
                    testSetIds.add(testSetInfo.getId());
                }
            }
            // 根据集合id批量删除测试集合
            interfaceTestSetDao.deleteTestSetBatch(testSetIds);
            // 根据用例id单个删除测试用例
            interfaceCaseDao.deleteCaseById(caseId);
            return new JsonResult<>("0", "操作成功");
        } catch (Exception e) {
            log.error("删除用例失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    public JSONArray removeNull(JSONArray array) {
        for (int i = array.size() - 1; i >= 0; i--) {
            if (array.getString(i).contains("[]"))
                array.remove(i);
        }
        return array;
    }
}
