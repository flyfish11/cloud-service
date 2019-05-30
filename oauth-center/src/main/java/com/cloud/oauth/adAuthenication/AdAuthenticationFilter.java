package com.cloud.oauth.adAuthenication;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * request中必须含有mobile参数
     */

    /**
     * post请求
     */
    private boolean postOnly = true;

    protected AdAuthenticationFilter() {
        /**
         * 处理的手机验证码登录请求处理url
         */
        super(new AntPathRequestMatcher("/adLogin", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        //判断是是不是post请求
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //从请求中获取手机号码
        String account = obtainMobile(request);

        if (account == null) {
            account = "";
        }

        account = account.trim();
        //创建SmsCodeAuthenticationToken(未认证)
        AdAuthenticationToken authRequest = new AdAuthenticationToken(account);

        //设置用户信息
        setDetails(request, authRequest);
        //返回Authentication实例
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 获取手机号
     */
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter("account");
    }

    protected void setDetails(HttpServletRequest request, AdAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}