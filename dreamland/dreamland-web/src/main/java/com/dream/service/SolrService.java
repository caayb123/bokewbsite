package com.dream.service;

import com.dream.domain.UserContent;
import com.github.pagehelper.PageInfo;

public interface SolrService {
     /**
          * @Description: 根据关键字搜索文章并分页
          * @Param: 方法参数
          * @return: 返回值
          * @Author: cyb
          */
     PageInfo<UserContent> findByKeyWords(String keyword, Integer pageNum);

    /**
     * 添加文章到solr索引库中
     */
    void addUserContent(UserContent userContent);

    /**
     * 更新solr索引库
     */
    void updateUserContent(UserContent userContent);

    /**
     * 根据文章id删除索引库
     */
    void deleteById(Long id);
}
