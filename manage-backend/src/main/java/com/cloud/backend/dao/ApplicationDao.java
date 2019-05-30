package com.cloud.backend.dao;
import org.apache.ibatis.annotations.Param;

import com.cloud.backend.model.Application;
import com.cloud.backend.model.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ApplicationDao {

	int deleteByPrimaryKey(String applicationid);


	Application selectByPrimaryKey(String applicationid);


	int insert(@Param("application") Application application);

	int insertSelective(@Param("application") Application application);


	List<Application> selectApplication(Application application);



}
