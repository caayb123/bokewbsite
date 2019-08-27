package com.dream.controller;

import com.dream.domain.RoleUser;
import com.dream.domain.User;
import com.dream.service.RoleUserService;
import com.dream.service.UserService;
import com.dream.utils.MD5Util;
import com.dream.utils.RandStringUtils;
import com.dream.utils.SercurityContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    /**
         * @Description: 登录模块
         * @Param: 方法参数
         * @return: 返回值
         * @Author: cyb
         */
    @Autowired
   private UserService userService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private RoleUserService roleUserService;
    @Autowired
    @Qualifier(value = "jmsQueueTemplate")
    private JmsTemplate jmsTemplate; // mq消息模板.

    @RequestMapping(value = "/doLogin")
    public String doLogin(String code, Model model, HttpServletRequest request,User user,String phone_code,String telephone){

        //手机登录模块
        if (StringUtils.isNotBlank(phone_code)){
            //匹配手机验证码
            String telephone1 = redisTemplate.opsForValue().get(telephone); //从redis中获取验证码
            if (telephone1!=null&&telephone1.equals(phone_code)){
                //验证码合法且匹配正确
                User byPhone = userService.findByPhone(telephone);
                model.addAttribute("user",byPhone);
                //存储用户信息到session中
                request.getSession().setAttribute("user",byPhone);
                return "redirect:/personal/list";
            }else {
                //验证码不正确或过期
                model.addAttribute("error","phone_fail");
                return "forward:/login.jsp";

            }
        }

        //邮箱登录模块
        if (StringUtils.isBlank(code)){
            //非法请求
            model.addAttribute("error","fail");
            return "forward:/login.jsp";
        }
        //匹配验证码正确性
        String vercode_key = (String)request.getSession().getAttribute("VERCODE_KEY");
        if (vercode_key==null){
            //session域的验证码过期
            model.addAttribute("error","fail");
            return "forward:/login.jsp";
        }
        if (!vercode_key.equals(code)){
            //匹配不上session中的验证码
            model.addAttribute("error","fail");
            return "forward:/login.jsp";
        }
        //验证码正确的情况下开始进行数据库匹配登录信息
        //加密密码
//        String encodePassword = MD5Util.encodeToHex("salt" + user.getPassword());
//        User loginUser = userService.login(user.getEmail(), encodePassword);
        User loginUser = userService.findByEmail(user.getEmail());
        if (loginUser==null){
            //用户登录信息不存在
            model.addAttribute("email",user.getEmail());
            model.addAttribute("error","fail");
            return "forward:/login.jsp";
        }else {
            if (loginUser.getState().equals("0")){
                //未激活
                model.addAttribute("email",user.getEmail());
                model.addAttribute("error","active");
                return "forward:/login.jsp";
            }else {
                //登录成功
                model.addAttribute("user",loginUser);
                //存储用户信息到session中
                request.getSession().setAttribute("user",loginUser);
               return "redirect:/personal/list";
            }
        }
    }

       @RequestMapping(value = "sendSms")
    public @ResponseBody Map<String,Object> sendSms(String telephone){
        Map<String,Object> map=new HashMap<>();
        try{
        //获取6位数的验证码
        String code = RandStringUtils.getCode();
        redisTemplate.opsForValue().set(telephone,code,60, TimeUnit.SECONDS);//redis的60s有效期
        //调用ActiveMQ消息模板发送消息给MQ
        jmsTemplate.send("login_msg", new MessageCreator() {
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
    @RequestMapping(value = "/phone")
    public @ResponseBody Map<String,String> checkPhone(String phone){
        Map<String,String> map=new HashMap<>();
        User byPhone = userService.findByPhone(phone);
        if (byPhone!=null&&byPhone.getState().equals("1")) {
            //已注册
            map.put("message","fail");
        }else {
            //未注册
            map.put("message", "success");
        }
        return map;
    }
    @RequestMapping(value = "/out")
    public String loginOut(HttpServletRequest request){
        //移除session中的用户信息
        request.getSession().removeAttribute("user");
        // 注销用户，使session失效,释放资源
        request.getSession().invalidate();
        return "forward:/login.jsp";
    }




}
