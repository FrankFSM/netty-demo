package com.ralap.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/1/29 15:52
 */
public class BioServer {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 1, TimeUnit.MINUTES,
                new LinkedBlockingQueue<Runnable>(20),
                Executors.defaultThreadFactory(), new AbortPolicy());

        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        while (true) {
            final Socket socket = serverSocket.accept();
            executorService.submit(         new Runnable() {
                public void run() {
                    handler(socket);
                }
            });
        }

    }

    public static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();
            int read;
            System.out.println("Client连接成功");
            System.out.println("线程ID" + Thread.currentThread().getId());
            System.out.println("线程Name" + Thread.currentThread().getName());
            while ((read = inputStream.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, read));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭client连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
