package com.example.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by magicliang on 2016/6/25.
 */
public class CustomThreadPool {
    private static final Logger log = LoggerFactory.getLogger(CustomThreadPool.class);


    private BlockingQueue<Runnable> taskQueue;
    private List<PooledThread> threads;
    private boolean isStopped;

    public static void main(String[] args) throws InterruptedException {
        CustomThreadPool pool = new CustomThreadPool(5, 5);
        for (int i = 0; i < 5; i++) {
            pool.execute(() -> {
                log.info("current thread is: " + Thread.currentThread());
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        Thread.sleep(5000L);
        pool.stop();
    }

    public CustomThreadPool() {

    }

    public CustomThreadPool(BlockingDeque<Runnable> taskQueue, List<PooledThread> threads) {
        this.taskQueue = taskQueue;
        this.threads = threads;
    }

    public CustomThreadPool(int taskCount, int threadCount) {
        this.taskQueue = new LinkedBlockingQueue<>(taskCount);
        this.threads = new ArrayList<>(threadCount);
        for (int i = 0; i < threadCount; i++) {
            //threads.add(new PooledThread(taskQueue));
            threads.add(new PooledThread());
        }
        threads.forEach((t) -> t.start());
        log.info("Thread pool started");
    }

    public synchronized void execute(Runnable task) {
        if (this.isStopped) throw
                new IllegalStateException("ThreadPool is stopped");
        //只要处理入队就够
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void stop() {
        isStopped = true;
        threads.forEach((s) -> {
            s.stopThread();
            s.stop();
        });
        log.info("Thread pool stopped");
    }


    private class PooledThread extends Thread {
        private boolean stopped = false;

        //        private  BlockingDeque<Runnable> taskQueue;
//        public PooledThread(BlockingDeque<Runnable> taskQueue){
//            this();
//            this.taskQueue = taskQueue;
//        }
        public PooledThread() {
        }

        synchronized public boolean stopThread() {
            this.stopped = true;
            this.interrupt();
            return this.stopped;
        }

        public synchronized boolean isStopped() {
            return isStopped;
        }

        @Override
        public void run() {
            //Use this to stop from spin
            //From outside, also need to interrupt the current thread
            while (!stopped) {
                try {

                    //Because here is blocking operation, we have to catch InterruptedException here.
                    //Use closure variable
                    //让线程内部自己通过消息队列通信
                    log.info("thread is running");
                    //It will be blocked
                    Runnable runnable = taskQueue.take();
                    log.info("fetch a task to run");
                    runnable.run();
                    log.info("task finished");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
