package com.security.entity;

import com.aim.model.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @AUTO 安全用户验证实体
 * @FILE UserDetail.java
 * @DATE 2017-11-2 下午4:32:31
 * @Author AIM
 */
public class UserDetail extends SysUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetail(Long id, String username, String password, Boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.setUsername(username);
        this.setPassword(password);
        this.setEnabled(enabled);
        this.authorities = authorities;
    }

    /**
     * 用户拥有的权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return authorities;
    }

    /**
     * 密码
     */
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    /**
     * 用户名
     */
    @Override
    public String getUsername() {
        return super.getUsername();
    }

    /**
     * 用户账号是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * 用户账号是否被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * 用户密码是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * 用户是否可用
     */
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return this.getEnabled();
    }

    /**
     * 判断对象是用户实体并判断用户信息是否相同
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SysUser) {
            return getUsername().equals(((SysUser) obj).getUsername());
        }
        return false;
    }

    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

}
