package com.ralap.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/3 9:57
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast("MyHttpServerCodec", new HttpServerCodec())
                .addLast("MyHandler", new MyHttpHandler());
    }
}
