package com.mutest.dao.base;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.interfaces.InterfaceInfo;
import com.mutest.model.interfaces.ProjectInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface InterfaceProjectDao {
    /**
     * 获取接口列表
     *
     * @return
     */
    List<ProjectInfo> getProjectList();

    List<ProjectInfo> projectInterfaceCount();

    List<ProjectInfo> projectCaseCount();

    void updateProjectCountBatch(List<ProjectInfo> projectList);

    int updateProject(JSONObject request);

    @Insert("INSERT INTO mutest.interface_project(project_name,url,creator,description)VALUES(#{projectName},#{url},#{creator},#{description})")
    int addProject(JSONObject request);

    @Select("SELECT id FROM mutest.interface_project WHERE project_name = #{projectName} LIMIT 1")
    Long getProjectIdByName(@RequestParam String projectName);

    @Select("SELECT COUNT(id) FROM mutest.interface_project WHERE id!= #{projectId} AND project_name=#{projectName}")
    int projectUpdateCount(@Param("projectId") Long projectId, @Param("projectName") String projectName);

    @Select("SELECT COUNT(id) FROM mutest.interface_project WHERE project_name=#{projectName}")
    int projectAddCount(@Param("projectName") String projectName);

    @Select("SELECT module_name moduleName,interface_name interfaceName FROM mutest.interface_list a JOIN mutest.interface_module b ON a.module_id=b.id JOIN mutest.interface_project c ON b.project_id=c.id WHERE project_name=#{projectName}")
    List<InterfaceInfo> getInterfaceListByProject(@Param("projectName") String projectName);
}
