package com.dream.service.impl;


import com.dream.dao.RoleUserDao;
import com.dream.domain.RoleUser;
import com.dream.domain.User;
import com.dream.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleUserServiceImpl implements RoleUserService {
    @Autowired
    private RoleUserDao roleUserDao;
    @Override
    public List<RoleUser> findByUser(User user) {
        return null;
    }

    @Override
    public int add(RoleUser roleUser) {
          roleUserDao.insert(roleUser);
        return 0;
    }

    @Override
    public void deleteByUid(Long uid) {
        RoleUser roleUser=new RoleUser();
        roleUser.setuId(uid);
        roleUserDao.delete(roleUser);
    }
}
