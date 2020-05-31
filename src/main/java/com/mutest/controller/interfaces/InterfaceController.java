package com.mutest.controller.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.mutest.annotation.interfaces.GroupPermissions;
import com.mutest.annotation.interfaces.ProjectPermissions;
import com.mutest.model.JsonResult;
import com.mutest.service.interfaces.InterfaceService;
import com.mutest.service.interfaces.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/7/3 17:05
 * @description 接口
 * @modify
 */
@RestController
@RequestMapping(value = "/interface")
public class InterfaceController {
    @Autowired
    private InterfaceService interfaceService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/interfaceList", method = RequestMethod.GET)
    public JsonResult getInterfaceList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return interfaceService.getInterfaceList(pageNum, pageSize);
    }

    @RequestMapping(value = "/searchInterface", method = RequestMethod.POST)
    public JsonResult searchInterface(@RequestBody JSONObject request) {
        return interfaceService.searchInterface(request);
    }

    @RequestMapping(value = "/getProject", method = RequestMethod.GET)
    public List<String> getProject() {
        return interfaceService.getProject();
    }

    @RequestMapping(value = "/getModule", method = RequestMethod.GET)
    public List<String> getModule(@RequestParam("projectName") String projectName) {
        return interfaceService.getModule(projectName);
    }

    @RequestMapping(value = "/getInterface", method = RequestMethod.GET)
    public List<String> getInterface(@RequestParam("projectName") String projectName, @RequestParam("moduleName") String moduleName) {
        return interfaceService.getInterface(projectName, moduleName);
    }


    @ProjectPermissions
    @RequestMapping(value = "/addInterface", method = RequestMethod.POST)
    public JsonResult addInterface(@RequestBody JSONObject request) {
        permissionService.interfaceCaseAddAuth(request.getString("projectName"));
        return interfaceService.addInterface(request);
    }

    @ProjectPermissions()
    @GroupPermissions()
    @RequestMapping(value = "/updateInterface", method = RequestMethod.PUT)
    public JsonResult updateInterface(@RequestBody JSONObject interfaceInfo) {
        return interfaceService.updateInterface(interfaceInfo);
    }
}
