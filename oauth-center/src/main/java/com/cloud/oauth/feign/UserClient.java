package com.cloud.oauth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloud.model.user.LoginAppUser;

@FeignClient("user-center")
public interface UserClient {

    @GetMapping(value = "/users-anon/internal", params = "account")
    LoginAppUser findByAccount(@RequestParam("account") String account);

    @GetMapping("/wechat/login-check")
    public void wechatLoginCheck(@RequestParam("tempCode") String tempCode, @RequestParam("openid") String openid);
}
