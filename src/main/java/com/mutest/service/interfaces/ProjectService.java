package com.mutest.service.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.JsonResult;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    JsonResult getProjectList(int pageNum, int pageSize);

    JsonResult updateProject(JSONObject request);

    JsonResult addProject(JSONObject request);

    JsonResult projectStructure(String projectName);

    JsonResult projectSync();
}
