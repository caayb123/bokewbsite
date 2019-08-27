package com.dream.service;

import com.dream.domain.User;

import java.util.List;
import java.util.Map;


public interface UserService {
    /**
     * 用户注册
     * @param user
     * @return
     */
    int regist(User user);

    /**
     * 用户登录
     * @param email
     * @param password
     * @return
     */
    User login(String email, String password);

    /**
     * 根据用户邮箱查询用户
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 根据用户手机号查询用户
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * 根据用户id查询用户
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     * 根据用户邮箱删除用户
     * @param email
     */
    void deleteByEmail(String email);

    /**
     * 更新用户信息
     * @param user
     */
    void update(User user);

     /**
          * @Description: 查询所有用户
          */
     List<User> findAll();

     void updateFollow(Long oid,Long uid,String flag);

    Map<String,List<User>> findFollowByFollow(Long id);
}
