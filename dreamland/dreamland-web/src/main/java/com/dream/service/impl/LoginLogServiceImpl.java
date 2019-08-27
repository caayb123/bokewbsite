package com.dream.service.impl;

import com.dream.dao.LoginLogDao;
import com.dream.domain.LoginLog;
import com.dream.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LoginLogServiceImpl implements LoginLogService {
    @Autowired
    private LoginLogDao loginLogDao;
    @Override
    public int add(LoginLog loginLog) {
        loginLogDao.insert(loginLog);
        return 0;
    }

    @Override
    public List<LoginLog> findAll() {
        return null;
    }

    @Override
    public List<LoginLog> findByUid(Long uid) {
        return null;
    }
}
