package com.dream.service.impl;

import com.dream.dao.UserInfoDao;
import com.dream.domain.UserInfo;
import com.dream.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo findByUid(Long id) {
        UserInfo userInfo=new UserInfo();
        userInfo.setuId(id);
        return userInfoDao.selectOne(userInfo);
    }

    @Override
    public void update(UserInfo userInfo) {
    userInfoDao.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    public void add(UserInfo userInfo) {
       userInfoDao.insert(userInfo);
    }
}
