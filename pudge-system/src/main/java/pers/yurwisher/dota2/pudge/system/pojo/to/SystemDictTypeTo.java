package pers.yurwisher.dota2.pudge.system.pojo.to;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author yq
 * @date 2020-11-04 11:13:36
 * @description 字典类型 To
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemDictTypeTo implements Serializable {
    private static final long serialVersionUID = 3678861194397555660L;
    private String code;
    private String name;
    private Long id;
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastUpdated;
}
