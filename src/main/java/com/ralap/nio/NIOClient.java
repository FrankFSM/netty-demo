package com.ralap.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/1/30 15:25
 */
public class NIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);
        if (!socketChannel.connect(address)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("连接中，非阻塞................");
            }
            String str = "I'am Ralap";
            ByteBuffer wrap = ByteBuffer.wrap(str.getBytes());
            socketChannel.write(wrap);
            System.in.read();
        }

    }

}
