package com.dream.service.impl;

import com.dream.dao.UserContentDao;
import com.dream.domain.Comment;
import com.dream.domain.UserContent;
import com.dream.service.UserContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserContentServiceImpl implements UserContentService {
    @Autowired
    private UserContentDao userContentDao;
    @Override
    public PageInfo<UserContent> findAll(UserContent content, Integer pageNum) {
        PageHelper.startPage(pageNum,10);
        List<UserContent> select = userContentDao.select(content);
        PageInfo<UserContent> userContentPageInfo=new PageInfo<>(select);
        return userContentPageInfo;
    }

    @Override
    public PageInfo<UserContent> findAll(UserContent content, Comment comment, Integer pageNum) {
        return null;
    }

    @Override
    public PageInfo<UserContent> findAllByUpvote(UserContent content, Integer pageNum) {
        return null;
    }

    @Override
    public PageInfo<UserContent> findAll(Integer pageNum) {
        if (pageNum==null){
            pageNum=1;
        }
        String orderBy="id desc";
        PageHelper.startPage(pageNum,7,orderBy);
        List<UserContent> userContents = userContentDao.findByJoin();
        PageInfo<UserContent> userContentPageInfo = new PageInfo<>(userContents);
        return userContentPageInfo;
    }

    @Override
    public PageInfo<UserContent> findHotDream(Integer pageNum) {
        PageHelper.startPage(pageNum,15);
        List<UserContent> hotDream = userContentDao.findHotDream();
        PageInfo<UserContent> userContentPageInfo=new PageInfo<>(hotDream);
        return userContentPageInfo;
    }

    @Override
    public void addContent(UserContent content) {
             userContentDao.insertBackId(content);
    }

    @Override
    public List<UserContent> findByUserId(Long uid) {
        return null;
    }

    @Override
    public List<UserContent> findAll() {
       return userContentDao.selectAll();
    }

    @Override
    public UserContent findById(long id) {
        return userContentDao.findById(id);
    }

    @Override
    public void updateById(UserContent content) {
          userContentDao.updateByPrimaryKeySelective(content);
    }

    @Override
    public List<UserContent> findCategoryByUid(Long uid) {
       return userContentDao.findCategoryByUid(uid);
    }

    @Override
    public void deleteById(Long id) {
        userContentDao.deleteByPrimaryKey(id);
    }


}
