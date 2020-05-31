package com.mutest.dao.base;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.*;

import com.mutest.model.Permission;

@Mapper
public interface PermissionDao {

    @Select("select * from mutest.sys_permission t order by t.sort")
    List<Permission> listAll();

    @Select("select * from mutest.sys_permission t where t.type = 1 order by t.sort")
    List<Permission> listParents();

    @Select("select distinct p.* from mutest.sys_permission p inner join mutest.sys_role_permission rp on p.id = rp.permissionId inner join mutest.sys_role_user ru on ru.roleId = rp.roleId where ru.userId = #{userId} order by p.sort")
    List<Permission> listByUserId(Long userId);

    @Select("select p.* from mutest.sys_permission p inner join mutest.sys_role_permission rp on p.id = rp.permissionId where rp.roleId = #{roleId} order by p.sort")
    List<Permission> listByRoleId(Long roleId);

    @Select("select * from mutest.sys_permission t where t.id = #{id}")
    Permission getById(Long id);

    @Insert("insert into mutest.sys_permission(parentId, name, css, href, type, permission, sort) values(#{parentId}, #{name}, #{css}, #{href}, #{type}, #{permission}, #{sort})")
    int save(Permission permission);

    @Update("update mutest.sys_permission t set parentId = #{parentId}, name = #{name}, css = #{css}, href = #{href}, type = #{type}, permission = #{permission}, sort = #{sort} where t.id = #{id}")
    int update(Permission permission);

    @Delete("delete from mutest.sys_permission where id = #{id}")
    int delete(Long id);

    @Delete("delete from mutest.sys_permission where parentId = #{id}")
    int deleteByParentId(Long id);

    @Delete("delete from mutest.sys_role_permission where permissionId = #{permissionId}")
    int deleteRolePermission(Long permissionId);

    @Select("select ru.userId from mutest.sys_role_permission rp inner join mutest.sys_role_user ru on ru.roleId = rp.roleId where rp.permissionId = #{permissionId}")
    Set<Long> listUserIds(Long permissionId);
}
