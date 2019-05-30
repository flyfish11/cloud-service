package com.cloud.user.dao;

import com.cloud.model.common.ZTreeNode;
import com.cloud.model.user.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 部门表 dao 接口
 * </p>
 *
 * @author yulj
 * @create: 2019/05/06 14:55
 */

@Mapper
public interface DeptDao {

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree();

    /**
     * 查询所有部门信息
     *
     * @return
     */
    List<Dept> findAllDept();

    @Select("select * from sys_dept where decode=#{code}")
    Dept findDeptByCode(String code);

    @Select("select * from sys_dept where id=#{id}")
    Dept findyById(Integer id);
}
