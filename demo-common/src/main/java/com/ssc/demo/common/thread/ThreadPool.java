package com.ssc.demo.common.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 * @create 2020/05/13 12:47
 */
@Slf4j
public class ThreadPool {
    private ThreadPool() {
    }

    private static final class ThreadPoolHolder {
        static final ThreadPool THREAD_POOL = new ThreadPool();
    }

    public static ThreadPool instance() {
        return ThreadPoolHolder.THREAD_POOL;
    }

    /**
     * 存放任务的阻塞队列 阻塞式链式队列
     * 在使用的时候可以指定容量，也可以不指定。但是如果不指定，那么默认容量是Integer.MAX_VALUE->2^31-1
     */
    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    /**
     * 获取cpu核数
     */
    private final int cpuNum = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池创建 Executors.defaultThreadFactory()默认工厂
     */
    protected final ThreadPoolExecutor executor = new ThreadPoolExecutor(cpuNum, cpuNum * 2,
            60L, TimeUnit.SECONDS, queue, new WorkThreadFactory("common"),
            new RejectedPolicy()) {
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            if (t != null) {
                t.printStackTrace();
                log.error("【线程池执行任务异常】", t);
            }
        }
    };

    public void submit(Runnable runnable) throws RejectedExecutionException {
        executor.execute(runnable);
    }

    /**
     * Thread工厂
     */
    private static class WorkThreadFactory implements ThreadFactory {
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        WorkThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix + "-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, namePrefix + threadNumber.getAndIncrement());
            if (t.isDaemon()) {
                t.setDaemon(true);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    /**
     * 拒绝策略
     */
    private static class RejectedPolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            // 1.当触发拒绝策略时，直接抛出拒绝执行的异常，打断当前执行流程
//            throw new RejectedExecutionException("线程池已满");

            // 2.如果线程池未关闭，就弹出队列头部的元素，然后尝试执行
//            if (!executor.isShutdown()){
//                executor.getQueue().poll();
//                executor.execute(r);
//            }

            // 3.当触发拒绝策略时，只要线程池没有关闭，就由提交任务的当前线程处理
            if (!executor.isShutdown()) {
                r.run();
            }
        }
    }
}
