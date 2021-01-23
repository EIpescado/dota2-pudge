package pers.yurwisher.dota2.pudge.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import pers.yurwisher.dota2.pudge.exception.CustomException;

import java.util.concurrent.Executor;

/**
 * @author yq
 * @date 2020/11/26 16:45
 * @description spring 异步线程池设置,使用默认可能导致内存溢出,默认实现为 SimpleAsyncTaskExecutor
 * @since V1.0.0
 */
@Configuration
public class AsyncTaskConfig implements AsyncConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskConfig.class);

    //@Override
    //public Executor getAsyncExecutor() {
    //    return null;
    //}

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            String message;
            if (throwable instanceof CustomException) {
                CustomException customException = (CustomException) throwable;
                message = customException.getTip().getMsg();
            } else {
                message = throwable.getLocalizedMessage();
            }
            logger.info("[{}]异步执行异常: [{}]", method.getName(), message);
        };
    }
}
