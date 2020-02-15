package com.ralap.netty.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/3 21:16
 */
public class NioClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s)
            throws Exception {
        System.out.println(s.trim());
    }
}
