package pers.yurwisher.dota2.pudge.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.yurwisher.dota2.pudge.base.BaseEntity;

/**
 * 菜单
 * @author yq 2020年9月21日 15:28:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemMenu extends BaseEntity {
    private static final long serialVersionUID = 5786814160655628952L;

    /**
     * 上级菜单
     */
    private Long pid;
    /**
     * 菜单名称
     */
    private String menuName;
    /**组件*/
    private String component;
    /**排序*/
    private Integer sortNo ;
    /**
     * 菜单图标
     */
    private String icon;
    /**路由地址*/
    private String path;
    /**
     * 是否外链菜单
     */
    private Boolean iFrame;
    /**
     * 是否不缓存
     */
    private Boolean noCache;
    /**
     * 是否隐藏
     */
    private Boolean hidden;
    /**
     * 权限标识
     */
    private String permission;

}
