package pers.yurwisher.dota2.pudge.system.pojo.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.yurwisher.dota2.pudge.wrapper.TreeNode;

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

    /**组件*/
    private String component;

    /**路由地址*/
    private String path;

    /**
     * 点击目录默认跳转的path
     */
    private String redirect;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 是否外链菜单
     */
    private Boolean iFrame;

    private Integer sortNo ;

    /**
     * meta data
     */
    private MenuMeta meta;


}
