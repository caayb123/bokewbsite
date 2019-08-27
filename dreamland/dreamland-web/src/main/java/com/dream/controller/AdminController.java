package com.dream.controller;

import com.dream.domain.RoleUser;
import com.dream.domain.Upvote;
import com.dream.domain.User;
import com.dream.domain.UserContent;
import com.dream.service.*;
import com.dream.utils.SercurityContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.ibatis.ognl.DynamicSubscript.all;

/**
      * @Description: 后台管理控制层
      * @Param: 方法参数
      * @return: 返回值
      * @Author: cyb
      */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleUserService roleUserService;
    @Autowired
    private UserContentService userContentService;
    @Autowired
    private UpvoteService upvoteService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private SolrService solrService;
    @RequestMapping(value = "/admin")
    public @ResponseBody
    Map<String,Object> admin(String mima){
        Map<String,Object> map=new HashMap<>();
        if (StringUtils.isBlank(mima)){
            map.put("msg","fail");
            return map;
        }
        if ("124578963".equals(mima)){
            List<User> all = userService.findAll();
            map.put("msg","success");
            map.put("list",all);
        }else {
            map.put("msg","fail");
        }
        return map;
    }
    @RequestMapping(value = "/jumpAdmin")
    public String jumpAdmin(Model model){
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            return "redirect:/login.jsp";
        }
        model.addAttribute("user",user);
        return "adminManager";
    }

    @RequestMapping(value = "/remo")
    public @ResponseBody Map<String,Object> remo(String mima,Long id) {
        Map<String,Object> map=new HashMap<>();
        if (StringUtils.isBlank(mima)||id==null){
            map.put("msg","fail");
            return map;
        }
        if (id.toString().equals("19")){  //超级用户内置无法封号
            map.put("msg","fail");
            return map;
        }
        if ("124578963".equals(mima)) {
            roleUserService.deleteByUid(id);
            map.put("msg","success");
        }else {
            map.put("msg","fail");
        }
        return map;
    }
    @RequestMapping(value = "/addmo")
    public @ResponseBody Map<String,Object> addmo(String mima,Long id) {
        Map<String,Object> map=new HashMap<>();
        if (StringUtils.isBlank(mima)){
            map.put("msg","fail");
            return map;
        }
        if ("124578963".equals(mima)) {
            RoleUser roleUser=new RoleUser();
            roleUser.setuId(id);
            roleUser.setrId(1L);
            roleUserService.add(roleUser);
            map.put("msg","success");
        }else {
            map.put("msg","fail");
        }
        return map;
    }
    @RequestMapping(value = "/adminCont")
    public @ResponseBody Map<String,Object> addmo(String mima) {
        Map<String,Object> map=new HashMap<>();
        if (StringUtils.isBlank(mima)){
            map.put("msg","fail");
            return map;
        }
        if ("124578963".equals(mima)){
            List<UserContent> all = userContentService.findAll();
            map.put("msg","success");
            map.put("list",all);
        }else {
            map.put("msg","fail");
        }
        return map;
    }
    @RequestMapping(value = "/remoCont")
    public @ResponseBody Map<String,Object> remoCont(String mima,Long id) {
        Map<String,Object> map=new HashMap<>();
        if (StringUtils.isBlank(mima)||id==null){
            map.put("msg","fail");
            return map;
        }
        if ("124578963".equals(mima)) {
            //删除评论
            commentService.deleteByCid(id);
            //删除点赞
            upvoteService.deleteByCid(id);
            //删除文章
            userContentService.deleteById(id);
            //删除solr索引库
            solrService.deleteById(id);
            map.put("msg","success");
        }else {
            map.put("msg","fail");
        }
        return map;
    }
}
