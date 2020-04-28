package com.dongbingbin.demo.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.operators.observable.ObservablePublish;

public class SemaphoreExample1 {
    private final static int threadCount = 20;

    public static void main(String[] args) throws Exception {

        Callable callable = new Callable() {
            private int tickt=10;
            @Override
            public Object call() throws Exception {
                // TODO Auto-generated method stub
                String result;
                while(tickt>0) {
                    Thread.sleep(1000L);
                    System.out.println("票还剩余："+tickt);
                    tickt--;
                }
                result=(String) "票已卖光";
                return result;
            }
        };

        FutureTask<String> ft=new FutureTask<String>(callable);
        new Thread(ft).start();
        try{
            String result=ft.get();
            System.out.println(result);
        }catch (Exception ex1){
            ex1.printStackTrace();
        }



        Observable.just(1,2,34,5,6).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer>6;
            }
        }).count().subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println(aLong);
            }
        });


    }

    static void test1() throws Exception{
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
