package pers.yurwisher.dota2.pudge.system.pojo.tree;

import com.alibaba.fastjson.annotation.JSONField;
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

    private Long id;

    /**菜单标题*/
    @JSONField(serialize = false)
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
     * 菜单图标
     */
    @JSONField(serialize = false)
    private String icon;
    /**
     * 是否缓存
     */
    @JSONField(serialize = false)
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
     * meta data
     */
    private MenuMeta meta;

    /**
     * 菜单下的按钮
     */
    private List<ButtonNode> buttons;

    private Boolean alwaysShow;

    private String redirect;
}
