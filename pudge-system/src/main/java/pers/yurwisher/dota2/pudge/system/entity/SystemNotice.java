package pers.yurwisher.dota2.pudge.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.yurwisher.dota2.pudge.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * @author yq
 * @date 2020/11/16 12:15
 * @description 系统公告
 * @since V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemNotice extends BaseEntity {
    private static final long serialVersionUID = 9136059269221837333L;

    /**公告类型*/
    private Integer type;
    /**标题*/
    private String title;
    /**内容*/
    private String content;
    /**失效日期*/
    private LocalDateTime expiredDate;
}
