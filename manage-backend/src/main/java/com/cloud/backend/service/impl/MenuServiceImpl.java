package com.cloud.backend.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.cloud.backend.dao.MenuDao;
import com.cloud.backend.dao.RoleMenuDao;
import com.cloud.backend.model.Menu;
import com.cloud.backend.service.MenuService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;
	@Autowired
	private RoleMenuDao roleMenuDao;

	@Override
	public void save(Menu menu) {
		menu.setId(UUID.randomUUID().toString().replace("-", ""));
		menu.setCreateTime(new Date());
		menu.setUpdateTime(menu.getCreateTime());

		menuDao.insert(menu);
		log.info("新增菜单：{}", menu);
	}

	@Override
	public void update(Menu menu) {
		menu.setUpdateTime(new Date());

		menuDao.update(menu);
		log.info("修改菜单：{}", menu);
	}

	@Transactional
	@Override
	public void delete(String id) {
		Menu menu = menuDao.findById(id);

		menuDao.deleteByParentId(menu.getId());
		menuDao.delete(id);

		log.info("删除菜单：{}", menu);
	}

	@Transactional
	@Override
	public void setMenuToRole(String roleId, Set<String> menuIds) {
		roleMenuDao.delete(roleId, null);

		if (!CollectionUtils.isEmpty(menuIds)) {
			menuIds.forEach(menuId -> {
				roleMenuDao.save(roleId, menuId);
			});
		}
	}

	@Override
	public List<Menu> findByRoles(Set<String> roleIds) {
		return roleMenuDao.findMenusByRoleIds(roleIds);
	}

	@Override
	public List<Menu> findAll() {
		return menuDao.findAll();
	}

	@Override
	public Menu findById(String id) {
		return menuDao.findById(id);
	}

	@Override
	public Set<String> findMenuIdsByRoleId(String roleId) {
		return roleMenuDao.findMenuIdsByRoleId(roleId);
	}

	@Override
	public List<Menu> selectAll(Menu menu) {
		return menuDao.selectAll(menu);
	}

}
