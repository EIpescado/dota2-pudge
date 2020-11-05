package pers.yurwisher.dota2.pudge.sqlInjector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import pers.yurwisher.dota2.pudge.sqlInjector.mapperMethod.SwitchEnabledById;

import java.util.List;

/**
 * @author yq
 * @date 2020/11/05 10:15
 * @description 自定义 Sql 注入器,定义通用mapper方法
 * @since V1.0.0
 */
public class CustomLogicSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new SwitchEnabledById());
        return methodList;
    }
}
