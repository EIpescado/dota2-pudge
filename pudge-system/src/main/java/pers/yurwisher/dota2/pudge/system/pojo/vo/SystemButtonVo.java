package pers.yurwisher.dota2.pudge.system.pojo.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author yq
 * @date 2020-09-26 11:35:17
 * @description 按钮 Vo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemButtonVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String buttonName;
    private Long menuId;
    private Integer sortNo;
    private String icon;
    private String position;
    private String click;
    private Long id;
    private Boolean enabled;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}
