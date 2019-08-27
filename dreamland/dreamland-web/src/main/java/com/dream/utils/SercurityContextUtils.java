package com.dream.utils;

import com.dream.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SercurityContextUtils {
    /**
     * 获取当前用户
     * @return
     */
    public static User getCurrentUser(){
        User user = null;
        Authentication authentication = null;
        SecurityContext context = SecurityContextHolder.getContext();
        if(context!=null){
            authentication = context.getAuthentication();
        }
        if(authentication!=null){
            Object principal = authentication.getPrincipal();
            //如果是匿名用户
            if(authentication.getPrincipal().toString().equals( "anonymousUser" )){
                return null;
            }else {
                user = (User)principal;

            }

        }
        return user;
    }
}
