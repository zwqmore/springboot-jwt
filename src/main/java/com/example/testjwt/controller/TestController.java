package com.example.testjwt.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.testjwt.config.TokenCheckAnnotation;
import com.example.testjwt.utils.JwtTokenUtils;
import com.example.testjwt.utils.response.ResponseServer;
import netscape.javascript.JSObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * description: 测试
 *
 * @author zwq
 * @date 2021/9/8 14:34
 */
@RestController
public class TestController {

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public String getToken(@RequestBody JSONObject jsonObject) {
        JSONObject data = jsonObject.getJSONObject("data");
        boolean a = Integer.valueOf(1).equals(data.get("id"));
        boolean b = "zwqzwq".equals(data.get("pass"));
        System.out.println("a,b:" + a + b);
        if (a && b) {
            Map<String, Object> map;
            map = new HashMap();
            map.put("uid", 1);
            String token = JwtTokenUtils.createToken(map, "zwqzwq");
            System.out.println(token);
            return token;
        }
        return "error";
    }

    @TokenCheckAnnotation
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void test(@RequestBody JSONObject jsonObject) {
        System.out.println("接口数据");
        System.out.println(jsonObject.toJSONString());
    }


    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    public ResponseServer test2(@RequestBody JSONObject jsonObject) {
        String sss = (String) jsonObject.get("fdsdf");
        sss.equals("99");
        System.out.println("异常后");

        System.out.println("接口里面");
        return ResponseServer.success();
    }

}
