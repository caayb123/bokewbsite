package com.dream.sercurity.phone;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
      * @Description: 手机号逻辑认证处理器
      * @Param: 方法参数
      * @return: 返回值
      * @Author: cyb
      */
public class PhoneAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String phoneParameter = "telephone";
    public static final String codeParameter = "phone_code";

    @Autowired
    private RedisTemplate redisTemplate;

    protected PhoneAuthenticationFilter( ) {
        super( new AntPathRequestMatcher("/login/phoneLogin") );  //指定手机登录拦截的url
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String phone=this.obtainPhone(request);  //获得手机号
        String code=this.obtainValidateCode(request);//获得验证码
        if(phone == null) {
            phone = "";
        }
        if(code == null) {
            code = "";
        }
        phone=phone.trim();
        String cache_code = (String)redisTemplate.opsForValue().get(phone);  //获取redis中的手机验证码
        if (StringUtils.isBlank(cache_code)){
            throw new PhoneNotFoundException( "手机验证码错误" );  //抛出手机验证码异常
        }else {
            if (!cache_code.equals(code)){
                throw new PhoneNotFoundException( "手机验证码错误" ); //抛出手机验证码错误
            }
        }
        PhoneAuthenticationToken phoneAuthenticationToken = new PhoneAuthenticationToken(phone); //实例化一个手机令牌
       this.setDetails(request,phoneAuthenticationToken); //设置请求信息交由 AuthenticationProvider 进行认证
        return this.getAuthenticationManager().authenticate(phoneAuthenticationToken); //返回认证结果

    }

    protected void setDetails(HttpServletRequest request, PhoneAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


    protected String obtainPhone(HttpServletRequest request) {
        return request.getParameter(phoneParameter);    //拿到手机号码参数
    }
    protected String obtainValidateCode(HttpServletRequest request) {
        return request.getParameter(codeParameter);  //拿到验证码参数
    }

}
