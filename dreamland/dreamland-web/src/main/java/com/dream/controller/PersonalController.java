package com.dream.controller;

import com.dream.domain.User;
import com.dream.domain.UserContent;
import com.dream.domain.UserInfo;
import com.dream.service.MessageService;
import com.dream.service.UserContentService;
import com.dream.service.UserInfoService;
import com.dream.service.UserService;
import com.dream.utils.DateUtils;
import com.dream.utils.MD5Util;
import com.dream.utils.RandStringUtils;
import com.dream.utils.SercurityContextUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.nio.cs.US_ASCII;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "/personal")
public class PersonalController {
  /**
       * @Description: 个人页面模块
       * @Param: 方法参数
       * @return: 返回值
       * @Author: cyb
       */
  @Autowired
  private UserContentService userContentService;
  @Autowired
  private UserInfoService userInfoService;
  @Autowired
  private UserService userService;
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  private JmsTemplate jmsTemplate;
  @Autowired
  private MessageService messageService;
    @RequestMapping(value = "/list")
    public String groupList(HttpServletRequest request, Integer pageNum, Model model,String manage){
        if (pageNum==null){
            pageNum=1;
        }
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            return "forward:/login.jsp";
        }
        model.addAttribute("user",user);
        //查询梦分类
        List<UserContent> categoryByUid = userContentService.findCategoryByUid(user.getId());
        model.addAttribute("categorys",categoryByUid);
        //查询发布的梦
         UserContent userContent=new UserContent();
         userContent.setuId(user.getId());
         userContent.setPersonal("0");  //0为非私密
        PageInfo<UserContent> all = userContentService.findAll(userContent, pageNum);
        model.addAttribute("page",all);

       //查询私密的梦
        userContent.setPersonal("1");  //1为私密
        PageInfo<UserContent> all2 = userContentService.findAll(userContent, pageNum);
        model.addAttribute("page2",all2);

        //查询热梦,根据点赞数倒序排列
        userContent.setPersonal("0");
        PageInfo<UserContent> hotDream = userContentService.findHotDream(pageNum);
        model.addAttribute("hotPage",hotDream);

