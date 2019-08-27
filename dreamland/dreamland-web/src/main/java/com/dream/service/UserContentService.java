package com.dream.service;

import com.dream.domain.Comment;
import com.dream.domain.UserContent;
import com.github.pagehelper.PageInfo;


import java.util.List;

public interface UserContentService {
    /**
     * 查询所有Content并分页
     * @param content
     * @param pageNum
     * @return
     */
    PageInfo<UserContent> findAll(UserContent content, Integer pageNum);
    PageInfo<UserContent> findAll(UserContent content, Comment comment, Integer pageNum);
    PageInfo<UserContent> findAllByUpvote(UserContent content, Integer pageNum);


     /**
          * @Description: 分页查询文章
          * @Param: 方法参数
          * @return: 返回值
          * @Author: cyb
          */
    PageInfo<UserContent> findAll(Integer pageNum);

    /**
     * 查询热梦（根据点赞数）
     */
    PageInfo<UserContent> findHotDream(Integer pageNum);

    /**
     * 添加文章并返回自增id
     * @param content
     */
    void addContent(UserContent content);

    /**
     * 根据用户id查询文章集合
     * @param uid
     * @return
     */
    List<UserContent> findByUserId(Long uid);

    /**
     * 查询所有文章
     * @return
     */
    List<UserContent> findAll();

    /**
     * 根据文章id查找文章
     * @param id
     * @return
     */
    UserContent findById(long id);
    /**
     * 根据文章id更新文章
     * @param content
     * @return
     */
    void updateById(UserContent content);

     /**
      * 根据用户id查询查询出梦分类
      */
     List<UserContent> findCategoryByUid(Long uid);

      /**
          *根据文章id删除文章
           */
      void deleteById(Long id);

}
