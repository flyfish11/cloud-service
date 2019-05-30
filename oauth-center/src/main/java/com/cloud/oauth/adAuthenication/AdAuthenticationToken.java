package com.cloud.oauth.adAuthenication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * ad域认证token
 */
public class AdAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Object principal;
    /**
     * 未认证时，内容为手机号
     * @param
     */
    public AdAuthenticationToken(String account) {
        super(null);
        this.principal = account;
        setAuthenticated(false);
    }
    /**
     * 认证成功后，其中为用户信息
     * @param principal
     * @param authorities
     */
    public AdAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
    }

    public AdAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal) {
        super(authorities);
        this.principal = principal;
    }

    public Object getCredentials() {
        return null;
    }
    public Object getPrincipal() {
        return this.principal;
    }
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}