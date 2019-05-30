package com.cloud.user.dao;

import com.cloud.model.user.AppUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppUserDao {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into app_user" +
            "(id,username,account,password,fullName,headImgUrl,phone,email,idNumber,sex,enabled,isDelete, type, createTime, updateTime, depId, remark, version, createBy)" +
            "values" +
            "(#{id},#{username},#{account},#{password},#{fullName},#{headImgUrl},#{phone},#{email},#{idNumber},#{sex},#{enabled},#{isDelete},#{type},#{createTime},#{updateTime},#{depId},#{remark},#{version},#{createBy})")
    int save(AppUser appUser);

    int update(AppUser appUser);

    @Select("select * from app_user t where t.username = #{username}")
    AppUser findByUsername(String username);

    @Select("select * from app_user t where t.id = #{id}")
    AppUser findById(String id);

    int count(Map<String, Object> params);

    List<AppUser> findData(Map<String, Object> params);

    List<AppUser> findusers(Map<String, Object> params);

    @Select("select * from app_user t")
    List<AppUser> findAllUser();



}
