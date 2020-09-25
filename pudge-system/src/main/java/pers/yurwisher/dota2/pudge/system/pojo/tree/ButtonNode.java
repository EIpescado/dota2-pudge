package pers.yurwisher.dota2.pudge.system.pojo.tree;

import lombok.Data;

/**
 * @author yq
 * @date 2020/09/25 17:11
 * @description 按钮
 * @since V1.0.0
 */
@Data
public class ButtonNode {

    private Long id;

    private String title;

    private Integer menuSort ;

    private Integer type;

    private String icon;

    private String click;

    private Long pid;
}
