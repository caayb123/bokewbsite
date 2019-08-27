package com.dream.dao;

import com.dream.domain.UserInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserInfoDao extends Mapper<UserInfo> {
}
