package com.cloud.user.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cloud.model.user.SysRole;

/**
 * 用户角色关系
 * 
 * @author hlxd
 *
 */
@Mapper
public interface UserRoleDao {

	int deleteUserRole(@Param("userId") String userId, @Param("roleId") String roleId);

	@Insert("insert into sys_role_user(userId, roleId) values(#{userId}, #{roleId})")
	int saveUserRoles(@Param("userId")String userId, @Param("roleId") String roleId);

	/**
	 * 根据用户id获取角色
	 * 
	 * @param userId
	 * @return
	 */
	@Select("select r.* from sys_role_user ru inner join sys_role r on r.id = ru.roleId where ru.userId = #{userId}")
	Set<SysRole> findRolesByUserId(String userId);

}
