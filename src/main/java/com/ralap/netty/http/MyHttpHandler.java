package com.ralap.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import java.net.URI;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/3 10:01
 */
public class MyHttpHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject)
            throws Exception {

        if (httpObject instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) httpObject;

            URI uri = new URI(request.uri());

            if ("/favicon.ico".equalsIgnoreCase(uri.getPath())) {
                return;
            }

            System.out.println("Msg Type " + httpObject.getClass());
            System.out.println("Client Address " + channelHandlerContext.channel().remoteAddress());

            ByteBuf byteBuf = Unpooled.copiedBuffer("Hello Client Welcome Server".getBytes());
            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            defaultFullHttpResponse.headers()
                    .set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            channelHandlerContext.writeAndFlush(defaultFullHttpResponse);
        }

    }
}
