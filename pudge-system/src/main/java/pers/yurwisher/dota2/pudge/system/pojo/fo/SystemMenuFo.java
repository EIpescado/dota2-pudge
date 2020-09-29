package pers.yurwisher.dota2.pudge.system.pojo.fo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * @author yq
 * @date 2020-09-21 15:33:47
 * @description 菜单 Fo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemMenuFo implements Serializable {
    private static final long serialVersionUID = -1918237882723347378L;

    private Long pid;

    /**
     * 路由名称 一定要填写不然使用<keep-alive>时会出现各种问题
     */
    private String routerName;

    @NotBlank(message = "菜单标题必填")
    private String title;

    private String component;

    /**路由地址*/
    private String path;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 点击目录默认跳转的path
     */
    private String redirect;

    /**
     * 是否外链菜单
     */
    private Boolean iFrame;
    /**
     * 是否不缓存
     */
    private Boolean noCache;
    /**
     * 是否固定在 tag-view中
     */
    private Boolean affix;
    /**
     * 是否隐藏
     */
    private Boolean hidden;
    /**
     * 权限标识
     */
    private String permission;

    @Positive(message = "菜单排序号必须大于0")
    private Integer sortNo;

    /**
     * 是否在面包屑中显示
     */
    private Boolean breadCrumb;

    /**
     * 当路由设置了该属性，则会高亮相对应的侧边栏
     * 这在某些场景非常有用，比如：一个文章的列表页路由为：/article/list
     * 点击文章进入文章详情页，这时候路由为/article/1，但你想在侧边栏高亮文章列表的路由，就可以进行如下设置
     */
    private String activeMenu;

}
