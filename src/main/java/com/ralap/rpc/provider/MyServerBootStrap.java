package com.ralap.rpc.provider;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/5 23:04
 */
public class MyServerBootStrap {

    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 7008);
    }

}
