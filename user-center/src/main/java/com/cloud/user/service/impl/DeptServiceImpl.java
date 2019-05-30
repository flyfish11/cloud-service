package com.cloud.user.service.impl;

import com.cloud.common.utils.SystemConst;
import com.cloud.model.common.ZTreeNode;
import com.cloud.model.common.ZtreeNodeRadio;
import com.cloud.model.user.AppUser;
import com.cloud.model.user.Dept;
import com.cloud.user.dao.AppUserDao;
import com.cloud.user.dao.DeptDao;
import com.cloud.user.service.DeptService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 部门服务
 *
 * @author yulj
 * @create: 2019/05/06 14:42
 */

@Service
@Slf4j
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;

    @Autowired
    private AppUserDao userDao;


    @Override
    public List<ZTreeNode> tree() {
        return this.deptDao.tree();
    }

    @Override
    public List<ZtreeNodeRadio> deptAndUserTree() {
        List<Dept> deptList = this.deptDao.findAllDept();
        List<AppUser> userList = this.userDao.findAllUser();
        List<ZtreeNodeRadio> tree = Lists.newArrayList();
        for (Dept dept : deptList) {
            ZtreeNodeRadio ztreeNodeCheck = ZtreeNodeRadio.builder().
                    id(dept.getId().longValue()).name(dept.getSdeptname()).
                    pId(dept.getPid().longValue()).nocheck(true).open(true).build();
            tree.add(ztreeNodeCheck);
        }
        for (AppUser user : userList) {
            if (user.getDepId() != null && !"".equals(user.getDepId())) {
                ZtreeNodeRadio ztreeNodeCheck = ZtreeNodeRadio.builder().
                        id(Long.parseLong(user.getId()) + SystemConst.USER_ID_ACCUMULATE).name(user.getUsername()).
                        pId(Long.parseLong(user.getDepId())).build();
                tree.add(ztreeNodeCheck);
            }
        }
        return tree;
    }

    @Override
    public Dept load(Integer id) {
        return this.deptDao.findyById(id);
    }

    @Override
    public String getDeptNames(String ids) {
        List<String> idsList = Arrays.asList(ids.split(","));
        String deptNames = "";
        for (String id : idsList) {
            Dept dept = load(Integer.valueOf(id));
            deptNames += dept.getSdeptname() + ",";
        }
        if (!deptNames.equals("")) {
            deptNames = deptNames.substring(0, deptNames.length() - 1);
        }
        return deptNames;
    }
}
