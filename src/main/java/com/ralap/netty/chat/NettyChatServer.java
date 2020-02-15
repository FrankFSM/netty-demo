package com.ralap.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/3 20:06
 */
public class NettyChatServer {


    public void init() throws InterruptedException {
        NioEventLoopGroup workerGroup = null;
        NioEventLoopGroup bossGroup = null;
        try {
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new StringEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast(new NioServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(7001).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }
    }

    public static void main(String[] args) throws InterruptedException {
        NettyChatServer chatServer = new NettyChatServer();
        chatServer.init();
    }

}
