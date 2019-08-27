package com.dream.service;

import com.dream.domain.Comment;


import java.util.List;
import java.util.Map;


public interface CommentService {


    /**
     * 添加评论
     * @param comment
     * @return
     */
   int add(Comment comment);


    /**
     * 更新评论
     * @param comment
     */
   void update(Comment comment);
    /**
         * @Description:根据文章ID查询所有评论（一级评论，子评论）
         * @Param: 方法参数
         * @return: 返回值
         * @Author: cyb
         */
    List<Comment> findByCid(Long content_id);

//    /**
//     * 根据文章id查询所有一级评论
//     * @return
//     */
//  List<Comment> findAll(Long content_id);

    /**
     * 根据id查询评论
     * @param id
     * @return
     */
  Comment findById(Long id);

    /**
     * 根据文章id查询所有父评论
     */
    List<Comment> findAllFirstComment(Long content_id);

    /**
     * 根据文章id和子评论ids查询所有子评论
     */
    List<Comment> findAllChildrenComment(Long content_id, String children);


    /**
     * 根据id删除评论
     * @param id
     */
    void deleteById(Long id);

    /**
     * 批量删除子评论
     * @param children
     */
    void deleteChildrenComment(String children);

     /**
          * @Description: 评论删除逻辑业务,返回删除了多少条评论用于更新文章表的评论数
          * @Param: 方法参数
          * @return: 返回值
          * @Author: cyb
          */
     int delete(Long id,Long uid,Long con_id,Long fid);

      /**
           * @Description: 根据文章id删除所有评论
           * @Param: 方法参数
           * @return: 返回值
           * @Author: cyb
           */
      void deleteByCid(Long cid);


}
