package pers.yurwisher.dota2.pudge.system.pojo.fo;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import pers.yurwisher.dota2.pudge.validator.annotation.DataIn;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author yq
 * @date 2020-09-21 15:33:47
 * @description 菜单 Fo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MenuFo implements Serializable {
    private static final long serialVersionUID = -1918237882723347378L;
    @NotBlank(message = "菜单标题必填")
    private String title;
    private String name;
    @Positive(message = "菜单排序号必须大于0")
    private Integer menuSort;
    private String component;
    private String path;
    /**0 目录  1菜单  2按钮*/
    @DataIn(message = "菜单类型无效",dataList = {"0","1","2"})
    private Integer type;
    private String permission;
    private String icon;
    private Boolean cache;
    private Boolean hidden;
    private Long pid;
    private Integer subCount;
    private Boolean iFrame;
    private Long id;
}
