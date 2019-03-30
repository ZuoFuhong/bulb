package com.maxzuo.juc;

import java.util.concurrent.*;

/**
 * 信号量Semaphore
 * Created by zfh on 2019/02/24
 */
public class SemaphoreExample {

    /** 请求总数 */
    private final static Integer CLIENT_TOTAL = 1000;

    /** 同时并发执行的线程数 */
    private final static Integer THREAD_TOTAL = 100;

    private static Integer       count        = 0;

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10);
        ThreadFactoryExample threadFactory = new ThreadFactoryExample("一号机房");
        RejectedHandlerExample rejectHandler = new RejectedHandlerExample();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 500, 60, TimeUnit.SECONDS, queue, threadFactory, rejectHandler);

        final Semaphore semaphore = new Semaphore(THREAD_TOTAL);
        CountDownLatch countDownLatch = new CountDownLatch(CLIENT_TOTAL);
        for (int i = 0; i < CLIENT_TOTAL; i++) {
            threadPool.execute(() -> {
                try {
                    // 获取一个许可（当100个许可 获取完后，新的线程需要等待其它线程释放许可）
                    semaphore.acquire();
                    add();
                    // 释放一个许可
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
            System.out.println("count: " + count);
            threadPool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void add() throws InterruptedException {
        ++count;
        System.out.println(Thread.currentThread().getName() + " i = " + count);
        //Thread.sleep(1000);
    }
}
