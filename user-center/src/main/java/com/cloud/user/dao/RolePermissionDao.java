package com.cloud.user.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cloud.model.user.SysPermission;

/**
 * 角色权限关系
 * 
 * @author hlxd
 *
 */
@Mapper
public interface RolePermissionDao {

	@Insert("insert into sys_role_permission(roleId, permissionId) values(#{roleId}, #{permissionId})")
	int saveRolePermission(@Param("roleId") String roleId, @Param("permissionId") String permissionId);

	int deleteRolePermission(@Param("roleId") String roleId, @Param("permissionId") String permissionId);

	Set<SysPermission> findPermissionsByRoleIds(@Param("roleIds") Set<String> roleIds);

}
