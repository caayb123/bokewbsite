package com.dream.sercurity.phone;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
      * @Description: 手机登录认证失败处理器
      * @Param: 方法参数
      * @return: 返回值
      * @Author: cyb
      */
public class PhoneAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private String defaultFailureUrl;  //认证失败后跳转的Url

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String telephone = request.getParameter("telephone");
        request.setAttribute("phoneError","phone");
        request.setAttribute("phoneNum",telephone);
        request.getRequestDispatcher(defaultFailureUrl).forward(request, response);

    }

    public String getDefaultFailureUrl() {
        return defaultFailureUrl;
    }

    @Override
    public void setDefaultFailureUrl(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }
}
