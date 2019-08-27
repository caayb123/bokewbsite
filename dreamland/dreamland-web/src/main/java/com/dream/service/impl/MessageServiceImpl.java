package com.dream.service.impl;

import com.dream.dao.MessageDao;
import com.dream.domain.Message;
import com.dream.service.MessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;
    @Override
    public void add(Message message) {
        messageDao.insert(message);
    }

    @Override
    public PageInfo<Message> findAll(Long rid, String see,Integer pageNum) {
        if (pageNum==null){
            pageNum=1;
        }
        Message message=new Message();
        message.setRid(rid);
        if (see!=null){
            message.setSee(see);
        }

        PageHelper.startPage(pageNum,8,"id desc");
        List<Message> list = messageDao.findById(rid, see);
        PageInfo<Message> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void updateAll(Long rid) {
        messageDao.updateAll(rid);
    }

    @Override
    public int updateById(Long id) {

        Message message = messageDao.selectByPrimaryKey(id);
        if (message.getSee().equals("0")){
            message.setSee("1");
            messageDao.updateByPrimaryKey(message);  //更新字段
            return 0;  //需要更新redis
        }else {
            return 1;  //不需要更新redis
        }

    }

    @Override
    public void deleteAll(Long rid) {
        Message message=new Message();
        message.setRid(rid);
        messageDao.delete(message);
    }

    @Override
    public List<Message> findAll(Long rid) {
        Message message=new Message();
        message.setRid(rid);
        return messageDao.select(message);
    }

    @Override
    public int deleteByCid(Long cid) {
        Message message=new Message();
        message.setCid(cid);
        return messageDao.delete(message);

    }

    @Override
    public List<Message> findAll(Long rid, String see) {
        Message message=new Message();
        message.setRid(rid);
        if (see!=null){
            message.setSee(see);
        }
      return   messageDao.findById(rid,see);
    }
}
