package pers.yurwisher.dota2.pudge.system.pojo.to;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author yq
 * @date 2020-11-04 11:18:42
 * @description 字典 To
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemDictTo implements Serializable {
    private static final long serialVersionUID = 4260293526791999571L;
    private String typeCode;
    private Integer seq;
    private String name;
    private String val;
    private Boolean fixed;
    private Long id;
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastUpdated;
}
