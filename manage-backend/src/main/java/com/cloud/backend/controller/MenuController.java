package com.cloud.backend.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.cloud.backend.model.Menu;
import com.cloud.backend.service.MenuService;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.SysRole;

@RestController
@RequestMapping("/menus")
public class MenuController {

	@Autowired
	private MenuService menuService;
	/**
	 * 当前登录用户的菜单
	 * @return
	 */
	@ApiOperation(value = "登录用户的菜单", notes = "当前登录用户的菜单")
	@GetMapping("/me")
	public List<Menu> findMyMenu() {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		Set<SysRole> roles = loginAppUser.getSysRoles();
		if (CollectionUtils.isEmpty(roles)) {
			return Collections.emptyList();
		}
		List<Menu> menus = menuService.findByRoles(roles.parallelStream()
				.map(SysRole::getId)
				.collect(Collectors.toSet()));

		List<Menu> firstLevelMenus = menus.stream().filter(m -> m.getParentId().equals("0"))
				.collect(Collectors.toList());
		firstLevelMenus.forEach(m -> {
			setChild(m, menus);
		});
		return firstLevelMenus;
	}

	private void setChild(Menu menu, List<Menu> menus) {
		List<Menu> child = menus.stream().filter(m -> m.getParentId().equals(menu.getId()))
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(child)) {
			menu.setChild(child);
		}
	}

	/**
	 * 给角色分配菜单
	 * @param menuIds
	 */
	@LogAnnotation(module = LogModule.SET_MENU_ROLE)
	@PreAuthorize("hasAuthority('back:menu:set2role')")
	@ApiOperation(value = "给角色分配菜单", notes = "给角色分配菜单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String ", paramType = "query"),
			@ApiImplicitParam(name = "menuIds", value = "菜单id", required = true, dataType = "Set<String> ", paramType = "query")
	})
	@PostMapping("/toRole")
	public void setMenuToRole(String roleId, @RequestBody Set<String> menuIds) {
		menuService.setMenuToRole(roleId, menuIds);
	}

	/**
	 * 菜单树ztree
	 * 
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('back:menu:set2role','back:menu:query')")
	@ApiOperation(value = "菜单树ztree", notes = "菜单树ztree")
	@GetMapping("/tree")
	public List<Menu> findMenuTree() {
		List<Menu> all = menuService.findAll();
		List<Menu> list = new ArrayList<>();
		setMenuTree("0", all, list);
		return list;
	}

	/**
	 * 菜单树
	 * 
	 * @param parentId
	 * @param all
	 * @param list
	 */
	private void setMenuTree(String parentId, List<Menu> all, List<Menu> list) {
		all.forEach(menu -> {
			if (parentId.equals(menu.getParentId())) {
				list.add(menu);

				List<Menu> child = new ArrayList<>();
				menu.setChild(child);
				setMenuTree(menu.getId(), all, child);
			}
		});
	}

	/**
	 * 获取角色的菜单
	 * 
	 * @param roleId
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('back:menu:set2role','menu:byroleid')")
	@ApiOperation(value = "获取角色的菜单", notes = "获取角色的菜单")
	@ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String ", paramType = "query")
	@GetMapping(params = "roleId")
	public Set<String> findMenuIdsByRoleId(String roleId) {
		return menuService.findMenuIdsByRoleId(roleId);
	}

	/**
	 * 添加菜单
	 * 
	 * @param menu
	 * @return
	 */
	@LogAnnotation(module = LogModule.ADD_MENU)
	@PreAuthorize("hasAuthority('back:menu:save')")
	@ApiOperation(value = "获取角色的菜单", notes = "获取角色的菜单")
	@ApiImplicitParam(name = "menu", value = "菜单实体", required = true, dataType = "Menu ", paramType = "query")
	@PostMapping
	public Menu save(@RequestBody Menu menu) {
		menuService.save(menu);

		return menu;
	}

	/**
	 * 修改菜单
	 * 
	 * @param menu
	 * @return
	 */
	@LogAnnotation(module = LogModule.UPDATE_MENU)
	@PreAuthorize("hasAuthority('back:menu:update')")
	@ApiOperation(value = " 修改菜单", notes = " 修改菜单")
	@ApiImplicitParam(name = "menu", value = "菜单实体", required = true, dataType = "Menu ", paramType = "query")
	@PutMapping
	public Menu update(@RequestBody Menu menu) {
		menuService.update(menu);

		return menu;
	}

	/**
	 * 删除菜单
	 * 
	 * @param id
	 */
	@LogAnnotation(module = LogModule.DELETE_MENU)
	@PreAuthorize("hasAuthority('back:menu:delete')")
	@ApiOperation(value = " 删除菜单", notes = " 根据菜单id删除菜单")
	@ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "String ", paramType = "path")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		menuService.delete(id);
	}

	/**
	 * 查询所有菜单
	 * 
	 * @return
	 */
	@PreAuthorize("hasAuthority('back:menu:query')")
	@ApiOperation(value = " 查询所有菜单", notes = " 查询所有菜单")
	@GetMapping("/all")
	public List<Menu> findAll() {
		List<Menu> all = menuService.findAll();
		List<Menu> list = new ArrayList<>();
		setSortTable("0", all, list);
		return list;
	}

	/**
	 * 查询所有菜单
	 *
	 * @return
	 */
	@GetMapping("/all1")
	@ApiOperation(value = " 查询所有菜单", notes = " 根据查询条件所有菜单")
	@ApiImplicitParam(name = "menu", value = "查询条件", required = true, dataType = "Menu ", paramType = "query")
	@ResponseBody
	public List<Menu> findAll1( Menu menu) {
		List<Menu> all = menuService.selectAll(menu);
		List<Menu> list = new ArrayList<>();
		setSortTable("0", all, list);
		return list;
	}

	/**
	 * 菜单tabl
	 * @param parentId
	 * @param all
	 * @param list
	 */
	private void setSortTable(String parentId, List<Menu> all, List<Menu> list) {
		all.forEach(a -> {
			if (a.getParentId().equals(parentId)) {
				list.add(a);
				setSortTable(a.getId(), all, list);
			}
		});
	}

	@PreAuthorize("hasAuthority('back:menu:query')")
	@ApiOperation(value = " 查询所有菜单", notes = " 根据id查询菜单")
	@ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "String ", paramType = "path")
	@GetMapping("/{id}")
	public Menu findById(@PathVariable String id) {
		return menuService.findById(id);
	}

}
