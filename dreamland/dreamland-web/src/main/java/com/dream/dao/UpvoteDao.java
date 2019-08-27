package com.dream.dao;

import com.dream.domain.Upvote;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UpvoteDao extends Mapper<Upvote> {
}
