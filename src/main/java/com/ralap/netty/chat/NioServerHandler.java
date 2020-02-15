package com.ralap.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/3 20:31
 */
public class NioServerHandler extends SimpleChannelInboundHandler<String> {


    private static ChannelGroup channelGroup = new DefaultChannelGroup(
            GlobalEventExecutor.INSTANCE);
    ThreadLocal<SimpleDateFormat> localSimpleDate = new ThreadLocal<>();

    public NioServerHandler() {
        localSimpleDate.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.add(channel);
        String msg = String.format("Client 【%s】加入群聊.....", channel.remoteAddress());
        channelGroup.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush(msg);
            }
        });
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String msg = String.format("Client 【%s】上线.....", channel.remoteAddress());
        System.out.println(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String msg = String.format("Client 【%s】下线.....", channel.remoteAddress());
        System.out.println(msg);


    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String msg = String.format("Client 【%s】下线.....", channel.remoteAddress());
        channel.writeAndFlush(msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s)
            throws Exception {

        Channel channel = channelHandlerContext.channel();
        channelGroup.forEach(ch -> {
            if (ch != channel) {
                String msg = String
                        .format("%s 客户端【%s】Send Msg： %s", localSimpleDate.get().format(new Date()),
                                channel.remoteAddress(), s);
                ch.writeAndFlush(msg);
            } else {
                ch.writeAndFlush("自己发送消息 " + s);
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
