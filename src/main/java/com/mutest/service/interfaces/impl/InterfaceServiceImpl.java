package com.mutest.service.interfaces.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mutest.advice.BusinessErrorException;
import com.mutest.advice.BusinessMsgEnum;
import com.mutest.dao.base.InterfaceListDao;
import com.mutest.dao.base.InterfaceModuleDao;
import com.mutest.model.JsonResult;
import com.mutest.model.interfaces.InterfaceInfo;
import com.mutest.service.interfaces.InterfaceService;
import com.mutest.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/7/3 11:23
 * @description
 * @modify 接口列表实现类
 */
@Service(value = "InterfaceService")
@Slf4j
public class InterfaceServiceImpl implements InterfaceService {
    @Resource
    private InterfaceListDao interfaceListDao;
    @Resource
    private InterfaceModuleDao interfaceModuleDao;

    @Override
    public JsonResult getInterfaceList(int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            PageInfo<InterfaceInfo> pageInfo = new PageInfo<>(interfaceListDao.getInterfaceList());
            return new JsonResult<>(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("获取接口列表失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    /**
     * 搜索接口
     *
     * @param request 搜索条件
     * @return 符合搜索条件的接口集合
     */
    @Override
    public JsonResult searchInterface(JSONObject request) {
        try {
            int pageNum = request.getInteger("pageNum");
            int pageSize = request.getInteger("pageSize");

            PageHelper.startPage(pageNum, pageSize);
            PageInfo<InterfaceInfo> pageInfo = new PageInfo(interfaceListDao.searchInterface(request));
            return new JsonResult<>(pageInfo.getList(), "操作成功！", pageInfo.getTotal());
        } catch (Exception e) {
            log.error("搜索接口失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    /**
     * 获取所有项目
     *
     * @return 项目名称的集合
     */
    @Override
    public List<String> getProject() {
        List<String> project;
        try {
            project = interfaceListDao.getProject();
        } catch (Exception e) {
            project = Arrays.asList("主站", "USDT合约");
        }
        return project;
    }

    /**
     * 根据项目获取模块
     *
     * @param projectName 项目
     * @return 模块名称的集合
     */
    @Override
    public List<String> getModule(String projectName) {
        List<String> module;
        try {
            module = interfaceListDao.getModule(projectName);
        } catch (Exception e) {
            module = Arrays.asList("58ex", "现货");
        }
        return module;
    }

    /**
     * 根据项目、模块获取接口
     *
     * @param projectName 项目
     * @param moduleName  模块
     * @return 接口集合
     */
    @Override
    public List<String> getInterface(String projectName, String moduleName) {
        List<String> interfaces;
        try {
            interfaces = interfaceListDao.getInterfaceName(projectName, moduleName);
        } catch (Exception e) {
            interfaces = Arrays.asList("58ex", "现货");
        }
        return interfaces;
    }

    /**
     * 新增接口
     *
     * @param interfaceInfo 接口信息
     * @return 响应结果
     */
    @Override
    public JsonResult addInterface(JSONObject interfaceInfo) {
        try {
            String projectName = interfaceInfo.getString("projectName");
            String moduleName = interfaceInfo.getString("moduleName");
            // 增加判重逻辑
            int count = interfaceListDao.interfaceAddCount(interfaceInfo);
            if (count > 0)
                return new JsonResult<>("400", "该接口已存在，请勿重复添加！");
            Long moduleId = interfaceModuleDao.getModuleIdByName(projectName, moduleName);
            interfaceInfo.put("moduleId", moduleId);
            interfaceInfo.put("creator", UserUtil.getLoginUser().getNickname());

            interfaceListDao.addInterface(interfaceInfo);
            return new JsonResult<>("0", "操作成功！");
        } catch (Exception e) {
            log.error("新增接口失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    /**
     * 修改接口信息
     *
     * @param interfaceInfo 前端请求
     * @return
     */
    @Override
    public JsonResult updateInterface(JSONObject interfaceInfo) {
        try {
            String projectName = interfaceInfo.getString("projectName");
            String moduleName = interfaceInfo.getString("moduleName");

            // 增加判重逻辑
            int count = interfaceListDao.interfaceUpdateCount(interfaceInfo);
            if (count > 0)
                return new JsonResult<>("400", "该接口已存在，请勿重复添加！");
            Long moduleId = interfaceModuleDao.getModuleIdByName(projectName, moduleName);
            interfaceInfo.put("moduleId", moduleId);

            interfaceListDao.updateInterface(interfaceInfo);
            return new JsonResult<>("0", "操作成功！");
        } catch (Exception e) {
            log.error("修改接口失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }

    public JsonResult deleteInterface(int interfaceId) {
        try {
            // 获取该接口下所有的用例

            return new JsonResult<>("0", "操作成功");
        } catch (Exception e) {
            log.error("删除接口失败：" + e.getMessage());
            return new JsonResult<>(new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION));
        }
    }
}
