package com.dongbingbin.demo.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample1 {
    private final static int threadCount = 20;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();
        // 每次最多三个线程获取许可
        final Semaphore semaphore = new Semaphore(3);
        final CountDownLatch cdl = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire(); // 获取一个许可
                        test(threadNum);
                        semaphore.release(); // 释放一个许可
                    } catch (Exception e) {
                        //log.error("exception", e);
                        e.printStackTrace();
                    } finally {
                        cdl.countDown();
                    }
                }
            });
        }
        exec.shutdown();
        cdl.await();
        System.out.println("over");
    }

    private static void test(int threadNum) throws Exception {
        System.out.println("{"+threadNum+"}");
        Thread.sleep(1000);
    }
}
