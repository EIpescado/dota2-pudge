package pers.yurwisher.dota2.pudge.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.yurwisher.dota2.pudge.base.BaseEntity;

/**
 * @author yq
 * @date 2020/11/04 11:01
 * @description 字典表
 * @since V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemDict extends BaseEntity {

    private static final long serialVersionUID = -4298121357500878107L;
    /**
     * 字典类型编码 即SystemDictType code
     */
    private String typeCode;
    /**
     * 序号
     */
    private Integer seq;
    /**
     * 字典名
     */
    private String name;
    /**
     * 字典值
     */
    private String val;

    /**
     * 是否固定.即不可修改
     */
    private Boolean fixed;

}
