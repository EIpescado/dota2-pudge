package pers.yurwisher.dota2.pudge.system.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yq
 * @date 2020-12-01 15:17:44
 * @description 系统日志 Vo,仅查看参数和异常信息
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemLogVo implements Serializable {
    private static final long serialVersionUID = -8330793237823157491L;
    private String params;
    private String errorInfo;
}
