package com.cloud.appmanage.dao;
import org.apache.ibatis.annotations.Param;

import com.cloud.model.appmanage.ServiceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 服务数据接口
 *
 * @author yulj
 * @create: 2019/05/08 23:11
 */
@Mapper
public interface ServiceInfoDao {

    int deleteByPrimaryKey(String id);

    int insert(ServiceInfo record);

    int insertSelective(ServiceInfo record);

    ServiceInfo selectByPrimaryKey(String id);

    int update(ServiceInfo record);

    List<ServiceInfo> findList(Map<String, Object> params);

    int count(Map<String, Object> params);
    
    ServiceInfo findById(@Param("id")String id);

    int updateById(@Param("updated")ServiceInfo updated,@Param("id")String id);

    List<ServiceInfo> selectByName(@Param("name")String name,@Param("isDelete")Integer isDelete);



















}