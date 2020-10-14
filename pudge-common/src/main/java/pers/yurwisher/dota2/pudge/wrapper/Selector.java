package pers.yurwisher.dota2.pudge.wrapper;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;

/**
 * @author yq
 * @date 2020年9月17日 15:32:32
 * @description select object,下拉框对象
 * @since V1.0.0
 */
@Data
public class Selector<T> {

    private String label;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private T value;
}
