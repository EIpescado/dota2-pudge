package pers.yurwisher.dota2.pudge.serializer;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 时间格式化
 * @author yq
 */
public class TimeSerializer implements ObjectSerializer {

    public static final TimeSerializer INSTANCE = new TimeSerializer();
    private static final  Map<String, DateTimeFormatter> FORMATTER_MAP = new ConcurrentHashMap<>();
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public TimeSerializer() {
        System.out.println("硕大的就阿斯大苏打岁侃大山的");
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type type, int i) throws IOException {
        SerializeWriter out = serializer.out;
        if(object == null){
            out.writeNull();
            return;
        }
        JSONField[] jsonFields =  object.getClass().getDeclaredAnnotationsByType(JSONField.class);
        String pattern = DEFAULT_PATTERN;
        if(jsonFields != null && jsonFields.length > 0){
            JSONField field = jsonFields[0];
            if(StringUtils.isNotEmpty(field.format())){
                pattern = field.format();
            }
        }
        DateTimeFormatter formatter;
        if (!FORMATTER_MAP.containsKey(pattern)){
            FORMATTER_MAP.put(pattern,DateTimeFormatter.ofPattern(pattern));
        }
        formatter = FORMATTER_MAP.get(pattern);
        if(object instanceof LocalDateTime){
            LocalDateTime value = (LocalDateTime) object;
            out.writeString(value.format(formatter));
        }else if(object instanceof LocalDate){
            LocalDate value = (LocalDate) object;
            out.writeString(value.format(formatter));
        }
    }
}
