package com.mutest.service.interfaces.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mutest.advice.BusinessErrorException;
import com.mutest.advice.BusinessMsgEnum;
import com.mutest.dao.base.InterfaceProjectDao;
import com.mutest.model.JsonResult;
import com.mutest.model.interfaces.CaseInfo;
import com.mutest.model.interfaces.InterfaceInfo;
import com.mutest.model.interfaces.ProjectInfo;
import com.mutest.service.interfaces.ProjectService;
import com.mutest.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/5/9 14:46
 * @description
 * @modify
 */
@Service(value = "ProjectService")
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private InterfaceProjectDao projectDao;

    /**
     * 获取用例列表
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return
     */
    @Override
    public JsonResult getProjectList(int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<CaseInfo> pageInfo = new PageInfo(projectDao.getProjectList());
            return new JsonResult<>(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("获取项目列表失败：" + e.getMessage());
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
    }

    @Override
    public JsonResult updateProject(JSONObject request) {
        try {
            // 判重
            Long projectId = request.getLong("id");
            String projectName = request.getString("projectName");
            int count = projectDao.projectUpdateCount(projectId, projectName);
            if (count > 0)
                return new JsonResult<>("400", "项目名称已存在！");
            projectDao.updateProject(request);
            return new JsonResult<>("0", "操作成功！");
        } catch (Exception e) {
            log.error("修改项目失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    @Override
    public JsonResult addProject(JSONObject projectInfo) {
        try {
            String projectName = projectInfo.getString("projectName");
            int count = projectDao.projectAddCount(projectName);
            if (count > 0)
                return new JsonResult<>("400", "该项目已存在，请勿重复添加！");
            String creator = UserUtil.getLoginUser().getNickname();
            projectInfo.put("creator", creator);
            projectDao.addProject(projectInfo);
            return new JsonResult<>("0", "操作成功！");
        } catch (Exception e) {
            log.error("修改项目失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    @Override
    public JsonResult projectStructure(String projectName) {
        try {
            List<InterfaceInfo> interfaceInfoList = projectDao.getInterfaceListByProject(projectName);
            Map<String, List<InterfaceInfo>> moduleInterface = interfaceInfoList.stream().collect(Collectors.groupingBy(InterfaceInfo::getModuleName));

            Iterator<String> ite = moduleInterface.keySet().iterator();
            JSONArray moduleArray = new JSONArray();
            JSONArray projectArray = new JSONArray();
            JSONObject projectObject = new JSONObject();
            projectObject.put("title", projectName);
            projectObject.put("spread", true);

            while (ite.hasNext()) {
                String moduleName = ite.next();
                JSONObject moduleObject = new JSONObject();
                moduleObject.put("title", moduleName);
                moduleObject.put("spread", true);

                List<InterfaceInfo> interfaceInfos = moduleInterface.get(moduleName);
                JSONArray moduleChildren = new JSONArray();
                for (InterfaceInfo interfaceInfo : interfaceInfos) {
                    JSONObject interfaceObject = new JSONObject();
                    interfaceObject.put("title", interfaceInfo.getInterfaceName());
                    moduleChildren.add(interfaceObject);
                }
                moduleObject.put("children", moduleChildren);
                moduleArray.add(moduleObject);
            }
            projectObject.put("children", moduleArray);
            projectArray.add(projectObject);

            return new JsonResult<>(projectArray);
        } catch (Exception e) {
            log.error("获取项目结构失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    @Override
    public JsonResult projectSync() {
        try {
            List<ProjectInfo> projectList1 = projectDao.projectInterfaceCount();
            List<ProjectInfo> projectList2 = projectDao.projectCaseCount();

            for (int i = 0; i < projectList1.size(); i++) {
                projectList1.get(i).setCaseCount(projectList2.get(i).getCaseCount());
            }
            projectDao.updateProjectCountBatch(projectList1);
            return new JsonResult<>("0", "操作成功！");
        } catch (Exception e) {
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }
}
