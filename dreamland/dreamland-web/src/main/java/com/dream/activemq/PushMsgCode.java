package com.dream.activemq;

import com.dream.service.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Date;


@Component
public class PushMsgCode implements MessageListener {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MessageService messageService;
    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;

        try {
            String conut = (String) redisTemplate.opsForValue().get(mapMessage.getString("rid")); //获取被评论者的消息数目
            if (!StringUtils.isBlank(conut)){
                //不为空说明有未读消息
                Integer i = Integer.parseInt(conut)+1;
                redisTemplate.opsForValue().set(mapMessage.getString("rid"),i.toString());  //redis中计数加1
            }else {
                //为空说明没有未读消息直接设置为1
                redisTemplate.opsForValue().set(mapMessage.getString("rid"),"1");  //redis中计数设置为1

            }
            //持久化该消息进入数据库中
            com.dream.domain.Message msg=new com.dream.domain.Message();
            msg.setSee("0"); //未读
            msg.setRid(Long.parseLong(mapMessage.getString("rid")));
            msg.setUid(Long.parseLong(mapMessage.getString("uid")));
            msg.setType(mapMessage.getString("type"));
            msg.setCid(Long.parseLong(mapMessage.getString("cid")));
            msg.setDate(new Date());
            messageService.add(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
