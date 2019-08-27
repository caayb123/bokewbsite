package com.dream.controller;

import com.dream.dao.UserContentDao;
import com.dream.domain.Comment;
import com.dream.domain.Upvote;
import com.dream.domain.User;
import com.dream.domain.UserContent;
import com.dream.service.*;
import com.dream.utils.DateUtils;
import com.dream.utils.SercurityContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/page")
public class IndexController {
   /**
        * @Description: 文章主页模块
        * @Param: 方法参数
        * @return: 返回值
        * @Author: cyb
        */
   @Autowired
   private UserContentService userContentService;
     @Autowired
    private UpvoteService upvoteService;
     @Autowired
     private CommentService commentService;
     @Autowired
     private UserService userService;
     @Autowired
     private SolrService solrService;
     @Autowired
     private JmsTemplate jmsTemplate;

    @RequestMapping(value = "/pages")
    public String indexSearch(HttpServletRequest request,Model model,String keyword,Integer pageNum){
        User user = SercurityContextUtils.getCurrentUser();
        if (user!=null){
            model.addAttribute("user",user);
        }
        if (StringUtils.isNotBlank(keyword)){
            //如果该索引不为空则查询调用solr引擎搜索
            PageInfo<UserContent> pages = solrService.findByKeyWords(keyword, pageNum);
            model.addAttribute("keyword",keyword);
            model.addAttribute("page",pages);
        }else {
            //如果索引为空则普通查询分页
            PageInfo<UserContent> all = userContentService.findAll(pageNum);
            model.addAttribute("page",all);

        }
        return "forward:/index.jsp";
    }

