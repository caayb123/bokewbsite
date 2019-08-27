package com.dream.dao;

import com.dream.domain.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;



@Repository
public interface UserDao extends Mapper<User> {

}
