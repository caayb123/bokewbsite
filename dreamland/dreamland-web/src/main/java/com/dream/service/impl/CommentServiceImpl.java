package com.dream.service.impl;

import com.dream.dao.CommentDao;
import com.dream.dao.UserDao;
import com.dream.domain.Comment;
import com.dream.domain.User;
import com.dream.service.CommentService;
import com.dream.service.UserService;
import com.dream.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserService userService;

    @Override
    public int add(Comment comment) {
        int i = commentDao.insertComment(comment);
        return i;
    }

    @Override
    public void update(Comment comment) {
      commentDao.updateByPrimaryKeySelective(comment);
    }

    @Override
    public List<Comment> findByCid(Long content_id) {
          //根据文章ID查询出所有一级评论
        List<Comment> all= commentDao.findAllFirstComment(content_id);
        if (all!=null&&all.size()>0) {
            //遍历所有一级评论
            for (Comment c1 : all) {
                //根据一级评论遍历二级评论
                if (!StringUtils.isBlank(c1.getChildren())) {
                    List<Comment> allChildrenComment = commentDao.findAllChildrenComment(content_id, c1.getChildren());
                    if (allChildrenComment != null && allChildrenComment.size() > 0) {
                        for (Comment c2 : allChildrenComment) {
                            if (c2.getById() != null) {
                                User byId = userService.findById(c2.getById());
                                //注入评论者信息到二级评论中
                                c2.setByUser(byId);
                            }
                        }

                    }
                    //将所有二级评论注入到一级评论中
                    if (allChildrenComment != null && allChildrenComment.size() > 0) {
                        c1.setComList(allChildrenComment);
                    }
                }
            }

        }
        return all;

    }

    @Override
    public Comment findById(Long id) {
        return commentDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Comment> findAllFirstComment(Long content_id) {
        return null;
    }

    @Override
    public List<Comment> findAllChildrenComment(Long content_id, String children) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
         commentDao.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteChildrenComment(String children) {

    }

    @Override
    public int delete(Long id,Long uid,Long con_id,Long fid) {
        int num=0;
        //根据评论id查询出评论
        Comment commentById = findById(id);
        //判断该评论是否有子评论
        if (!StringUtils.isBlank(commentById.getChildren())){
            //有子评论的话说明该评论是一级评论并带有二级评论，获取并清空该评论的所有子评论信息，并删除掉该评论
            String[] split = commentById.getChildren().split(",");
            for (int i=0;i<split.length;i++){
                deleteById(Long.parseLong(split[i]));
            }
            deleteById(id);
            num=split.length+1;
        }else {
            //没有子评论，则判断fid参数是否为空，有则将其从父评论中移除,没有父评论的话也直接删除
                    if (fid==null){
                        //父评论id为空,他是一个没有子评论的一级评论
                        //直接删除
                        deleteById(id);
                        num=num+1;
                    }else {
                        Comment byId = findById(fid); //查询父评论
                        //父评论id不为空，他是一个二级评论需要移除该子评论在父评论的信息后删除
                        String str = StringUtil.getString(byId.getChildren(), id); //工具类抽取蒋传入的字符串切割并且重新拼接去除掉参数id的字符串返回
                        byId.setChildren(str);
                        //更新父评论
                        update(byId);
                        //删除该评论
                        deleteById(id);
                        num=num+1;
                    }
        }

        return num;
    }

    @Override
    public void deleteByCid(Long cid) {
        Comment comment=new Comment();
        comment.setConId(cid);
        commentDao.delete(comment);
    }
}
