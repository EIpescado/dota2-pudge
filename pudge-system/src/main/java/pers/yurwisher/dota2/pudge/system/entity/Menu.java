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
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 5786814160655628952L;

    /**菜单标题*/
    private String title;

    /**菜单组件名称*/
    private String name;

    /**排序*/
    private Integer menuSort ;

    /**组件路径*/
    private String component;

    /**路由地址*/
    private String path;

    /**
     * 菜单类型: 0目录,1菜单.2按钮
     */
    private Integer type;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否缓存
     */
    private Boolean cache;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 上级菜单
     */
    private Long pid;

    /**
     * 是否外链菜单
     */
    private Boolean iFrame;

    /**
     * 按钮绑定事件
     */
    private String click;

}
