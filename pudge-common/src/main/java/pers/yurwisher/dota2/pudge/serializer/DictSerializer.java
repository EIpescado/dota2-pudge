package pers.yurwisher.dota2.pudge.serializer;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextObjectSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 字典值格式化
 *
 * @author yq 2020年12月16日 12:06:58
 */
public class DictSerializer implements ContextObjectSerializer {
    public static final DictSerializer INSTANCE = new DictSerializer();

    public DictSerializer() {
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type type, int i) throws IOException {
        SerializeWriter out = serializer.out;
        //out.writeString(object != null ? object.toString() : "");
    }

    @Override
    public void write(JSONSerializer serializer, Object object, BeanContext beanContext) throws IOException {
        SerializeWriter out = serializer.out;
        String label = beanContext.getLabel();
        out.writeString(object != null ? object.toString() : "");
    }
}
