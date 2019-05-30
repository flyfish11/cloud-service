package com.cloud.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cloud.model.common.Page;
import com.cloud.model.user.AppUser;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.SysRole;

public interface AppUserService {

	void addAppUser(AppUser appUser);

	void updateAppUser(AppUser appUser);

	LoginAppUser findByAccount(String account);

	AppUser findById(String id);

	void setRoleToUser(String id, Set<String> roleIds);

	void updatePassword(String id, String oldPassword, String newPassword);

	Page<AppUser> findUsers(Map<String, Object> params);

	List<AppUser> findUsers1(Map<String, Object> params);

	List<AppUser> findUsers2(Map<String, Object> params);


	Set<SysRole> findRolesByUserId(String userId);

	void bindingPhone(String userId, String phone);
}
