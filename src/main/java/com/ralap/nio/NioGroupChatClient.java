package com.ralap.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/1/30 21:48
 */
public class NioGroupChatClient {

    Selector selector;
    SocketChannel socketChannel;
    private String username;

    public NioGroupChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6667));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + "     connect is ok.....");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readInfo() throws IOException {

        if (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isReadable()) {

                    SocketChannel channel = (SocketChannel) next.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    channel.read(buffer);
                    String msg = new String(buffer.array()).trim();
                    System.out.println(channel.getRemoteAddress() + "发来消息 ：" + msg);
                }
                iterator.remove();
            }
        }
    }

    public void sendInfo(String info) {
        info = username + " say " + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final NioGroupChatClient client = new NioGroupChatClient();
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        client.readInfo();
                        Thread.sleep(3000);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String s = scan.nextLine();
            client.sendInfo(s);
        }
    }

}
