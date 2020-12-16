package pers.yurwisher.dota2.pudge.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.serializer.ValueFilter;
import pers.yurwisher.dota2.pudge.annotation.DictValueToName;
import pers.yurwisher.dota2.pudge.system.service.ISystemDictService;
import pers.yurwisher.dota2.pudge.utils.SpringContextHolder;

import java.lang.reflect.Field;

/**
 * @author yq
 * @date 2020/12/16 17:30
 * @description 字典值转化 fastjson 过滤器
 * @since V1.0.0
 */
public class DictValueFastJSONFilter implements ValueFilter {

    private ISystemDictService dictService;

    @Override
    public Object process(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            DictValueToName fieldAnnotation = field.getAnnotation(DictValueToName.class);
            if(fieldAnnotation != null){
                String dictType = StrUtil.isEmpty(fieldAnnotation.dictType()) ? fieldAnnotation.value() : fieldAnnotation.dictType();
                if (StrUtil.isNotBlank(dictType)) {
                    //todo 转化
                    if (dictService == null) {
                        dictService = SpringContextHolder.getBean(ISystemDictService.class);
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            return value;
        }
        return value;
    }
}
