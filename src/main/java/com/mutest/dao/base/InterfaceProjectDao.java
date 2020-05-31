package com.mutest.dao.base;

import com.alibaba.fastjson.JSONObject;
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

    int updateProject(JSONObject request);

    @Insert("INSERT INTO mutest.interface_project(project_name,url,creator,description)VALUES(#{projectName},#{url},#{creator},#{description})")
    int addProject(JSONObject request);

    @Select("SELECT id FROM mutest.interface_project WHERE project_name = #{projectName} LIMIT 1")
    Long getProjectIdByName(@RequestParam String projectName);

    @Select("SELECT COUNT(id) FROM mutest.interface_project WHERE id!= #{projectId} AND project_name=#{projectName}")
    int projectUpdateCount(@Param("projectId") Long projectId, @Param("projectName") String projectName);

    @Select("SELECT COUNT(id) FROM mutest.interface_project WHERE project_name=#{projectName}")
    int projectAddCount(@Param("projectName") String projectName);
}
