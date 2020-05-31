package com.mutest.service.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.JsonResult;
import com.mutest.model.interfaces.TestSetInfo;
import org.springframework.stereotype.Service;

@Service
public interface TestSetService {
    JsonResult getTestSetList(int pageNum, int pageSize);

    JsonResult searchTestSet(JSONObject request);

    JsonResult updateTestSet(JSONObject request);

    JsonResult executeTestSet(TestSetInfo testSetInfo);

    JsonResult getTestSetResultList(int pageNum, int pageSize);

    JsonResult searchTestSetResult(JSONObject request);

    JsonResult getCaseResultList(String executeId);

    JsonResult getTestSetById(Long testSetId);
}
