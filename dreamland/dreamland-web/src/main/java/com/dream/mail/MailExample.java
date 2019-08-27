package com.dream.mail;
 /**
      * @Description: 邮件服务测试
      * @Param:
      * @return: 返回值
      * @Author: cyb
      */

public class MailExample {

    public static void main (String args[]) throws Exception {
        String email = "784503325@qq.com";
        String validateCode = "22323";
        SendEmail.sendEmailMessage(email,validateCode);

    }
}
