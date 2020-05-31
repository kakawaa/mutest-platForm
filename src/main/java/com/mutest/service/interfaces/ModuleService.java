package com.mutest.service.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.JsonResult;
import org.springframework.stereotype.Service;

@Service
public interface ModuleService {
    JsonResult getModuleList(int pageNum, int pageSize);

    JsonResult searchModule(JSONObject request);

    JsonResult addModule(JSONObject request);

    JsonResult updateModule(JSONObject request);
}
