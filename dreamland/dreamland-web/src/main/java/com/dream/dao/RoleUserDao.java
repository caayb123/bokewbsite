package com.dream.dao;

import com.dream.domain.RoleUser;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface RoleUserDao extends Mapper <RoleUser> {
}
