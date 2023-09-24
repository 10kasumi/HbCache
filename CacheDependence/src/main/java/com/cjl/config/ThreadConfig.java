package com.cjl.config;

import java.util.concurrent.*;

public class ThreadConfig {
    public static ExecutorService backupThread = Executors.newSingleThreadExecutor();
    public static ExecutorService recoveryThread = Executors.newSingleThreadExecutor();
    public static ExecutorService blockThreadPool = new ThreadPoolExecutor(10,
            20,
            20,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(50),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.DiscardOldestPolicy()
    );

    public static ScheduledExecutorService clusterThreadPool = Executors.newScheduledThreadPool(5);

}
