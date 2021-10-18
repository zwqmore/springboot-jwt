package com.example.testjwt.exception;

import com.example.testjwt.utils.response.ServerEnum;

/**
 * description: 异常处理
 *
 * @author zwq
 * @date 2021/9/8 14:57
 */
public class AuthenticateException extends RuntimeException{
    private Integer code;
    public AuthenticateException(ServerEnum serverEnum) {
        super(serverEnum.getMsg());
        this.code=serverEnum.getCode();
    }
    public Integer getCode() {
        return code;
    }

}

