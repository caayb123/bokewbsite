package com.dream.sercurity.Account;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
 /**
      * @Description: security认证失败的逻辑处理器
      * @Param: 方法参数
      * @return: 返回值
      * @Author: cyb
      */
public class AccountAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private String defaultFailureUrl;
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if("User is disabled".equals(exception.getMessage())){
            request.setAttribute("error","active");
        }else {
            request.setAttribute("error", "fail");
        }
        String email = request.getParameter("email");
        request.setAttribute("email", email);
        request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
    }
    @Override
    public void setDefaultFailureUrl(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }

    public String getDefaultFailureUrl() {
        return defaultFailureUrl;
    }
}
