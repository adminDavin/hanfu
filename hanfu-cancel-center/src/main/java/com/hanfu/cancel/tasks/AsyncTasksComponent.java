package com.hanfu.cancel.tasks;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
@Async("taskExecutor")
public class AsyncTasksComponent {
    public Future<String> task4() throws InterruptedException {
        long begin = System.currentTimeMillis();
        Thread.sleep(2000L);
        long end = System.currentTimeMillis();
        System.out.println("任务4耗时=" + (end - begin));
        return new AsyncResult<String>("任务4");
    }
}
