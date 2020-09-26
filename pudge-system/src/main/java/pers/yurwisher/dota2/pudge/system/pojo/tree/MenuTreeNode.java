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
    /**
     * 菜单名称
     */
    @JSONField(serialize = false)
    private String menuName;
    /**组件*/
    private String component;
    private Integer sortNo ;
    @JSONField(serialize = false)
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
    @JSONField(serialize = false)
    private Boolean noCache;
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
    private List<ButtonNode> buttons;

    private Boolean alwaysShow;
    private String redirect;
}
