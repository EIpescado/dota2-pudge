package pers.yurwisher.dota2.pudge.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.yurwisher.dota2.pudge.base.BaseEntity;

/**
 * @author yq
 * @date 2020/09/21 14:11
 * @description 角色
 * @since V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemRole extends BaseEntity {
    private static final long serialVersionUID = -3777984388888594077L;
    /**角色名称*/
    private String name;
    /**角色描述*/
    private String description;
    /**角色登记*/
    private Integer level;
}
