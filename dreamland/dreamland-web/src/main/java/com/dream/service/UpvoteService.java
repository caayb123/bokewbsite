package com.dream.service;

import com.dream.domain.Upvote;

import java.util.Map;


public interface UpvoteService {
    /**
     * 根据用户id和文章id查询点赞对象并判断是否可以点赞或者踩
     * @param upvote
     * @return
     */
   Map<String,String> findByUidAndConId(Upvote upvote, int upvote1);

    /**
     * 添加upvote
     * @param upvote
     * @return
     */
   int add(Upvote upvote);



    /**
     * 更新Upvote
     * @param upvote
     */
   void update(Upvote upvote);

    /**
         * @Description: 根据文章id删除点赞表
         */
    void deleteByCid(Long cid);
}
