package com.ralap.rpc.provider;

import com.ralap.rpc.HelloService;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/5 23:02
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String msg) {
        System.out.println("收到客户端的消息");
        return "Hello Client I am Received your msg + " + msg;
    }
}
