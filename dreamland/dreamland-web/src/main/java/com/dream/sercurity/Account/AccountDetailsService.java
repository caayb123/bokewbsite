package com.dream.sercurity.Account;

import com.dream.domain.Role;
import com.dream.domain.User;
import com.dream.service.RoleService;
import com.dream.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

 /**
      * @Description: UserDetailsService作为安全框架在做认证时规范的具体方法，也是用于结合数据库认证的必备
      * @Author: cyb
      */
public class AccountDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //实现UserDetaisService作为规范化我们在做认证时的具体方法
        User byEmail = userService.findByEmail(email);
        if (byEmail==null){
            //如果email为空直接抛出异常
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        //如果不为空则查询该用户的所有角色信息并添加到user中返回
        List<Role> byUid = roleService.findByUid(byEmail.getId());
       byEmail.setRoles(byUid);
        return byEmail;
    }
}
