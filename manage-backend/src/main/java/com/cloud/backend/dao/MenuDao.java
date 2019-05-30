package com.cloud.backend.dao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.cloud.backend.model.Menu;


@Mapper
public interface MenuDao<findApplicationId> {

	int insert(Menu menu);

	/*@Insert("insert into menu(id,parentId, name, url, css, sort, createTime, updateTime) "
			+ "values (#{id},#{parentId}, #{name}, #{url}, #{css}, #{sort}, #{createTime}, #{updateTime})")
	int save(Menu menu);*/

	int update(Menu menu);

	@Select("select * from menu t where t.id = #{id}")
	Menu findById(String id);

	@Delete("delete from menu where id = #{id}")
	int delete(String id);

	@Delete("delete from menu where parentId = #{id}")
	int deleteByParentId(String id);

	@Select("select * from menu t order by t.sort")
	List<Menu> findAll();


	List<Menu> findByApplicationId(@Param("applicationId")String applicationId);


	List<Menu> selectAll(Menu menu);








}
