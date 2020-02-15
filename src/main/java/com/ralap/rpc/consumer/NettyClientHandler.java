package com.ralap.rpc.consumer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.Callable;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/5 23:16
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String result;
    private String para;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(para);
        wait();
        return result;
    }

    public NettyClientHandler setPara(String para) {
        this.para = para;
        return this;
    }
}
