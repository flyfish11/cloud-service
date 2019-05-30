package com.cloud.user.service.impl;

import com.cloud.model.common.ZTreeNode;
import com.cloud.model.user.SysMenu;
import com.cloud.user.dao.SysMenuDao;
import com.cloud.user.service.SysMenuService;
import com.sun.org.apache.xpath.internal.functions.FuncSubstring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;
    @Override
    public int deleteById(String id) {
        return sysMenuDao.deleteById(id);
    }

    @Override
    public int add(SysMenu sysMenu) {
        sysMenu.setId(UUID.randomUUID().toString().replace("-",""));
        sysMenu.setCreateTime(new Date());
        return sysMenuDao.insert(sysMenu);
    }

    @Override
    public int insertSelective(SysMenu record) {
        return 0;
    }

    @Override
    public SysMenu findById(String menuId) {
        return sysMenuDao.selectById(menuId);
    }

    @Override
    public int updateMenu(SysMenu sysMenu) {
        return sysMenuDao.updateMenu(sysMenu);
    }

    @Override
    public List<SysMenu> findSysMenu(Map<String, Object> params ) {


        return sysMenuDao.selectSysMenu(params);
    }


    @Override
    public List<ZTreeNode> getZtreesMenu(String menuType) {
        return sysMenuDao.selectZtreesMenu(menuType);
    }

    @Override
    public List<SysMenu> findByRoles(Set<String> roleIds) {
        return sysMenuDao.selectByRoles(roleIds);
    }

    @Override
    public List<ZTreeNode> menuTreeListByroleId(String roleId) {

        return sysMenuDao.menuTreeListByroleId(roleId);
    }


    @Transactional
    @Override
    public void setMenuToRole(String roleId, Set<String> menuIds) {
        sysMenuDao.delete(roleId, null);

        if (!CollectionUtils.isEmpty(menuIds)) {
            menuIds.forEach(menuId -> {
                sysMenuDao.setMenuToRole(roleId, menuId);
            });
        }
    }
}
