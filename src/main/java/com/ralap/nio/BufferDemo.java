package com.ralap.nio;

import java.nio.IntBuffer;
import java.util.Random;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/1/29 21:14
 */
public class BufferDemo {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            Random random = new Random();
            intBuffer.put(random.nextInt(100));
        }

        intBuffer.flip();
        //for (int i = 0; i < intBuffer.capacity(); i++) {
        //    System.out.println(intBuffer.get(i));
        //}
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }

}
