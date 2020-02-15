package com.ralap.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/1 0:14
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("Client ctx" + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Server 喵喵喵", CharsetUtil.UTF_8));

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("Server result" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("Server Address" + ctx.channel().remoteAddress());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
