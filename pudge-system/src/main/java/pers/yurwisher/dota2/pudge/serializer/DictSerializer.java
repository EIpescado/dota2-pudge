package pers.yurwisher.dota2.pudge.serializer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextObjectSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import pers.yurwisher.dota2.pudge.system.service.ISystemDictService;
import pers.yurwisher.dota2.pudge.utils.SpringContextHolder;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 字典值格式化
 *
 * @author yq 2020年12月16日 12:06:58
 */
public class DictSerializer implements ContextObjectSerializer {
    public static final DictSerializer INSTANCE = new DictSerializer();

    private ISystemDictService dictService;

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type type, int i) throws IOException {
    }

    @Override
    public void write(JSONSerializer serializer, Object object, BeanContext beanContext) throws IOException {
        SerializeWriter out = serializer.out;
        if(object == null){
            out.writeNull();
            return;
        }
        //字典类型
        String dictType = beanContext.getFormat();
        if (StrUtil.isNotBlank(dictType)) {
            //todo 转化
            if (dictService == null) {
                dictService = SpringContextHolder.getBean(ISystemDictService.class);
            }
            String result = dictService.getNameByTypeAndVal(dictType,object.toString());
            out.writeString(result);
            return;
        }
        out.writeString(object.toString());
    }
}
