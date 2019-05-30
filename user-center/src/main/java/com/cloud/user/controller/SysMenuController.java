package com.cloud.user.controller;

import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Result;
import com.cloud.model.common.ZTreeNode;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.SysMenu;
import com.cloud.model.user.SysRole;
import com.cloud.user.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/sysMenu")
@Slf4j
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取所有菜单列表
     * @param params
     * @return
     */
    @GetMapping
    public Result findSysMenu(@RequestParam Map<String, Object> params){
        List<SysMenu> sysMenu1 = sysMenuService.findSysMenu(params);
        return ResultUtil.success(sysMenu1);
    }



    /**
     * 添加菜单
     * @param sysMenu
     * @return
     */
    @PostMapping
    public Result addSysMenu(@RequestBody SysMenu sysMenu){
        sysMenuService.add(sysMenu);
        return ResultUtil.success(null);
    }

    /**
     * 获取下拉树菜单
     * @return
     */
    @GetMapping("/selectZtree/{menuType}")
    public List<ZTreeNode> ztreeMenu(@PathVariable String menuType){
        return sysMenuService.getZtreesMenu(menuType) ;
    }

    /**
     * 根据菜单id查询菜单信息
     * @param id
     * @return 菜单实体
     */
    @GetMapping("/{id}")
    public Result getMenuById(@PathVariable String id){
        SysMenu sysMenu = sysMenuService.findById(id);
        return ResultUtil.success(sysMenu);
    }

    /**
     * 修改菜单信息
     * @param sysMenu
     * @return
     */
    @PutMapping
    public Result updateMenu(@RequestBody SysMenu sysMenu){
         sysMenuService.updateMenu(sysMenu);
        return ResultUtil.success(null);
    }

    /**
     * 根据用菜单id删除菜单
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteMenu(@PathVariable String id){
        sysMenuService.deleteById(id);
        return ResultUtil.success(null);
    }

    /**
     * 获取当前用户树形菜单
     * @return
     */
    @GetMapping("/me")
    public List<SysMenu> findMyMenu() {
        log.info("进入查询菜单****************************************************************************************************************");
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Set<SysRole> roles = loginAppUser.getSysRoles();
        if (CollectionUtils.isEmpty(roles)) {
            return Collections.emptyList();
        }
        List<SysMenu> menus = sysMenuService.findByRoles(roles.parallelStream()
                .map(SysRole::getId)
                .collect(Collectors.toSet()));
        List<SysMenu> firstLevelMenus = menus.stream().filter(m -> m.getPId().equals("0"))
                .collect(Collectors.toList());
        firstLevelMenus.forEach(m -> {
            setChild(m, menus);
        });
        return firstLevelMenus;
    }
    private void setChild(SysMenu menu, List<SysMenu> menus) {
        List<SysMenu> child = menus.stream().filter(m -> m.getPId().equals(menu.getId()))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(child)) {
            menu.setChild(child);
        }
    }

   @GetMapping("/menuTreeListByRoleId/{roleId}")
    public List<ZTreeNode> menuTreeListByroleId(@PathVariable  String roleId) {

        return sysMenuService.menuTreeListByroleId(roleId);
    }
    /**
     * 给角色分配菜单
     * @param menuIds
     */
    @LogAnnotation(module = LogModule.SET_MENU_ROLE)
    @PreAuthorize("hasAuthority('back:menu:set2role')")
    @PostMapping("/toRole/{roleId}")
    public void setMenuToRole(@PathVariable String roleId, @RequestBody Set<String> menuIds) {
        sysMenuService.setMenuToRole(roleId, menuIds);
    }


}
