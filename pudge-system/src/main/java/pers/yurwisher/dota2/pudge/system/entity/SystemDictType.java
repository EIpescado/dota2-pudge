package pers.yurwisher.dota2.pudge.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.yurwisher.dota2.pudge.base.BaseEntity;

/**
 * @author yq
 * @date 2020/11/04 09:42
 * @description 字典类型
 * @since V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemDictType extends BaseEntity {
    private static final long serialVersionUID = -7121331701223956333L;

    /**
     * 编码
     */
    private String code;
    /**
     * 字典名
     */
    private String name;
}
