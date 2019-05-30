package com.cloud.backend.dao;

import com.cloud.backend.model.SysDict;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysDictDao {

    int count(Map<String, Object> params);

    //查询字典
    @Select("select * from sys_dict t where t.id=#{id}")
    SysDict dictionarieEntity(int id);

    //查询字典记录
    @Select("select * from sys_dict t where t.pid=#{id}")
    List<SysDict> byPidDictionarieEntity(int id);

    List<SysDict> list(Map<String, Object> params);

    @Insert("insert into sys_dict (num,pid,name,tips,code) values (#{num},#{pid},#{name},#{tips},#{code})")
    int saveEntity(SysDict dictionarie);

    //删除字典
    @Delete("delete from sys_dict where id=#{id}")
    int deleteEntity(int id);

    //删除字典记录
    @Delete("delete from sys_dict where pid=#{id}")
    int byPidDeleteEntity(int id);

    SysDict selectEntity(String name, String code);


    int updateEntity(SysDict dictionarie);

    SysDict selectByCode(@Param("code") String code);

    List<SysDict> queryLists(@Param("pid") Integer pid);
}
