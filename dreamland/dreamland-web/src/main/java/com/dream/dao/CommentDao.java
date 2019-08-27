package com.dream.dao;

import com.dream.domain.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CommentDao extends Mapper<Comment> {
    //对 comment 表和 user 表进行联合查询，把指定文章 id 并且评论者 id 与用户表中 id 相同的所有评论查询出来
    List<Comment> selectAllByCommentId(@Param("cid")long cid);

    //根据文章id查询所有一级评论
    List<Comment> findAllFirstComment(@Param("cid")long cid);

    //根据文章id和一级评论的二级评论内容查询出所有二级评论
    List<Comment> findAllChildrenComment(@Param("cid")long cid,@Param("children")String children);


    //插入评论并返回主键id 返回值是影响行数  id在Comment对象中
    int insertComment(Comment comment);
}
