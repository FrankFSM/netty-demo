package com.ralap.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.util.Scanner;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/3 21:04
 */
public class NettyChatClient {

    private String host;
    private int port;

    public NettyChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void init() throws InterruptedException {
        NioEventLoopGroup workerGroup = null;
        try {
            workerGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new StringEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast(new NioClientHandler());
                        }
                    });
            ChannelFuture sync = bootstrap.connect(host, port).sync();
            Channel channel = sync.channel();
            System.out.println("-----------" + channel.localAddress() + "----------");

            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                channel.writeAndFlush(s + "\n\r");
            }
        } finally {
            workerGroup.shutdownGracefully();
        }


    }

    public static void main(String[] args) throws InterruptedException {
        NettyChatClient client = new NettyChatClient("127.0.0.1", 7001);
        client.init();
    }


}
