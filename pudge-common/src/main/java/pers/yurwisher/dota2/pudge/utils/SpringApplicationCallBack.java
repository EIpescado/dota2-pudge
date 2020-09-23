package pers.yurwisher.dota2.pudge.utils;

/**
 * spring上下文初始化前回调,用于初始化一些配置
 * @author yq
 */
public interface SpringApplicationCallBack {
    /**
     * 回调执行方法
     */
    void execute();

    /**
     * 回调任务名称
     * return name
     */
    default String name() {
        return Thread.currentThread().getId() + ":" + this.getClass().getName();
    }
}

