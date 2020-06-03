package com.mutest.dao.base;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.interfaces.ModuleInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/9/4 16:20
 * @description
 * @modify
 */
public interface InterfaceModuleDao {
    /**
     * 获取模块列表
     *
     * @return
     */
    List<ModuleInfo> getModuleList();

    /**
     * 搜索模块
     *
     * @param projectName 项目
     * @return 模块列表
     */
    List<ModuleInfo> searchModule(@Param("projectName") String projectName);

    @Select("SELECT COUNT(module_name) FROM mutest.interface_module a JOIN mutest.interface_project b ON a.project_id =b.id WHERE project_name=#{projectName} AND module_name=#{moduleName}")
    int moduleAddCount(@Param("projectName") String projectName, @Param("moduleName") String moduleName);

    @Select("SELECT COUNT(module_name) FROM mutest.interface_module a JOIN mutest.interface_project b ON a.project_id =b.id WHERE a.id!=#{id} AND project_name=#{projectName} AND module_name=#{moduleName}")
    int moduleUpdateCount(@Param("id") Long id, @Param("projectName") String projectName, @Param("moduleName") String moduleName);

    /**
     * 新增模块
     *
     * @param request 请求体
     */
    @Insert("INSERT INTO mutest.interface_module(project_id,module_name,creator,description) VALUES(#{projectId},#{moduleName},#{creator},#{description})")
    void addModule(JSONObject request);

    /**
     * 修改模块信息
     *
     * @param request 请求体
     * @return
     */
    int updateModule(JSONObject request);


    /**
     * 根据项目名称和模块名称获取模块id
     *
     * @param projectName 项目名称
     * @param moduleName  模块名称
     * @return
     */
    @Select("SELECT a.id FROM mutest.interface_module a JOIN mutest.interface_project b ON a.project_id = b.id AND project_name=#{projectName} AND module_name=#{moduleName}")
    Long getModuleIdByName(@Param("projectName") String projectName, @Param("moduleName") String moduleName);
}
