package com.dream.dao;

import com.dream.domain.Resource;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ResourceDao extends Mapper<Resource> {
}
