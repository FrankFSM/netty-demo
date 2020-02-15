package com.ralap.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/13 23:59
 */
public class SourceDemo {

    public static void main(String[] args) {
        ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
        ByteBuf byteBuf = allocator.directBuffer(500);
        System.out.println(byteBuf.memoryAddress());
        ByteBuf byteBuf1 = allocator.directBuffer(1000);
        System.out.println(byteBuf1.memoryAddress());
    }

}
