package pers.yurwisher.dota2.pudge.base;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 基础service
 * @author yq
 * @date 2018/04/11 16:06
 */
public interface BaseService<T extends BaseEntity> extends IService<T>{

    /**
     * 是否存在字段为指定值的数据
     * @param function 实体属性function
     * @param value 值
     * @return boolean
     */
    boolean haveFieldValueEq(SFunction<T,?> function,Object value);

    /**
     * 获取单个字段为指定值的实体
     * @param function 实体属性function
     * @param value 值
     * @return T
     */
    T getOneByFieldValueEq(SFunction<T,?> function,Object value);

    /**
     * 获取所有字段为指定值的实体
     * @param function 实体属性function
     * @param value 值
     * @return T
     */
    List<T> getByFieldValueEq(SFunction<T,?> function, Object value);

}
