package com.mutest.dao.base;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author muguozheng
 * @date 2020/5/14 19:11
 * @Description: 权限sql
 * @modify
 */
public interface InterfacePermissionDao {
    @Select("SELECT DISTINCT(project_id) FROM mutest.interface_group a JOIN mutest.interface_group_user b ON a.id=b.group_id JOIN mutest.interface_group_project c ON a.id=c.group_id WHERE b.user_id=#{userId}")
    List<Long> projectListByUserId(Long userId);

    @Select("SELECT roleId FROM mutest.sys_role_user WHERE userId=#{userId}")
    List<Long> roleListByUserId(Long userId);

    @Select("SELECT nickname FROM interface_group_user a INNER JOIN sys_user b ON a.user_id=b.id WHERE group_id = #{groupId}")
    JSONArray getNickNames(@Param("groupId") Long groupId);
}
