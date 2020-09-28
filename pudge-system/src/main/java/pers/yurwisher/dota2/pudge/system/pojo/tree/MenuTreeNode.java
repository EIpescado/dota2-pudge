package pers.yurwisher.dota2.pudge.system.pojo.tree;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.yurwisher.dota2.pudge.wrapper.TreeNode;

import java.util.List;

/**
 * @author yq
 * @date 2020/09/25 14:52
 * @description 菜单树节点
 * @sinceV1.0.0
*/
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuTreeNode extends TreeNode<Long,MenuTreeNode> {
    private static final long serialVersionUID = 8337076770624687962L;

    /**
     * 路由名称
     */
    private String name;
    /**
     * 菜单名称
     */
    @JSONField(serialize = false)
    private String title;
    /**组件*/
    private String component;

    /**路由地址*/
    private String path;

    @JSONField(serialize = false)
    private String icon;

    /**
     * 点击目录默认跳转的path
     */
    private String redirect;

    /**
     * 是否外链菜单
     */
    @JSONField(serialize = false)
    private Boolean iFrame;
    /**
     * 是否不缓存
     */
    @JSONField(serialize = false)
    private Boolean noCache;

    /**
     * 是否固定在 tag-view中
     */
    @JSONField(serialize = false)
    private Boolean affix;

    /**
     * 是否隐藏
     */
    private Boolean hidden;
    /**
     * meta data
     */
    private MenuMeta meta;
    /**
     * 菜单下的按钮
     */
    @JSONField(serialize = false,serialzeFeatures = SerializerFeature.WriteNullListAsEmpty)
    private List<ButtonNode> buttons;

    private Boolean alwaysShow;

    @JSONField(serialize = false)
    private Integer sortNo ;

    /**
     * 是否在面包屑中显示
     */
    @JSONField(serialize = false)
    private Boolean breadCrumb;

    /**
     * 当路由设置了该属性，则会高亮相对应的侧边栏
     * 这在某些场景非常有用，比如：一个文章的列表页路由为：/article/list
     * 点击文章进入文章详情页，这时候路由为/article/1，但你想在侧边栏高亮文章列表的路由，就可以进行如下设置
     */
    @JSONField(serialize = false)
    private String activeMenu;


}
