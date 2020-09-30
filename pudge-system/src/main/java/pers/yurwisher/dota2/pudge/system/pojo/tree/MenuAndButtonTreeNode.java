package pers.yurwisher.dota2.pudge.system.pojo.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.yurwisher.dota2.pudge.wrapper.TreeNode;

/**
 * @author yq
 * @date 2020/09/30 11:28
 * @description 菜单按钮综合树 用于菜单管理
 * @since V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuAndButtonTreeNode extends TreeNode<Long,MenuAndButtonTreeNode> {

    /**菜单或按钮名称*/
    private String name;

    /**是否为按钮*/
    private Boolean whetherButton;

    /**图标*/
    private String icon;

    /**排序号*/
    private Integer sortNo;
}
