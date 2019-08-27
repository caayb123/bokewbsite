package com.dream.interceptor;
import com.dream.dao.UserContentDao;
import com.dream.domain.User;
import com.dream.domain.UserContent;
import com.dream.utils.SercurityContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import javax.servlet.*;

import java.io.IOException;
import java.util.List;


public class IndexJspFilter implements Filter{
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("===========自定义过滤器==========");
        ServletContext context = request.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        UserContentDao userContentDao = ctx.getBean(UserContentDao.class);//过滤器初始化时bean还未初始化，此时需要手动获取dao
        String orderBy="id desc";//按照时间倒序
        PageHelper.startPage(1, 7,orderBy);//开始分页
        List<UserContent> list = userContentDao.findByJoin();
        PageInfo<UserContent> endPage=new PageInfo<>(list);//分页结束,返回分页对象
        request.setAttribute("page", endPage );
        User user = SercurityContextUtils.getCurrentUser();
        request.setAttribute("user",user);
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
