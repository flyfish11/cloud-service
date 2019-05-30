package com.cloud.oauth.adAuthenication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class AdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        AdAuthenticationFilter adAuthenticationFilter = new AdAuthenticationFilter();
        adAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        adAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        AdAuthenticationProvider smsCodeAuthenticationProvider = new AdAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
        http.authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterAfter(adAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
