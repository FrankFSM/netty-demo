package com.ralap.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/1/30 21:47
 */
public class NioGroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;


    public NioGroupChatServer() {

        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void listen() {
        try {

            while (true) {
                if (selector.select() > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + "上线....");
                        }

                        if (key.isReadable()) {
                            readData(key);
                        }
                        iterator.remove();
                    }

                }
            }
        } catch (Exception e) {
        } finally {

        }
    }

    public void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(128);
            int read = channel.read(buffer);
            if (read > 0) {
                String msg = new String(buffer.array());
                System.out.println("Client Msg: " + msg);
                System.out.println("Server 转发消息.......");
                for (SelectionKey selectionKey : selector.keys()) {
                    SelectableChannel targetChannel = selectionKey.channel();
                    if (targetChannel instanceof SocketChannel
                            && targetChannel != channel
                    ) {
                        SocketChannel dest = (SocketChannel) targetChannel;
                        ByteBuffer bf = ByteBuffer.wrap(msg.getBytes());
                        dest.write(bf);
                    }
                }
            }
        } catch (Exception e) {
            try {
                System.out.println((channel != null ? channel.getRemoteAddress() : "") + "离线了");
                key.cancel();
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {
        NioGroupChatServer server = new NioGroupChatServer();
        server.listen();
    }
}
