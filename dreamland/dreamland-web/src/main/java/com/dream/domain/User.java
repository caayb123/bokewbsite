package com.dream.domain;


import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class User  implements UserDetails {

    @Id
    private Long id;

    private String email;

    private String password;

    private String phone;

    private String nickName;

    private String state;

    private String imgUrl;

    private String enable;

    private String follow;

    private String byfollow;

    @Transient
    protected List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

//    userdetails接口的方法
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //该方法用于获取用户角色信息,并且进行授权
     if (roles==null||roles.size()<=0) {
         return null;
     }
      List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for(Role r:roles){
            authorities.add(new SimpleGrantedAuthority(r.getRoleValue()));
        }
        return authorities;
    }



    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        //账户未过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //账户未锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //密码未过期
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(StringUtils.isNotBlank(state) && "1".equals(state) && StringUtils.isNotBlank(enable) && "1".equals(enable)){
            return true;
        }
        return false;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable == null ? null : enable.trim();
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getByfollow() {
        return byfollow;
    }

    public void setByfollow(String byfollow) {
        this.byfollow = byfollow;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return getEmail().equals(((User)obj).getEmail())||getUsername().equals(((User)obj).getUsername());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }
}