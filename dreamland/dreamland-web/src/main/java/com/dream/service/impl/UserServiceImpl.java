package com.dream.service.impl;

import com.dream.dao.UserDao;
import com.dream.domain.User;
import com.dream.service.UserService;
import com.dream.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public int regist(User user) {
        return userDao.insert(user);
    }

    @Override
    public User login(String email, String password) {
        User user=new User();
        user.setEmail(email);
        user.setPassword(password);
        return  userDao.selectOne(user);

    }

    @Override
    public User findByEmail(String email) {
        User user =new User();
        user.setEmail(email);
       return userDao.selectOne(user);

    }

    @Override
    public User findByPhone(String phone) {
        User user =new User();
        user.setPhone(phone);
        return userDao.selectOne(user);
    }

    @Override
    public User findById(Long id) {
        User user =new User();
        user.setId(id);
       return userDao.selectOne(user);
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {
             User user=new User();
             user.setEmail(email);
             userDao.delete(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);

}

    @Override
    public List<User> findAll() {
        return userDao.selectAll();
    }

    @Override
    public void updateFollow(Long oid,Long uid,String flag) {
        User otherUser = findById(oid);  //查出被关注的用户的关注信息
        User user = findById(uid);  //查出关注的用户的关注信息
        if (flag.equals("add")){
            //添加关注
            if (StringUtils.isBlank(otherUser.getByfollow())){
                //为空说明没有关注直接添加
                otherUser.setByfollow(uid.toString());
                update(otherUser);
            }else {
                //不为空说明存在则继续添加
                otherUser.setByfollow(otherUser.getByfollow()+","+uid.toString());
                update(otherUser);
            }
            //给关关注用户增加关注信息
            if (StringUtils.isBlank(user.getFollow())){
                //为空说明没有关注直接添加
                user.setFollow(oid.toString());
                update(user);
            }else {
                //不为空说明存在则继续添加
                user.setFollow(user.getFollow()+","+oid.toString());
                update(user);
            }
        }else {
            //取消关注
            String ostr = StringUtil.getString(otherUser.getByfollow(), uid);  //工具类切割字符串
            otherUser.setByfollow(ostr);
            update(otherUser);   //更新被关注用户
            String str = StringUtil.getString(user.getFollow(), oid);
            user.setFollow(str);
            update(user);  //更新关注用户
        }
    }

    @Override
    public Map<String,List<User>> findFollowByFollow(Long id) {
        User user = findById(id);
        String follow = user.getFollow();
        String byfollow = user.getByfollow();
        Map<String,List<User>> map=new HashMap<>();
        if (!StringUtils.isBlank(follow)){
            String[] split = follow.split(",");
            List<User> list=new ArrayList<>();
            for (int i=0;i<split.length;i++){
                list.add(findById(Long.parseLong(split[i])));
            }
            map.put("follow",list);
        }
        if (!StringUtils.isBlank(byfollow)){
            String[] split = byfollow.split(",");
            List<User> list2=new ArrayList<>();
            for (int i=0;i<split.length;i++){
                list2.add(findById(Long.parseLong(split[i])));
            }
            map.put("byfollow",list2);
        }
        return map;
    }
}
