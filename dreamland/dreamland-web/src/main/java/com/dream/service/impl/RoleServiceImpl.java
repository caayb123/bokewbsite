package com.dream.service.impl;

import com.dream.dao.RoleDao;
import com.dream.domain.Role;
import com.dream.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public Role findById(long id) {
        return null;
    }

    @Override
    public int add(Role role) {
        return 0;
    }

    @Override
    public List<Role> findByUid(Long uid) {
        return roleDao.findByUid(uid);
    }
}
