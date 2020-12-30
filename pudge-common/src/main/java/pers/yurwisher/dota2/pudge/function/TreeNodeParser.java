package pers.yurwisher.dota2.pudge.function;

/**
 * @author yq
 * @date 2020/09/25 17:39
 * @description 树节点转化
 * @since V1.0.0
 */
@FunctionalInterface
public interface TreeNodeParser<T> {

    /**
     * 节点转化
     * @param t 数据
     */
    void parse(T t);
}
