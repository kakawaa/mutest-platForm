package com.mutest.controller.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.JsonResult;
import com.mutest.service.interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/5/16 14:27
 * @description 项目
 * @modify
 */
@RestController
@RequestMapping(value = "/interface")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/projectList", method = RequestMethod.GET)
    public JsonResult getCaseList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return projectService.getProjectList(pageNum, pageSize);
    }

    @RequestMapping(value = "/updateProject", method = RequestMethod.PUT)
    public JsonResult updateInterface(@RequestBody JSONObject projectInfo) {
        return projectService.updateProject(projectInfo);
    }

    @RequestMapping(value = "/addProject", method = RequestMethod.POST)
    public JsonResult addInterface(@RequestBody JSONObject projectInfo) {
        return projectService.addProject(projectInfo);
    }
}
