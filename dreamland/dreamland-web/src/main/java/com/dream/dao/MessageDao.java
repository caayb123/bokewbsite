package com.dream.dao;

import com.dream.domain.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface MessageDao extends Mapper<Message> {

   List<Message> findById(@Param(value = "rid") Long rid, @Param(value = "see") String see);

   void updateAll(@Param(value = "rid") Long rid);



}
