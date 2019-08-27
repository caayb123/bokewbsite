package com.dream.dao;

import com.dream.domain.User;
import com.dream.domain.UserContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserContentDao extends Mapper<UserContent> {

    List<UserContent> findCategoryByUid(@Param(value = "uid") Long uid);

    List<UserContent> findHotDream();

    int insertBackId(UserContent userContent);

    List<UserContent> findByJoin();

    UserContent findById(@Param(value = "id") Long id);
}
