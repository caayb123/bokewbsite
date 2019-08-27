package com.dream.sercurity.phone;

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
      * @Description: 和普通认证一样结合数据库必须的规范
      * @Param: 方法参数
      * @return: 返回值
      * @Author: cyb
      */
public class PhoneUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
  @Autowired
  private RoleService roleService;
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User byPhone = userService.findByPhone(phone);
        if (byPhone==null){
            throw new PhoneNotFoundException("手机号码错误");
        }
        List<Role> byUid = roleService.findByUid(byPhone.getId());
        byPhone.setRoles(byUid);
        return byPhone;
    }
}
