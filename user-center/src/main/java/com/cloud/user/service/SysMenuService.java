package com.cloud.user.service;

import com.cloud.model.common.ZTreeNode;
import com.cloud.model.user.SysMenu;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysMenuService {

    int deleteById(String id);

    int add(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu findById(String menuId);

    int updateMenu(SysMenu record);

    List<SysMenu> findSysMenu(Map<String, Object> params);

    List<ZTreeNode> getZtreesMenu(String menuType);

    List<SysMenu> findByRoles (Set<String> roleIds);

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeListByroleId(String roleId);


    void setMenuToRole(String roleId, Set<String> menuIds);

}
