package com.ralap.rpc.consumer;

import com.ralap.rpc.HelloService;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/5 23:34
 */
public class ClientBootStrap {

    public static void main(String[] args) {
        NettyClient client = new NettyClient();
        //client.noProxy();
        HelloService bean = (HelloService) client.getBean(HelloService.class);
        String result = bean.hello("I Am Ralap");
        System.out.println("Result :" + result);

    }

}
