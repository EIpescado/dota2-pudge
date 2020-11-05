package pers.yurwisher.dota2.pudge.system.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yq
 * @date 2020-11-04 11:13:36
 * @description 字典类型 Vo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemDictTypeVo implements Serializable {
    private static final long serialVersionUID = -1272792668533161030L;
    private String code;
    private String name;
}
