package com.dream.sercurity.Account;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
      * @Description: UsernamePasswordAuthenticationFilter作为安全框架处理用户认证逻辑的过滤器
      * @Author: cyb
      */
public class AccountAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
           private String codeParameter="code";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);   //调用父类方法获取用户名和密码
         String code = this.obtainCode(request);   //调用自定义方法获取验证码
        String caChecode = (String)request.getSession().getAttribute("VERCODE_KEY"); //调用session中的验证码
        if (!(StringUtils.isNotBlank(code)&&code.equals(caChecode))){
            throw new UsernameNotFoundException("验证码错误");  //验证码不对认证失败抛出异常
        }
        if(username == null) {
            username = "";
        }

        if(password == null) {
            password = "";
        }
        username=username.trim();
        //将用户名和密码封装到UsernamePasswordAuthenticationToken对象中并设置请求信息，找到支持的 AuthenticationProvider 进行认证。
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
      return request.getParameter("email");
    }

    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(this.codeParameter);
    }
}
