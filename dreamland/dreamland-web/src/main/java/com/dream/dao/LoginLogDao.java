package com.dream.dao;

import com.dream.domain.LoginLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface LoginLogDao extends Mapper<LoginLog> {
}
