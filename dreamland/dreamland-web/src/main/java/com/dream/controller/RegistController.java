package com.dream.controller;

import com.dream.domain.RoleUser;
import com.dream.domain.User;
import com.dream.mail.SendEmail;
import com.dream.service.RoleUserService;
import com.dream.service.UserService;
import com.dream.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "/check")
public class RegistController {
    /**
         * @Description: 注册模块
         * @Param: 方法参数
         * @return: 返回值
         * @Author: cyb
         */
    @Autowired
  private UserService userService;
    //Redise数据库操作模板
    @Autowired
    private RedisTemplate <String,String> redisTemplate;
    @Autowired
    private RoleUserService roleUserService;
    private final Long CONSTANT_ROLEUSER=1L;
    @RequestMapping(value = "/phone")
    public @ResponseBody Map<String,String> checkPhone(String phone){
        Map<String,String> map=new HashMap<>();
        User byPhone = userService.findByPhone(phone);
        if (byPhone==null) {
            //未注册
            map.put("message", "success");
        }else {
            //已注册
            map.put("message","fail");
        }
        return map;
    }
    @RequestMapping(value = "/email")
    public @ResponseBody Map<String,String> checkEmail(String email){
        Map<String,String> map=new HashMap<>();
        User byEmail = userService.findByEmail(email);
        if (byEmail==null) {
            //未注册
            map.put("message", "success");
        }else {
            //已注册
            map.put("message","fail");
        }
        return map;
    }

    @RequestMapping(value = "/code")
    public @ResponseBody Map<String,String> checkCode(String code, HttpServletRequest request){
        Map<String,String> map=new HashMap<>();
        //获取session域中的验证码
        String vercode_key = (String) request.getSession().getAttribute("VERCODE_KEY");
        //匹配验证码
        if (code.equals(vercode_key)){
            //验证码正确
            map.put("message", "success");
        }else {
            //验证码有误
            map.put("message","fail");
        }
        return map;
    }

    @RequestMapping(value = "/regist")
   public String doRegist(@ModelAttribute User user,String code, Model model,HttpServletRequest request){
        System.out.println(user.getEmail());
        //判断验证码是否为空或者空串
        if (StringUtils.isBlank(code)){
            model.addAttribute("error","非法注册,请重新注册");
            return "forward:/register.jsp";
        }
     //检测session域中的验证码
        String code1 = (String)request.getSession().getAttribute("VERCODE_KEY");
        if (code1==null){
            //验证码为空
            model.addAttribute("error","验证码超时,请重新注册");
            return "forward:/register.jsp";
        }
        if (!code1.equals(code)){
            //验证码不等
            model.addAttribute("error","验证码不正确请重新输入");
            return "forward:/register.jsp";
        }
        User byEmail = userService.findByEmail(user.getEmail());
        if (byEmail!=null){ //不为空说明已经被注册过了
             model.addAttribute("error", "该用户已被注册");
            return "forward:/register.jsp";
        }else {
            user.setPassword(new Md5PasswordEncoder().encodePassword(user.getPassword(),user.getEmail())); //加密用户密码
            user.setImgUrl("/images/icon_m.jpg");  //设置默认头像
            user.setState("0");
            user.setEnable("0");
//            设置邮件激活码
            String validateCode = MD5Util.encodeToHex("salt" + user.getEmail() + user.getPassword());
            redisTemplate.opsForValue().set(user.getEmail(),validateCode,24, TimeUnit.HOURS);//设置激活码的24小时有效期，到期自动从redis中删除
            userService.regist(user);
            SendEmail.sendEmailMessage(user.getEmail(),validateCode);      //发送注册邮件
            String message = user.getEmail() + "," + validateCode;
            model.addAttribute("message",message);  //将邮箱和注册码放到request域中
            return "registerSuccess";

        }

    }
    @RequestMapping(value = "/activecode")
    public String doActive(String email,String validateCode,Model model){
            //激活验证
        //判断激活是否过期
        User byEmail = userService.findByEmail(email);
        String code = redisTemplate.opsForValue().get(email);
        if (code!=null&&byEmail.getState().equals("1")){
            //已经激活过
            model.addAttribute("success","您已激活过，请直接登录");
                 return "forward:/login.jsp";
        }
        if (code==null){
            //激活码过期
            model.addAttribute("fail","您的激活码已过期请重新注册");
            return "forward:/register.jsp";
        }
        if (StringUtils.isNotBlank(validateCode)&&code.equals(validateCode)){
                        //激活码正确
            byEmail.setState("1");
            byEmail.setEnable("1");
            userService.update(byEmail);
            //给用户分配角色
            RoleUser roleUser=new RoleUser();
            roleUser.setrId(CONSTANT_ROLEUSER);
            roleUser.setuId(byEmail.getId());
            roleUserService.add(roleUser);
            model.addAttribute("email",byEmail.getEmail());
            return "activeSuccess";
        }else {
            //激活码错误
            model.addAttribute("fail","您的激活码错误，请重新激活！");
            return "activeFail";
        }
    }

    @RequestMapping(value = "/resend")
    public @ResponseBody Map<String,String> resend(String email,String validateCode){
        SendEmail.sendEmailMessage(email,validateCode);      //发送注册邮件
        Map<String,String> map=new HashMap<>();
        map.put("message","success");
        return map;

    }
 



}
