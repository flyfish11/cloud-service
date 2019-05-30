package com.cloud.backend.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cloud.backend.model.Menu;

@Mapper
public interface RoleMenuDao {

	@Insert("insert into role_menu(roleId, menuId) values(#{roleId}, #{menuId})")
	int save(@Param("roleId") String roleId, @Param("menuId") String menuId);

	int delete(@Param("roleId") String roleId, @Param("menuId") String menuId);

	@Select("select t.menuId from role_menu t where t.roleId = #{roleId}")
	Set<String> findMenuIdsByRoleId(String roleId);

	List<Menu> findMenusByRoleIds(@Param("roleIds") Set<String> roleIds);

}
