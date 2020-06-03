package com.mutest.dao.base;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.interfaces.InterfaceInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InterfaceListDao {
    /**
     * 获取接口列表
     *
     * @return
     */
    List<InterfaceInfo> getInterfaceList();

    Long getInterfaceIdByNames(@Param("projectName") String projectName, @Param("moduleName") String moduleName, @Param("interfaceName") String interfaceName);

    /**
     * 按条件搜索接口
     *
     * @param request:projectName moduleName interfaceName
     * @return
     */
    List<InterfaceInfo> searchInterface(JSONObject request);

    /**
     * 获取所有项目
     *
     * @return 项目列表
     */
    @Select("SELECT DISTINCT(project_name) FROM mutest.interface_project")
    List<String> getProject();

    /**
     * 获取某平台某项目下的所有模块
     *
     * @param projectName 项目
     * @return
     */
    @Select("SELECT DISTINCT(module_name) FROM mutest.interface_module a JOIN mutest.interface_project b ON a.project_id = b.id WHERE project_name = #{projectName}")
    List<String> getModule(@Param("projectName") String projectName);

    /**
     * 获取某平台某项目某模块下的所有接口
     *
     * @param projectName 项目
     * @param moduleName  模块
     * @return
     */
    @Select("SELECT DISTINCT(interface_name) FROM mutest.interface_list a JOIN mutest.interface_module b ON a.module_id=b.id JOIN mutest.interface_project c ON b.project_id = c.id AND project_name=#{projectName} AND module_name=#{moduleName}")
    List<String> getInterfaceName(@Param("projectName") String projectName, @Param("moduleName") String moduleName);

    /**
     * 新增接口
     *
     * @param interfaceInfo 前端请求
     */
    @Insert("INSERT INTO mutest.interface_list(module_id,interface_name,path,method,param_type,creator,description) VALUES(#{moduleId},#{interfaceName},#{path},#{method},#{paramType},#{creator},#{description})")
    void addInterface(JSONObject interfaceInfo);

    int interfaceAddCount(JSONObject request);

    int interfaceUpdateCount(JSONObject request);

    /**
     * 修改接口信息
     *
     * @param request 前端请求体
     * @return
     */
    int updateInterface(JSONObject request);
}
