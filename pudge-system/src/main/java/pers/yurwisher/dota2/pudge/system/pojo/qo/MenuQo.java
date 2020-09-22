package pers.yurwisher.dota2.pudge.system.pojo.qo;

import pers.yurwisher.dota2.pudge.base.BasePageQo;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author yq
 * @date 2020-09-21 15:33:47
 * @description 菜单 Qo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MenuQo extends BasePageQo {
    private String title;
    private String componentName;
    private Integer menuSort;
    private String component;
    private String path;
    private Integer type;
    private String permission;
    private String icon;
    private Boolean cache;
    private Boolean hidden;
    private Long pid;
    private Integer subCount;
    private Boolean iFrame;
    private Long id;
    private Boolean enabled;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}
