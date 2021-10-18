package com.example.testjwt.utils;

import com.example.testjwt.utils.response.ResponseServer;
import com.example.testjwt.utils.response.ServerEnum;
import io.jsonwebtoken.*;
import sun.misc.BASE64Encoder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * description: jwt
 *
 * @author zwq
 * @date 2021/9/8 14:47
 */
public class JwtTokenUtils {

    public static  String createToken(Map<String,Object> map,String pass){
        //jwt如何生成字符串
        //声明头部信息
        Map<String,Object> headerMap=new HashMap<String,Object>();
        headerMap.put("alg","HS256");
        headerMap.put("typ","JWT");
        //设置负载:不要放着涉密的东西比如:银行账号密码，余额，身份证号
        Map<String,Object> payload=new HashMap<String,Object>();
        payload.putAll(map);
        Long iat=System.currentTimeMillis();
        //设置jwt的失效时间 一分钟
        Long endTime = iat+60000l;

        //签名值就是我们的安全密钥
        String token=Jwts.builder()
                .setHeader(headerMap)
                .setClaims(payload)
                .setExpiration(new Date(endTime))
                .signWith(SignatureAlgorithm.HS256,getSecretKey(pass))
                .compact();
        return token;
    }

    public static ResponseServer resolverToken(String token ,String pass){
        Claims claims=null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(getSecretKey(pass))
                    .parseClaimsJws(token)
                    .getBody();

        }catch (ExpiredJwtException exp){
            System.out.println("token超时，token失效了");
            return ResponseServer.error(ServerEnum.TOKEN_TIMEOUT);
        }catch (SignatureException sing){
            System.out.println("token解析失败");
            return ResponseServer.error(ServerEnum.SAFETY_ERROR);
        }
        return ResponseServer.success(claims);
    }
    private static String getSecretKey(String key){
        return new BASE64Encoder().encode(key.getBytes());
    }

}

