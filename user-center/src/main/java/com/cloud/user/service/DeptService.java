package com.cloud.user.service;

import com.cloud.model.common.ZTreeNode;
import com.cloud.model.common.ZtreeNodeRadio;
import com.cloud.model.user.Dept;

import java.util.List;

/**
 * 部门服务
 *
 * @author yulj
 * @create: 2019/05/06 14:41
 */
public interface DeptService {

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree();

    /**
     * 获取ztree的节点列表(包含用户)
     *
     * @return
     */
    List<ZtreeNodeRadio> deptAndUserTree();

    /**
     * 根据部门id查询部门名称
     *
     * @param id
     * @return
     */
    Dept load(Integer id);

    /**
     * 根据部门ids查询部门名称
     *
     * @param ids
     * @return
     */
    String getDeptNames(String ids);
}
