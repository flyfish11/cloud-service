package com.cloud.user.controller;

import com.cloud.model.common.ZTreeNode;
import com.cloud.model.common.ZtreeNodeRadio;

import com.cloud.model.user.Dept;
import com.cloud.user.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门控制器
 *
 * @author yulj
 * @create: 2019/05/06 14:39
 */

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 获取部门的tree列表
     */
    @GetMapping(value = "/tree")
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.deptService.tree();
        return tree;
    }

    /**
     * 获取部门的tree列表
     */
    @GetMapping(value = "/treeRadio")
    public List<ZtreeNodeRadio> treeRadio() {
        List<ZtreeNodeRadio> tree = this.deptService.deptAndUserTree();
        return tree;
    }

    /**
     * 根据部门id查询部门数据
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/load/{id}")
    public Dept load(@PathVariable Integer id) {
        return this.deptService.load(id);
    }

    @GetMapping(value = "/queryDeptNames")
    public String queryDeptNames(@RequestParam String ids) {
        return this.deptService.getDeptNames(ids);
    }


}
