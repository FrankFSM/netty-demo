package com.ralap.rpc.consumer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/5 23:21
 */
public class NettyClient {

    private static ExecutorService executor = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static NettyClientHandler client;
    private static ChannelFuture sync;

    public void noProxy() {
        initClient();
        try {
            client.setPara("Ralap Client");
            executor.submit(client).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Object getBean(final Class<?> serverClass) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serverClass}, (proxy, method, args) -> {
                    if (client == null) {
                        initClient();
                    }
                    client.setPara("Ralap Client");
                    return executor.submit(client).get();

                });
    }

    private static void initClient() {

        client = new NettyClientHandler();
        NioEventLoopGroup group = null;
        try {
            Bootstrap bootstrap = new Bootstrap();
            group = new NioEventLoopGroup();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(client);
                        }
                    });
            sync = bootstrap.connect("127.0.0.1", 7008).sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sync.isDone()) {
                //这里关闭存在问题
                //group.shutdownGracefully();
            }
        }
    }

}
