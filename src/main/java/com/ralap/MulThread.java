package com.ralap;

/**
 * @author ralap.hao
 * @version 1.0
 * @date 2020/2/10 18:36
 */
public class MulThread {

    private int size = 0;

    public synchronized void add() {
        size++;

    }

    public int get() {
        return size;
    }

    public static void main(String[] args) {
        MulThread thread = new MulThread();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                thread.add();
                System.out.println(thread.get());
            }).start();

        }
    }

}
