package com.ralap.netty.http;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/3 9:45
 */
public class HttpNettyServer {

    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap bootstrap = null;
        NioEventLoopGroup bossGroup = null;
        NioEventLoopGroup workerGroup = null;
        try {
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();

            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer());
            System.out.println("Server is Ready");

            ChannelFuture sync = bootstrap.bind(6677).sync();
            sync.channel().closeFuture().sync();


        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }


}
