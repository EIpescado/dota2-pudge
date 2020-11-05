package pers.yurwisher.dota2.pudge.system.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * @author yq
 * @date 2020-11-04 11:18:42
 * @description 字典 Vo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemDictVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String typeCode;
    private Integer seq;
    private String name;
    private String val;
    private Boolean fixed;
}
