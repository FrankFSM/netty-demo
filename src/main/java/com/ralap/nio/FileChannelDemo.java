package com.ralap.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/1/29 22:25
 */
public class FileChannelDemo {


    public static void main(String[] args) {
        String str = "I'am Ralap";
        FileOutputStream fos = null;
        try {
            File file = new File("D:\\channel.txt");
            fos = new FileOutputStream(file);
            FileChannel channel = fos.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(str.getBytes());
            buffer.flip();
            channel.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
