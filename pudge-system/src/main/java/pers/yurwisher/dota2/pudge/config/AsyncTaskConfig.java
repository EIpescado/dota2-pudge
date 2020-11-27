package pers.yurwisher.dota2.pudge.config;

//import org.springframework.context.annotation.Configuration;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.Executor;

/**
 * @author yq
 * @date 2020/11/26 16:45
 * @description spring 异步线程池设置,使用默认可能导致内存溢出,默认实现为 SimpleAsyncTaskExecutor
 * @since V1.0.0
 */
//@Configuration
public class AsyncTaskConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        return null;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
