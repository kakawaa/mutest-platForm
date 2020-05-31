package com.mutest.dao.base;

import com.alibaba.fastjson.JSONObject;
import com.mutest.model.SysUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface UserDao {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into mutest.sys_user(username, password, nickname, department, role, headImgUrl, phone, telephone, email, birthday, sex, status, description) values(#{username}, #{password}, #{nickname}, #{department}, #{role}, #{headImgUrl}, #{phone}, #{telephone}, #{email}, #{birthday}, #{sex}, #{status}, #{description})")
    int addUser(JSONObject userInfo);

    List<SysUser> getUserList();

    @Select("select * from mutest.sys_user t where t.id = #{id}")
    SysUser getById(Long id);

    @Select("SELECT id FROM mutest.sys_role WHERE nickname = #{nickname}")
    Long getRoleIdByName(@Param("nickname") String nickname);

    @Select("select * from mutest.sys_user t where t.username = #{username}")
    SysUser getUser(String username);

    @Update("update mutest.sys_user t set t.password = #{password} where t.id = #{id}")
    int changePassword(@Param("id") Long id, @Param("password") String password);

    @Delete("DELETE FROM mutest.sys_user WHERE id=#{userId}")
    int deleteUser(@Param("userId") Long userId);

    List<SysUser> searchUser(JSONObject request);

    Integer count(@Param("params") Map<String, Object> params);

    List<SysUser> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset,
                       @Param("limit") Integer limit);

    @Delete("delete from mutest.sys_role_user where userId = #{userId}")
    int deleteUserRole(Long userId);

    int saveUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    int saveUserRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

    int updateUser(JSONObject userInfo);

    @Update("UPDATE mutest.sys_user SET headImgUrl = #{headImgUrl} WHERE id = #{id}")
    int updateHeadImgUrl(@Param("headImgUrl") String headImgUrl, @Param("id") Long id);
}
