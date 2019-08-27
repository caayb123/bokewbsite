package com.dream.sercurity.phone;

import com.dream.utils.SercurityContextUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
      * @Description: 处理手机认证成功的角色跳转处理器
      * @Param: 方法参数
      * @return: 返回值
      * @Author: cyb
      */
public class PhoneSuccessHandle implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String contextPath = request.getContextPath();
        if (roles.contains("ROLE_ADMIN")) {

            response.sendRedirect(contextPath + "/admin/jumpAdmin");
            return;
        }
        if (roles.contains("ROLE_USER")) {
            response.sendRedirect(contextPath + "/personal/list");
            return;
        }

        request.setAttribute("phoneError","fenghao");  //传递封号参数
        request.getRequestDispatcher("/login.jsp").forward(request,response);
        request.getSession().invalidate();  //由于认证通过了，因此要释放session同理security基于session的用户信息也被释放
    }
}
