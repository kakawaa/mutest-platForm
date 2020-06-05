package com.mutest.service.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.JsonResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InterfaceService {
    JsonResult getInterfaceList(int pageNum, int pageSize,Long projectId);

    List<String> getProject();

    List<String> getModule(String projectName);

    List<String> getInterface(String project, String module);

    JsonResult searchInterface(JSONObject interfaceInfo);

    JsonResult addInterface(JSONObject interfaceInfo);

    JsonResult updateInterface(JSONObject interfaceInfo);

}
