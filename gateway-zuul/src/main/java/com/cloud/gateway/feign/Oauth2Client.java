package com.cloud.gateway.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("oauth-center")
public interface Oauth2Client {

	/**
	 * 获取access_token
	 * 
	 * @param parameters
	 * @return
	 */
	@PostMapping(path = "/oauth/token")
	Map<String, Object> postAccessToken(@RequestParam Map<String, String> parameters);

	@PostMapping(path = "/adLogin")
	Object adLlogin(@RequestParam Map<String, String> parameters);

	/**
	 * 删除access_token和refresh_token
	 * 
	 * @param access_token
	 */
	@DeleteMapping(path = "/remove_token")
	void removeToken(@RequestParam("access_token") String access_token);

}
