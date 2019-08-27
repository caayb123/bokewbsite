package com.dream.sercurity.phone;

import org.springframework.security.core.AuthenticationException;

/**
      * @Description: 普通登录异常是UserNotFoundException，手机的则是PhoneNotFoundException
      * @Param: 方法参数
      * @return: 返回值
      * @Author: cyb
      */
public class PhoneNotFoundException extends AuthenticationException {
    public PhoneNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public PhoneNotFoundException(String msg) {
        super(msg);
    }
}
