package com.ralap.rpc.provider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/5 23:11
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg " + msg.toString());
        String result = new HelloServiceImpl().hello(msg.toString());
        ctx.writeAndFlush(result);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }
}