        if (!StringUtils.isBlank(manage)){
            model.addAttribute("manage",manage);
        }
        //查询关注和被关注
        Map<String, List<User>> followByFollow = userService.findFollowByFollow(user.getId());
        List<User> list = followByFollow.get("follow");
        List<User> list1 = followByFollow.get("byfollow");
        model.addAttribute("follow",list);
        model.addAttribute("byfollow",list1);
        return "personal";
    }

    @RequestMapping(value = "/findByCategory")
    public @ResponseBody Map<String,Object> findByCategory(String category,Integer pageNum,HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            map.put("pageCate", "fail");
            return map;
        }
        if (pageNum==null){
            pageNum=1;
        }
        UserContent userContent= new UserContent();

        if (StringUtils.isNotBlank(category)&&!category.equals("null")){
            userContent.setCategory(category);
        }
        userContent.setuId(user.getId());
        userContent.setPersonal("0");
        PageInfo<UserContent> all = userContentService.findAll(userContent, pageNum);
        map.put("pageCate",all);

        return map;
    }
    @RequestMapping(value = "/findPersonal")
    public @ResponseBody Map<String,Object> findPersonal(Integer pageNum,HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        User user = SercurityContextUtils.getCurrentUser();
        if(user==null){
            map.put("page2","fail");
            return map;
        }
        if (pageNum==null){
            pageNum=1;
        }
        UserContent userContent=new UserContent();
        userContent.setPersonal("1");
        userContent.setuId(user.getId());
        PageInfo<UserContent> all = userContentService.findAll(userContent, pageNum);
        map.put("page2",all);
        return map;
    }


    @RequestMapping(value = "/findAllHotContents")
    public @ResponseBody Map<String,Object> findAllHotContents(Integer pageNum,HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        User user = SercurityContextUtils.getCurrentUser();
        if(user==null){
            map.put("hotPage","fail");
            return map;
        }
        if (pageNum==null){
            pageNum=1;
        }
        PageInfo<UserContent> hotDream = userContentService.findHotDream(pageNum);
        map.put("hotPage",hotDream);
        return map;
    }

    @RequestMapping(value = "/profile")
    public String profile(HttpServletRequest request,Model model){
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            return "forward:/login.jsp";
        }

            UserInfo userInfo = userInfoService.findByUid(user.getId());
            model.addAttribute("user",user);
            model.addAttribute("userInfo",userInfo);
           return "profile";
    }
    @RequestMapping(value = "/saveImage")
    public @ResponseBody Map<String,Object> saveImage(HttpServletRequest request,String url){
        Map<String,Object> map=new HashMap<>();
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            map.put("msg","登录过期");
            return map;
        }
       user.setImgUrl(url);
        userService.update(user);
        map.put("msg","头像保存成功");
        return map;
    }
    @RequestMapping(value = "/saveUserInfo")
    public String saveUserInfo(HttpServletRequest request,UserInfo userInfo,String nickName,Model model,String birthdayy){
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            return "forward:/login.jsp";
        }
        UserInfo u=new UserInfo();
        u.setBirthday(DateUtils.StringToDate(birthdayy,"yyyy-MM-dd"));
        u.setuId(user.getId());
        u.setSex(userInfo.getSex());
        u.setName(userInfo.getName());
        u.setAddress(userInfo.getAddress());
        UserInfo userInfo1 = userInfoService.findByUid(user.getId());
        if (userInfo1==null){
            //为空说明不存在需要新增
            userInfoService.add(u);
        }else {
            u.setId(userInfo1.getId());
            userInfoService.update(u);
        }

        user.setNickName(nickName);
        userService.update(user);

        model.addAttribute("user",user);
        model.addAttribute("userInfo",u);
        return "profile";
    }
    @RequestMapping(value = "/repassword")
    public String repassword(HttpServletRequest request,Model model){
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            return "forward:/login.jsp";
        }else {
            model.addAttribute("user",user);
            return "repassword";
        }
    }
    @RequestMapping(value = "/updatePassword")
    public String updatePassword(HttpServletRequest request,Model model,String old_password,String password){
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null||StringUtils.isBlank(old_password)||StringUtils.isBlank(password)){
            return "forward:/login.jsp";
        }
        String oldPassword =new Md5PasswordEncoder().encodePassword(old_password,user.getEmail());
        if (oldPassword.equals(user.getPassword())){
            user.setPassword(new Md5PasswordEncoder().encodePassword(password,user.getEmail()));
            userService.update(user);
            model.addAttribute("message", "success");
        }else {
            model.addAttribute("message", "fail");
        }
        model.addAttribute("user",user);
        return "passwordSuccess";
    }
    @RequestMapping(value = "sendSms")
    public @ResponseBody Map<String,Object> sendSms(String telephone){
        Map<String,Object> map=new HashMap<>();
        try{
            //获取6位数的验证码
            String code = RandStringUtils.getCode();

            redisTemplate.opsForValue().set(telephone,code,60, TimeUnit.SECONDS);//redis的60s有效期
            //调用ActiveMQ消息模板发送消息给MQ
            jmsTemplate.send("recellphone_msg", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    MapMessage mapMessage = session.createMapMessage();
                    mapMessage.setString("telephone",telephone);
                    mapMessage.setString("code",code);
                    return mapMessage;
                }
            });
        }catch (Exception e){
            map.put("msg",false);
            return map;
        }
        map.put("msg",true);
        return map;
    }

    @RequestMapping(value = "/cell")
    public String cell(HttpServletRequest request,Model model){
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            return "forward:/login.jsp";
        }else {
            model.addAttribute("user",user);
            return "recellphone";
        }
    }

    @RequestMapping(value = "/updateCellPhone")
    public String updateCellPhone(HttpServletRequest request,Model model,String phone,String code){
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            return "forward:/login.jsp";
        }
        if (StringUtils.isBlank(code)||StringUtils.isBlank(phone)){
            model.addAttribute("msg","fail");
            return "cellphoneSuccess";
        }

        String phoneCode = (String) redisTemplate.opsForValue().get(phone);
        if (phoneCode!=null&&phoneCode.equals(code)){
            user.setPhone(phone);
            userService.update(user);
            model.addAttribute("msg","success");
        }
        model.addAttribute("user",user);
        return "cellphoneSuccess";
    }
    @RequestMapping(value = "/msg")
    public @ResponseBody Map<String,Object> msg(){
        Map<String,Object> map=new HashMap<>();
        User user = SercurityContextUtils.getCurrentUser();
        if (user!=null) {
            String conut = (String)redisTemplate.opsForValue().get(user.getId().toString());//获取该用户的消息情况
            if (conut!=null){
                map.put("num",conut); //有未读消息
            }else {
                map.put("num","no"); //没有未读消息
            }
        }
        return map;
    }
    @RequestMapping(value = "/msgList")
    public String jumpMsg(Model model,Integer pageNum){
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            return "redirect:/login.jsp";
        }
        PageInfo<com.dream.domain.Message> all = messageService.findAll(user.getId(), null, pageNum);
         model.addAttribute("page",all);
        List<com.dream.domain.Message> list = messageService.findAll(user.getId(), "0");
        if (list!=null&&list.size()>0){   //考虑到文章被删除后消息跟着删除，只能通过此方式消除redis中的值
            redisTemplate.opsForValue().set(user.getId().toString(),String.valueOf(list.size()));
        }else {
            redisTemplate.delete(user.getId().toString());
        }
        return "message";
    }

    @RequestMapping(value = "/allRead")
    public @ResponseBody Map<String,Object> allRead(){
        Map<String,Object> map=new HashMap<>();
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            map.put("msg","fail");
            return map;
        }
        redisTemplate.delete(user.getId().toString());  //清空redis中的未读消息
        messageService.updateAll(user.getId());
         map.put("msg","success");
        return map;
    }
    @RequestMapping(value = "/allClean")
    public @ResponseBody Map<String,Object> allClean(){
        Map<String,Object> map=new HashMap<>();
        User user = SercurityContextUtils.getCurrentUser();
        if (user==null){
            map.put("mg","error");
            return map;
        }

        // 先查询是否有未读的消息
        List<com.dream.domain.Message> all =messageService.findAll(user.getId());
        for (com.dream.domain.Message msg:
                all ) {
            if (msg.getSee().equals("0")){
               map.put("mg","fail");   //有未读消息就不让删除直接返回
               return map;
            }
        }
        messageService.deleteAll(user.getId());
         map.put("mg","success");
        return map;
    }

}
