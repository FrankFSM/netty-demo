package com.ralap.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/1/29 22:25
 */
public class FileChannelDemo3 {


    public static void main(String[] args) {
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("1.txt");
            File file = new File("2.txt");
            fos = new FileOutputStream(file);
            FileChannel fosChannel = fos.getChannel();
            FileChannel fisChannel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(5);
            int read = 0;
            while (read != -1) {
                buffer.clear();
                read = fisChannel.read(buffer);
                buffer.flip();
                fosChannel.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                fos.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
