package com.cloud.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cloud.model.user.SysRole;

@Mapper
public interface SysRoleDao {

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_role(id,code, name, createTime, updateTime) values(#{id},#{code}, #{name}, #{createTime}, #{createTime})")
	int save(SysRole sysRole);

	@Update("update sys_role t set t.name = #{name} , t.code= #{code},t.updateTime = #{updateTime} where t.id = #{id}")
	int update(SysRole sysRole);

	@Select("select * from sys_role t where t.id = #{id}")
	SysRole findById(String id);

	@Select("select * from sys_role t where t.code = #{code}")
	SysRole findByCode(String code);

	@Delete("delete from sys_role where id = #{id}")
	int delete(String id);

	int count(Map<String, Object> params);

	List<SysRole> findData(Map<String, Object> params);



}
