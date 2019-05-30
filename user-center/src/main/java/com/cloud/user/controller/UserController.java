package com.cloud.user.controller;

import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.AppUser;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.SysRole;
import com.cloud.user.feign.SmsClient;
import com.cloud.user.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private AppUserService appUserService;

    /**
     * 当前登录用户 LoginAppUser
     *
     * @return
     */
    @GetMapping("/users/current")
    public LoginAppUser getLoginAppUser() {
        return AppUserUtil.getLoginAppUser();
    }

    @GetMapping(value = "/users-anon/internal", params = "account")
    public LoginAppUser findByAccount(String account) {
        return appUserService.findByAccount(account);
    }

    /**
     * 用户查询
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('back:user:query')")
    @GetMapping("/users")
    public Page<AppUser> findUsers(@RequestParam Map<String, Object> params) {

        List<AppUser> users1 = appUserService.findUsers1(params);

        Page<AppUser> users = new Page<AppUser>();
        users.setData(users1);
        users.setCount(10);
        return users;
    }


    @GetMapping("/user/list")
    public Result findUsers2(@RequestParam Map<String, Object> params) {


        Page<AppUser> users = appUserService.findUsers(params);
        return ResultUtil.success(users.getTotal(),users.getData());
    }
    @GetMapping("/act/users")
    public Page<AppUser> actFindUsers(@RequestParam Map<String, Object> params) {

        return  appUserService.findUsers(params);
    }

    @PreAuthorize("hasAuthority('back:user:query')")
    @GetMapping("/users/{id}")
    public AppUser findUserById(@PathVariable String id) {
        return appUserService.findById(id);
    }

    /**
     * 添加用户,根据用户名注册
     *
     * @param appUser
     * @return
     */
    @PostMapping("/users-anon/register")
    public AppUser register(@RequestBody AppUser appUser) {
        // 用户名等信息的判断逻辑挪到service了
        appUserService.addAppUser(appUser);

        return appUser;
    }

    /**
     * 修改自己的个人信息
     *
     * @param appUser
     * @return
     */
    @LogAnnotation(module = LogModule.UPDATE_ME)
    @PutMapping("/users/me")
    public AppUser updateMe(@RequestBody AppUser appUser) {
        AppUser user = AppUserUtil.getLoginAppUser();
        appUser.setId(user.getId());

        appUserService.updateAppUser(appUser);

        return appUser;
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     */
    @LogAnnotation(module = LogModule.UPDATE_PASSWORD)
    @PutMapping(value = "/users/password", params = {"oldPassword", "newPassword"})
    public void updatePassword(String oldPassword, String newPassword) {
        if (StringUtils.isBlank(oldPassword)) {
            throw new IllegalArgumentException("旧密码不能为空");
        }
        if (StringUtils.isBlank(newPassword)) {
            throw new IllegalArgumentException("新密码不能为空");
        }

        AppUser user = AppUserUtil.getLoginAppUser();
        appUserService.updatePassword(user.getId(), oldPassword, newPassword);
    }

    /**
     * 管理后台，给用户重置密码
     *
     * @param id
     * @param newPassword
     */
    @LogAnnotation(module = LogModule.RESET_PASSWORD)
    @PreAuthorize("hasAuthority('back:user:password')")
    @PutMapping(value = "/users/{id}/password", params = {"newPassword"})
    public void resetPassword(@PathVariable String id, String newPassword) {
        appUserService.updatePassword(id, null, newPassword);
    }

    /**
     * 管理后台修改用户
     *
     * @param appUser
     */
    @LogAnnotation(module = LogModule.UPDATE_USER)
    @PreAuthorize("hasAuthority('back:user:update')")
    @PutMapping("/users")
    public void updateAppUser(@RequestBody AppUser appUser) {
        appUserService.updateAppUser(appUser);
    }

    /**
     * 管理后台给用户分配角色
     *
     * @param id
     * @param roleIds
     */
    @LogAnnotation(module = LogModule.SET_ROLE)

    @PreAuthorize("hasAuthority('back:user:role:set')")
    @PostMapping("/users/{id}/roles")
    public void setRoleToUser(@PathVariable String id, @RequestBody Set<String> roleIds) {
        appUserService.setRoleToUser(id, roleIds);
    }

    /**
     * 获取用户的角色
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('back:user:role:set','user:role:byuid')")
    @GetMapping("/users/{id}/roles")
    public Set<SysRole> findRolesByUserId(@PathVariable String id) {
        String s=id;
        return appUserService.findRolesByUserId(id);
    }

    @Autowired
    private SmsClient smsClient;

    @PostMapping(value = "/users/binding-phone")
    public void bindingPhone(String phone, String key, String code) {
        if (StringUtils.isBlank(phone)) {
            throw new IllegalArgumentException("手机号不能为空");
        }

        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("key不能为空");
        }

        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("code不能为空");
        }

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        log.info("绑定手机号，key:{},code:{},username:{}", key, code, loginAppUser.getUsername());

        String value = smsClient.matcheCodeAndGetPhone(key, code, false, 30);
        if (value == null) {
            throw new IllegalArgumentException("验证码错误");
        }

        if (phone.equals(value)) {
            appUserService.bindingPhone(loginAppUser.getId(), phone);
        } else {
            throw new IllegalArgumentException("手机号不一致");
        }
    }


}
