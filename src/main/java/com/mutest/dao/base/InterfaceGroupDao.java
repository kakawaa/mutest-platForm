package com.mutest.dao.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mutest.model.SysUser;
import com.mutest.model.interfaces.GroupInfo;
import com.mutest.model.interfaces.ProjectInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author muguozheng
 * @date 2020/5/14 19:11
 * @Description: 组成员与组项目管理sql
 * @modify
 */
public interface InterfaceGroupDao {
    List<GroupInfo> getGroupList();

    int updateGroup(JSONObject request);

    @Insert("INSERT INTO mutest.interface_group(group_name,group_leader,description) VALUES (#{groupName},#{groupLeader},#{description})")
    int addGroup(JSONObject request);

    @Select("SELECT COUNT(*) FROM mutest.interface_group WHERE group_name=#{groupName}")
    int groupAddCount(@Param("groupName") String groupName);

    @Select("SELECT COUNT(*) FROM mutest.interface_group WHERE id!=#{id} AND group_name=#{groupName}")
    int groupUpdateCount(@Param("id") Long id, @Param("groupName") String groupName);

    @Select("SELECT * FROM mutest.sys_user WHERE status = 1")
    List<SysUser> getUserList();

    List<GroupInfo> searchGroup(JSONObject request);

    @Select("SELECT id,project_name projectName FROM mutest.interface_project")
    List<ProjectInfo> getProjectList();

    @Select("SELECT id FROM mutest.sys_user a JOIN mutest.interface_group_user b ON a.id=b.user_id WHERE group_id = #{groupId}")
    List<String> getUserIdsByGroup(@Param("groupId") Long groupId);

    int addGroupUserBatch(Long groupId, JSONArray userList);

    int deleteGroupUserBatch(Long groupId, JSONArray userList);

    @Select("SELECT project_id projectId FROM mutest.interface_group_project WHERE group_id = #{groupId}")
    List<String> getProjectIdsByGroup(@Param("groupId") Long groupId);

    int addGroupProjectBatch(Long groupId, JSONArray projectList);

    int deleteGroupProjectBatch(Long groupId, JSONArray projectList);
}
