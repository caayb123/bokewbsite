package com.dream.sercurity.phone;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
 /**
      * @Description: 手机认证令牌
      * @Param: 方法参数
      * @return: 返回值
      * @Author: cyb
      */
public class PhoneAuthenticationToken extends AbstractAuthenticationToken {
     private final Object principal;

     public PhoneAuthenticationToken(Object principal) {
         super((Collection)null);       //权限设置为null
         this.principal = principal;   //将手机号赋值给principal
         this.setAuthenticated(false);  //将认证状态设置为false
     }
     public PhoneAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
         super(authorities);   //传入权限集合
         this.principal = principal; //传入用户信息
         super.setAuthenticated(true); //认证状态设置为true
     }


    @Override
    public Object getCredentials() {
        return null;   //没有用到密码所以返回null
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }


}
