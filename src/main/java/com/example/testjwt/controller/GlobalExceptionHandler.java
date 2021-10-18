package com.example.testjwt.controller;

import com.example.testjwt.exception.AuthenticateException;
import com.example.testjwt.utils.response.ResponseServer;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description: 全局异常处理
 *
 * @author zwq
 * @date 2021/9/8 14:58
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticateException.class)
    public ResponseServer authenticateException(AuthenticateException e, HttpServletRequest request, HttpServletResponse response){
        return ResponseServer.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseServer exceptionHandler(Exception e,HttpServletRequest request, HttpServletResponse response){
        e.printStackTrace();
        System.out.println("全局异常");
        return ResponseServer.error();

    }
}
