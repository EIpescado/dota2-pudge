package pers.yurwisher.dota2.pudge.system.pojo.tree;

import lombok.Data;

import java.util.List;
import java.util.Map;


/**
 * @author yq
 * @date 2020/09/25 16:02
 * @description meta
 * @since V1.0.0
 */
@Data
public class MenuMeta {

    private String title;

    private String icon;

    private Boolean noCache;

    private Boolean affix;

    private Boolean breadCrumb;

    private String activeMenu;

    /**
     * 菜单下的按钮
     */
    private Map<String,List<ButtonNode>> buttons;
}
