package com.dream.service.impl;

import com.dream.dao.UpvoteDao;
import com.dream.domain.Upvote;
import com.dream.service.UpvoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpvoteServiceImpl implements UpvoteService {
    @Autowired
    private UpvoteDao upvoteDao;
    @Override
    public Map<String,String> findByUidAndConId(Upvote upvote, int upvote1) {
        Map<String,String> map=new HashMap<>();
        Upvote upvote2 = upvoteDao.selectOne(upvote);
//        -1为踩模块逻辑
        if (upvote1==-1){
            if (upvote2!=null){
                if ( "1".equals(upvote2.getDownvote())) {//为1说明踩过
              map.put("result", "errord");
               return map;
                }else {
                //不为1则可能之前对该文章点赞,更新文章的踩,并设置时间
                    upvote2.setDownvote("1");
                    upvote2.setUpvoteTime(new Date());
                    update(upvote2);
                    map.put("result","success");
                    return map;
                }
            }else {
              //为空则说明之前没点赞过，本次为踩，新增文章，并设置时间
                upvote.setDownvote("1");
                upvote.setUpvoteTime(new Date());
                add(upvote);
                map.put("result","success");
                return map;
            }
        }else {
            //1为点赞逻辑模块,逻辑同上
            if (upvote2!=null){
                if ("1".equals(upvote2.getUpvote())){
                    map.put("result", "erroru");
                    return map;
                }else {
                    upvote2.setUpvote("1");
                    upvote2.setUpvoteTime(new Date());
                    update(upvote2);
                    map.put("result","success");
                    return map;
                }
            }else {
                upvote.setUpvote("1");
                upvote.setUpvoteTime(new Date());
                add(upvote);
                map.put("result","success");
                return map;
            }
        }

    }

    @Override
    public int add(Upvote upvote) {
        upvoteDao.insert(upvote);
        return 0;
    }


    @Override
    public void update(Upvote upvote) {
         upvoteDao.updateByPrimaryKeySelective(upvote);
    }

    @Override
    public void deleteByCid(Long cid) {
        Upvote upvote=new Upvote();
        upvote.setContentId(cid);
        upvoteDao.delete(upvote);
    }
}
