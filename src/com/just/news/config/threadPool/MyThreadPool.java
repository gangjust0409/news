package com.just.news.config.threadPool;

import java.util.concurrent.*;

public class MyThreadPool extends ThreadPoolExecutor {

    public MyThreadPool() {
        super(50,200,10,TimeUnit.SECONDS,new LinkedBlockingDeque(),Executors.defaultThreadFactory());
    }
}
