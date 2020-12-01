package pers.yurwisher.dota2.pudge.system.pojo.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author yq
 * @date 2020-12-01 15:17:44
 * @description 系统日志 Vo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemLogVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String nickname;
    private String action;
    private String method;
    private String params;
    private String ip;
    private String address;
    private Integer timeCost;
    private Integer type;
    private String errorInfo;
    private Long id;
    private Boolean enabled;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}
