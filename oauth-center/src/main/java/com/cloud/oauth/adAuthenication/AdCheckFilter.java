package com.cloud.oauth.adAuthenication;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.*;
import javax.naming.directory.*;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 *
 */
public class AdCheckFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;


    private Set<String> urls = new HashSet<>();


    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
       /* String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(),",");
        for (String configUrl : configUrls) {
            urls.add(configUrl);
        }*/
        urls.add("/adLogin");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 当请求为登陆时 并且是post请求时 才进行过滤 进行密码校验
        if (StringUtils.equals("/adLogin", request.getRequestURI())
                && StringUtils.equals(request.getMethod(), "POST")) {
            try {
                validate(new ServletWebRequest(request));
            } catch (Exception e) {
                return;
            }
        }
        filterChain.doFilter(request, response);

    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String host = "10.130.14.9";//AD域IP，必须填写正确
        String domain = "@hyhhgroup.com";//域名后缀，例.@noker.cn.com
        String port = "389"; //端口，一般默认389
        String url = new String("ldap://" + host + ":" + port);//固定写法
        Hashtable env = new Hashtable();//实例化一个Env
        DirContext ctx = null;
        env.put(Context.SECURITY_AUTHENTICATION, "none");//LDAP访问安全级别(none,simple,strong),一种模式，这么写就行
        env.put(Context.SECURITY_PRINCIPAL, account+domain); //用户名
        env.put(Context.SECURITY_CREDENTIALS, password);//密码
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");// LDAP工厂类
        env.put(Context.PROVIDER_URL, url);//Url

        List<String> list = new LinkedList<String>();
        NamingEnumeration results = null;
        try {
            ctx = new InitialDirContext(env);// 初始化上下文
            System.out.println("身份验证成功!");
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            results = ctx.search("", "(objectclass=person)", controls);
            while (results.hasMore()) {
                SearchResult searchResult = (SearchResult) results.next();
                Attributes attributes = searchResult.getAttributes();
                Attribute attr = attributes.get("cn");
                String cn = attr.get().toString();
                list.add(cn);
            }
        } catch (AuthenticationException e) {
            System.out.println("身份验证失败!");
            e.printStackTrace();
        } catch (javax.naming.CommunicationException e) {
            System.out.println("AD域连接失败!");
            e.printStackTrace();
        } catch (NameNotFoundException e) {
            System.out.println("用户名不存在");
            e.printStackTrace();
        } catch (NamingException e) {
            //throw new RuntimeException(e);
        }  catch (Exception e) {
            System.out.println("身份验证未知异常!");
            e.printStackTrace();
        }finally {
            if (null != ctx) {
                try {
                    ctx.close();
                    ctx = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        //进行ad认证
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }


    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

}
