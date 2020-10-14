package pers.yurwisher.dota2.pudge.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.yurwisher.dota2.pudge.base.BaseEntity;

/**
 * 系统配置
 * @author yq 2020年10月14日 18:53:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemConfig extends BaseEntity {

    private static final long serialVersionUID = 8559194687627558756L;

    /**
     * 配置编码
     */
    private String code;

    /**
     * 配置描述
     */
    private String description;

    /**
     * 配置值
     */
    private String val;


}
