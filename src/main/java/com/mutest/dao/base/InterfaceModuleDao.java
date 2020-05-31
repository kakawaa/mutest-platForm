package com.mutest.dao.base;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.interfaces.ModuleInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
     * @param platform 平台
     * @param project  项目
     * @param module   模块
     * @return 模块列表
     */
    List<ModuleInfo> searchModule(@Param("platform") String platform, @Param("project") String project, @Param("module") String module);

    /**
     * 新增模块
     *
     * @param request 请求体
     */
    @Insert("INSERT INTO mutest.interface_module(platform,project,module,host,description) VALUES(#{platform},#{project},#{module},#{host},#{description})")
    void addModule(JSONObject request);

    /**
     * 修改模块信息
     *
     * @param request 请求体
     * @return
     */
    int updateModule(JSONObject request);

    /**
     * 根据projectId和mouduleName获取moduleId
     *
     * @param projectId
     * @param moduleName
     * @return
     */
//    @Select("SELECT id FROM mutest.interface_module WHERE platform=#{platform} AND project=#{project} AND module=#{module}")
    @Select("SELECT id FROM mutest.interface_module WHERE project_id= #{projectId} AND module_name = #{moduleName}")
    int getModuleId(@Param("projectId") Long projectId, @Param("moduleName") String moduleName);

    /**
     * 根据项目名称和模块名称获取模块id
     *
     * @param projectName 项目名称
     * @param moduleName  模块名称
     * @return
     */
    @Select("SELECT a.id FROM mutest.interface_module a JOIN mutest.interface_project b ON a.project_id = b.id AND project_name=#{projectName} AND module_name=#{moduleName}")
    Long getModuleIdByName(@Param("projectName") String projectName, @Param("moduleName") String moduleName);

    @Update("UPDATE mutest.interface_module SET host=#{host} WHERE platform=#{platform} AND project=#{project}")
    int updateHost(JSONObject request);
}
