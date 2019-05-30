package com.cloud.workflow.feign;

import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.SysRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("user-center")
public interface UserClient {

    @GetMapping(value = "/users-anon/internal", params = "account")
    LoginAppUser findByAccount(@RequestParam("account") String account);

    @GetMapping("/wechat/login-check")
    public void wechatLoginCheck(@RequestParam("tempCode") String tempCode, @RequestParam("openid") String openid);

    @GetMapping(value = "/users-sysRole/internal", params = "account")
    public List<SysRole> querySysRole();
}
