package com.cloud.backend.service;

import java.util.List;
import java.util.Set;

import com.cloud.backend.model.Menu;

public interface MenuService {

	void save(Menu menu);

	void update(Menu menu);

	void delete(String id);

	void setMenuToRole(String roleId, Set<String> menuIds);

	List<Menu> findByRoles(Set<String> roleIds);

	List<Menu> findAll();

	Menu findById(String id);

	Set<String> findMenuIdsByRoleId(String roleId);

	List<Menu> selectAll(Menu menu);


}
