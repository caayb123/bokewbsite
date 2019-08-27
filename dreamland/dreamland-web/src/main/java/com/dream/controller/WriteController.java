package com.dream.controller;


import com.dream.domain.User;
import com.dream.domain.UserContent;
import com.dream.service.*;
import com.dream.utils.SercurityContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Controller
@RequestMapping(value = "/jump")
public class WriteController {
    @Autowired
  private UserContentService userContentService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UpvoteService upvoteService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SolrService solrService;
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping(value = "/write")
    public String jumpPage(HttpServletRequest request, Model model){
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
         return "forward:/login.jsp";
        }
        model.addAttribute("user",user);
        return "writedream";
    }

    @RequestMapping(value = "/doWriteDream")
    public String doWriteDream(Long cid, String category, String txtT_itle, String content, String private_dream, HttpServletRequest request,Model model){
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            return "forward:/login.jsp";
        }

            UserContent userContent = new UserContent();
        if (cid!=null){
            userContent = userContentService.findById(cid);
        }
            userContent.setCategory(category);
            userContent.setContent(content);
            userContent.setRptTime(new Date());
            userContent.setTitle(txtT_itle);
            if ("on".equals(private_dream)) {   //on代表按钮打开
                userContent.setPersonal("1");
            } else {
                userContent.setPersonal("0");
            }
            userContent.setuId(user.getId());
            if (cid==null) {
                userContent.setUpvote(0);
                userContent.setDownvote(0);
                userContent.setCommentNum(0);
                //添加
                userContentService.addContent(userContent);
                //添加完成后也要更新solr的索引库
                solrService.addUserContent(userContent);
            }else {
                //更新
                userContentService.updateById(userContent);
                //更新完成后也要更新solr的索引库
                solrService.updateUserContent(userContent);
            }

            model.addAttribute("content", userContent);

        return "writesuccess";
    }
    @RequestMapping(value = "/toWrite")
    public String toWrite(HttpServletRequest request){
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            return "forward:/login.jsp";
        }
        return "writedream";
    }
    @RequestMapping(value = "/watch")
    public String watch(Long id,Long cid,Model model){
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            return "forward:/login.jsp";
        }
        model.addAttribute("user",user);
        UserContent byId = userContentService.findById(cid);
        if (byId.getPersonal().equals("1")){
            if (byId.getuId().equals(user.getId())){
                model.addAttribute("cont", byId);
                return "watch";
            }else {
                return "forward:/login.jsp";
            }
        }else {
            model.addAttribute("cont", byId);
        }
        if (id!=null){
            int i = messageService.updateById(id);
            if (i==0){
                    String count = (String)redisTemplate.opsForValue().get(user.getId().toString());  //获取信息
                    if (count!=null){
                        //去count的值转换未int并减1
                        Integer num = Integer.parseInt(count);
                        num=num-1;
                        if (num<=0){
                            redisTemplate.delete(user.getId().toString());  //小于等于0说明已经没有未读消息了移除redis中的键
                        }else {
                            redisTemplate.opsForValue().set(user.getId().toString(),num.toString()); //将减1后的值写入redis
                        }
                    }
            }
        }
        return "watch";
    }
    @RequestMapping(value = "/writedream")
    public String writedream(HttpServletRequest request,Long cid,Model model){
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            return "forward:/login.jsp";
        }
        if (cid!=null){
            UserContent byId = userContentService.findById(cid);
            model.addAttribute("cont",byId);
        }
        model.addAttribute("user",user);
        return "writedream";
    }
    @RequestMapping(value = "deleteContent")
    public String deleteContent(HttpServletRequest request,Long cid){
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            return "forward:/login.jsp";
        }
        if (cid!=null){
            //删除消息
            messageService.deleteByCid(cid);  //影响的记录数
            //删除评论
            commentService.deleteByCid(cid);
            //删除点赞
            upvoteService.deleteByCid(cid);
            //删除文章
            userContentService.deleteById(cid);
            //删除solr索引库
            solrService.deleteById(cid);
        }
        return "redirect:/personal/list?manage=manage";
    }
}
