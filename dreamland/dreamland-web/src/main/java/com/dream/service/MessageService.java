package com.dream.service;

import com.dream.domain.Message;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MessageService {
    /**
         * @Description: 添加一条消息
         * @Param: 方法参数
         * @return: 返回值
         * @Author: cyb
         */
    void add(Message message);
     /**
          * @Description: 根据接收者id和是否阅读查询消息
          * @Param: 方法参数
          * @return: 返回值
          * @Author: cyb
          */
     PageInfo<Message> findAll(Long rid,String see,Integer pageNum);

      /**
           * @Description: 根据用户id更新所有状态为已读
           * @Param: 方法参数
           * @return: 返回值
           * @Author: cyb
           */
     void updateAll(Long rid);

      /**
           * @Description: 根据消息id更新状态为已读
           * @Param: 方法参数
           * @return: 返回值
           * @Author: cyb
           */
      int updateById(Long id);

       /**
            * @Description: 根据用户id删除所有消息
            * @Param: 方法参数
            * @return: 返回值
            * @Author: cyb
            */
      void deleteAll(Long rid);
      /**
           * @Description: 根据用户id查询所有消息
           * @Param: 方法参数
           * @return: 返回值
           * @Author: cyb
           */
      List<Message> findAll(Long rid);

       /**
            * @Description: 根据文章ID删除所有消息
            * @Param: 方法参数
            * @return: 返回值
            * @Author: cyb
            */
      int deleteByCid(Long cid);

       /**
            * @Description: 根据接收者和是否阅读消息查询消息
            * @Param: 方法参数
            * @return: 返回值
            * @Author: cyb
            */
      List<Message> findAll(Long rid,String see);
}
