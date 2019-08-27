package com.dream.controller;

import com.dream.domain.User;
import com.dream.domain.UserContent;
import com.dream.service.UserContentService;
import com.dream.service.UserService;
import com.dream.utils.SercurityContextUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/other")
public class OtherPersonalController {
     /**
          * @Description: 其他用户主页模块
          * @Param: 方法参数
          * @return: 返回值
          * @Author: cyb
          */

    @Autowired
    private UserContentService userContentService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list")
    public String otherList(Long oid, Integer pageNum, Model model){
        if (oid==null){
            return "redirect:/index.jsp";
        }
        if (pageNum==null){
            pageNum=1;
        }
        User user = SercurityContextUtils.getCurrentUser();
        if (user!=null){
            //添加用户信息
              model.addAttribute("user",user);
        }
        //添加其他用户信息
        User otheruser = userService.findById(oid);
        if (otheruser==null){
            return "redirect:/index.jsp";
        }
        model.addAttribute("otheruser",otheruser);
        //查询其他用户梦分类
        List<UserContent> categoryByUid = userContentService.findCategoryByUid(oid);
        model.addAttribute("categorys",categoryByUid);
        //查询发布的梦
        UserContent userContent=new UserContent();
        userContent.setuId(oid);
        userContent.setPersonal("0");  //0为非私密
        PageInfo<UserContent> all = userContentService.findAll(userContent, pageNum);
        model.addAttribute("page",all);

        //查询关注和被关注
        Map<String, List<User>> followByFollow = userService.findFollowByFollow(oid);
        List<User> list = followByFollow.get("follow");
        List<User> list1 = followByFollow.get("byfollow");
        model.addAttribute("follow",list);
        model.addAttribute("byfollow",list1);
        return "otherPersonal";
    }
    @RequestMapping(value = "/findByCategory")
    public @ResponseBody Map<String,Object> findByCategory(Long oid,String category,Integer pageNum,Model model){
        Map<String,Object> map=new HashMap<>();
        if (oid==null){
            map.put("pageCate", "fail");
            return map;
        }
        if (pageNum==null){
            pageNum=1;
        }
        UserContent userContent=new UserContent();
        if (StringUtils.isNotBlank(category)&&!category.equals("null")){
            userContent.setCategory(category);
        }
        userContent.setuId(oid);
        userContent.setPersonal("0");
        PageInfo<UserContent> all = userContentService.findAll(userContent, pageNum);
        map.put("pageCate",all);
        User otheruser = userService.findById(oid);
        model.addAttribute("otheruser",otheruser);
        return map;
    }

    @RequestMapping(value = "/checkfollow")
    public @ResponseBody Map<String,Object> checkFollow(Long oid) {
        Map<String,Object> map=new HashMap<>();
        if (oid==null){
            map.put("msg","error");
        }else {
            User user = SercurityContextUtils.getCurrentUser();
            //由于需要及时获取user最新数据，因此不能使用安全框架中的user信息

            if (user!=null){
                user = userService.findByEmail(user.getEmail());
                String[] split = user.getFollow().split(",");

                for (int i=0;i<split.length;i++){
                    if (oid.toString().equals(split[i])){
                        map.put("msg","success");
                        return map;
                    }
                }
            }
        }
        map.put("msg","error");
        return map;
    }
    @RequestMapping(value = "/updatefollow")
    public @ResponseBody Map<String,Object> checkFollow(Long oid,String flag){
        Map<String,Object> map=new HashMap<>();
        User user = SercurityContextUtils.getCurrentUser();

        if (oid==null||StringUtils.isBlank(flag)||user==null){
            map.put("msg","error");
            return map;
        }
        if (user.getId().toString().equals(oid.toString())){
            map.put("msg","no");
            return map;
        }
       userService.updateFollow(oid,user.getId(),flag);

        return map;
    }
}
