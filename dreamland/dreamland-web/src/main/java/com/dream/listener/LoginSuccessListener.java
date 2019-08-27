package com.dream.listener;

import com.dream.domain.LoginLog;
import com.dream.domain.User;
import com.dream.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
      * @Description: 日志监听器,记录用户登录日志
      * @Param: 方法参数
      * @return: 返回值
      * @Author: cyb
      */
@Component
public class LoginSuccessListener implements ApplicationListener {

    @Autowired
    private LoginLogService loginLogService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof AuthenticationSuccessEvent){
            AuthenticationSuccessEvent authEvent = (AuthenticationSuccessEvent) event;  //如果event是AuthenticationSuccessEvent类型将其强转
            WebAuthenticationDetails details = (WebAuthenticationDetails) authEvent.getAuthentication().getDetails(); //获取 WebAuthenticationDetails对象
            User principal = (User) authEvent.getAuthentication().getPrincipal();  //获取User对象
            LoginLog loginLog = new LoginLog();
            loginLog.setCreateTime(new Date());
            loginLog.setIp(details.getRemoteAddress());
            loginLog.setuId(principal.getId());
            loginLogService.add(loginLog);   //添加日志
        }
    }
}
