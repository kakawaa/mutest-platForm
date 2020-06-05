package com.mutest.service.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.JsonResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CaseService {
    JsonResult getCaseList(int pageNum, int pageSize, Long projectId, Long interfaceId);

    JsonResult getCaseInfoById(Long id);

    JsonResult updateCaseInfo(JSONObject request);

    JsonResult updateCaseMain(JSONObject request);

    JsonResult addCase(JSONObject request);

    JsonResult searchCase(JSONObject request);

    JsonResult executeCase(JSONObject request);

    List<String> getCreators(String projectName, String moduleName, String interfaceName);

    JsonResult getCaseFeature(JSONObject request);

    JsonResult getCaseByDesc(String caseDesc);

    JsonResult deleteCaseById(int caseId);
}
