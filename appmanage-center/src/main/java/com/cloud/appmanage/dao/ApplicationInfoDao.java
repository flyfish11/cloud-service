package com.cloud.appmanage.dao;

import org.apache.ibatis.annotations.Param;

import com.cloud.model.appmanage.ApplicationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 应用数据接口
 *
 * @author yulj
 * @create: 2019/05/08 20:43
 */
@Mapper
public interface ApplicationInfoDao {

    int deleteByPrimaryKey(String id);

    int insert(ApplicationInfo record);

    int insertSelective(ApplicationInfo record);

    ApplicationInfo selectByPrimaryKey(String id);

    int updateById(ApplicationInfo applicationInfo);

    List<ApplicationInfo> findList(Map<String, Object> params);

    int count(Map<String, Object> params);

    List<ApplicationInfo> selectByNameAndBelongDepartment(@Param("name") String name, @Param("belongDepartment") String belongDepartment, @Param("isDelete") Integer isDelete);


}