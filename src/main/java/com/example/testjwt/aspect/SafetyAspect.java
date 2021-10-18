package com.example.testjwt.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.testjwt.config.TokenCheckAnnotation;
import com.example.testjwt.exception.AuthenticateException;
import com.example.testjwt.utils.JwtTokenUtils;
import com.example.testjwt.utils.response.ResponseServer;
import com.example.testjwt.utils.response.ServerEnum;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.media.sound.SoftTuning;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.apache.commons.lang3.StringUtils;

import javax.print.attribute.standard.JobSheets;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.SchemaOutputResolver;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

/**
 * description: 切面
 *
 * @author zwq
 * @date 2021/5/15 15:59
 */
@Aspect
@Component
public class SafetyAspect {
    /**
     * Pointcut 切入点
     * 匹配包下面的所有方法
     */
    @Pointcut(value = "execution(public * com.example.testjwt.controller.*.*(..))&& @annotation(tokenCheckAnnotation)")
    public void safetyAspect(TokenCheckAnnotation tokenCheckAnnotation) {
    }

    /**
     * 环绕通知
     */
    @Around(value = "safetyAspect(tokenCheckAnnotation)")
    public Object around(ProceedingJoinPoint pjp, TokenCheckAnnotation tokenCheckAnnotation) {
        System.out.println("截获");
        //验证信息，保证接口安全
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        Object obj = null;

        //request对象
        HttpServletRequest request = attributes.getRequest();

        //方法的形参参数
        Object[] args = pjp.getArgs();

        System.out.println("长度:"+args.length);

        if(args.length!=1||!(JSONObject.class.isInstance(JSONObject.toJSON(args[0])))){
            throw new AuthenticateException(ServerEnum.ERROR);
        }

        JSONObject jsonObject = (JSONObject)args[0];

        System.out.println("元数据");
        System.out.println(jsonObject.toJSONString());

        String token = (String)jsonObject.get("Authorization-token");

        JSONObject data = (JSONObject)jsonObject.getJSONObject("data");

        if(data == null){
            throw new AuthenticateException(ServerEnum.ERROR);
        }

        Integer id = (Integer)data.get("id");

//        String pass = id+id+"thisispass";
        String pass = "zwqzwq";

        // 验证token是否为空
        if (!StringUtils.isNotBlank(token)) {
            throw new AuthenticateException(ServerEnum.TOKEN_ISNULL);
        }

        // 验证token是否失效
        ResponseServer responseServer = JwtTokenUtils.resolverToken(token, pass);
        if (responseServer.getCode() != 200) {
            throw new AuthenticateException(ServerEnum.LOGIN_EXPIRED);
        }

        // 执行目标方法
        try {
            args[0] =data;
            obj = pjp.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return obj;


    }
}
