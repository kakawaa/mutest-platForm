package com.mutest.controller.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.JsonResult;
import com.mutest.service.interfaces.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/9/4 14:28
 * @description 接口自动化项目管理
 * @modify
 */
@RestController
@RequestMapping(value = "/interface")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    @RequestMapping(value = "/moduleList", method = RequestMethod.GET)
    public JsonResult getModuleList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return moduleService.getModuleList(pageNum, pageSize);
    }

    @RequestMapping(value = "/searchModule", method = RequestMethod.POST)
    public JsonResult searchModule(@RequestBody JSONObject request) {
        return moduleService.searchModule(request);
    }

    @RequestMapping(value = "/addModule", method = RequestMethod.POST)
    public JsonResult addInterface(@RequestBody JSONObject request) {
        return moduleService.addModule(request);
    }

    @RequestMapping(value = "/updateModule", method = RequestMethod.PUT)
    public JsonResult updateInterface(@RequestBody JSONObject request) {
        return moduleService.updateModule(request);
    }
}
