package com.ralap.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/1/29 22:25
 */
public class FileChannelDemo2 {


    public static void main(String[] args) {
        FileInputStream fis = null;
        try {
            File file = new File("D:\\channel.txt");
            fis = new FileInputStream(file);
            FileChannel channel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
            channel.read(buffer);
            buffer.flip();
            String str = new String(buffer.array());
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