    @RequestMapping(value = "/up")
    public @ResponseBody Map<String,Object> upvote(@Param(value = "id") Long contentId, Long uid, int upvote, HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        //先判断用户是否登录没有登陆则让回调函数弹回登录登录界面
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            map.put("data","fail");
            return map;
        }
        Upvote upvote1=new Upvote();
        upvote1.setContentId(contentId); //设置文章id
        upvote1.setuId(uid);//设置用户id
        Map<String, String> byUidAndConId = upvoteService.findByUidAndConId(upvote1, upvote);
        //获取业务层结果
        String result = byUidAndConId.get("result");
        if (result.equals("errord")){
            //错误d说明已经踩
            map.put("data","down");
            return map;
        }
        if (result.equals("erroru")){
            //说明已经赞过
            map.put("data","done");
            return map;
        }
         //说明成功，更新文章点赞或者踩数目
            if (upvote==-1){
                //根据文章id查询文章
                UserContent byId = userContentService.findById(contentId);
                byId.setDownvote(byId.getDownvote()+upvote);
                userContentService.updateById(byId);
            }else {
                UserContent byId = userContentService.findById(contentId);
                byId.setUpvote(byId.getUpvote()+upvote);
                userContentService.updateById(byId);
            }
            map.put("data","success");
            return map;


    }
     @RequestMapping(value = "reply")
    public @ResponseBody Map<String,Object> reply(Long content_id){
        Map<String,Object> map=new HashMap<>();
        //查询出该文章的所有评论
         List<Comment> all = commentService.findByCid(content_id);
         map.put("list",all);
         return map;
     }

     @RequestMapping(value = "comment")
     public @ResponseBody Map<String,Object> comment(Long content_id,Long uid,String oSize,String comment_time,HttpServletRequest request,Long id,Integer upvote,Long bid){
         Map<String,Object> map=new HashMap<>();
         User user = SercurityContextUtils.getCurrentUser();
         if (user==null){
             map.put("data","fail");
             return map;
         }
         if (id==null){
       //评论id为空说明是评论
             //格式化日期
             Date date= DateUtils.StringToDate(comment_time,"yyyy-MM-dd HH:mm:ss" );
             Comment comment=new Comment();
             comment.setCommTime(date);  //设置评论日期
             comment.setConId(content_id); //设置文章id
             comment.setComContent(oSize); //设置评论内容
             comment.setComId(uid);//设置评论者id
             if (upvote==null){
                 upvote=0;
             }
             comment.setUpvote(upvote);
             comment.setById(bid); //设置被评论者id(一级评论下该bid为null)
             User user1 = userService.findById(uid);
             comment.setUser(user1);  //设置评论者信息
             commentService.add(comment); //添加评论
             map.put("data",comment);
             //将文章表中的评论数+1
             UserContent userContent = userContentService.findById(content_id);
             Integer commentNum = userContent.getCommentNum();
             userContent.setCommentNum(commentNum+1);
             userContentService.updateById(userContent);
         }else {
//             评论id不为空说明是点赞
             Comment comment = commentService.findById(id);
              comment.setUpvote(upvote);
              commentService.update(comment);
         }
         return map;
     }
     @RequestMapping(value = "/deleteComment")
     public @ResponseBody Map<String,Object> deleteComment(Long id,Long uid,Long con_id,Long fid,HttpServletRequest request){
         Map<String,Object> map=new HashMap<>();
         User user = SercurityContextUtils.getCurrentUser();
         if (user==null){
             map.put("data","fail");
             return map;
         }
         if (user.getId().toString().equals(uid.toString())){
             //删除方是文章作者本人
             int deleteNum = commentService.delete(id, uid, con_id, fid);  //获取删除总记录数
             //更新文章表评论数
             UserContent userContent = userContentService.findById(con_id);
             int newNum = userContent.getCommentNum() - deleteNum;
             userContent.setCommentNum(newNum);
             userContentService.updateById(userContent);
            map.put("data",newNum);  //返回删除后计算新更新的评论数
         }else {
             //删除方不是文章作者本人
             map.put("data","no-access");

         }

         return map;
     }

     @RequestMapping(value = "/commentChild")
    public @ResponseBody Map<String,Object> commentChild(Long content_id,Long uid,String oSize,String comment_time,Long by_id,Long fid,Integer upvote,HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
         User user = SercurityContextUtils.getCurrentUser();
         if (user==null){
             map.put("data","fail");
             return map;
         }
         //格式化日期
         Date date = DateUtils.StringToDate( comment_time, "yyyy-MM-dd HH:mm:ss" );
         Comment comment=new Comment();
         comment.setCommTime(date);
         comment.setComContent(oSize);
         comment.setById(by_id);
         comment.setComId(uid);
         comment.setConId(content_id);
         if (upvote==null){
             upvote=0;
         }
         comment.setUpvote(upvote);
         User byId = userService.findById(uid);
         comment.setUser(byId);
         commentService.add(comment);
         //查询一级评论，判断是否有子评论
         Comment firstComment = commentService.findById(fid);
         if (firstComment.getChildren()!=null){
          //有则说明已经有子评论了，以逗号形式隔开
             firstComment.setChildren(firstComment.getChildren()+","+comment.getId().toString());
         }else {
             //没有则直接加入到一级评论的子评论ids中
             firstComment.setChildren(comment.getId().toString()); //利用插入后返回自增主键直接插入到一级评论中
         }
         //更新评论
         commentService.update(firstComment);
         map.put("data",comment);
         //调用mq发送通知告知被评论者
         jmsTemplate.send("push_msg", new MessageCreator() {
             @Override
             public Message createMessage(Session session) throws JMSException {
                 MapMessage mapMessage = session.createMapMessage();
                 mapMessage.setString("uid",uid.toString());
                 mapMessage.setString("rid",by_id.toString());
                 mapMessage.setString("cid",content_id.toString());
                 mapMessage.setString("type","回复");
                 return mapMessage;
             }
         });
         //根据文章id查询出评论并且修改评论数
         UserContent userContent = userContentService.findById(content_id);
         userContent.setCommentNum(userContent.getCommentNum()+1); //评论数加1
         userContentService.updateById(userContent); //更新文章表
         return map;
     }


}
