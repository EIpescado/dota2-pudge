package pers.yurwisher.dota2.pudge.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.yurwisher.dota2.pudge.base.BaseEntity;

/**
 * @author yq
 * @date 2020/11/30 17:28
 * @description 系统日志
 * @since V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemLog extends BaseEntity {
    private static final long serialVersionUID = -3283462691884708202L;

    /**操作用户ID*/
    private String userId;
    /**用户昵称*/
    private String nickname;
    /**操作名称*/
    private String action;
    /**接口对应方法*/
    private String method;
    /**请求参数*/
    private String params;

    /**请求IP*/
    private String ip;

    /**请求地址*/
    private String address;

    /**消耗时间 单位ms*/
    private Integer timeCost;

    /**日志类型 详见字典*/
    private Integer type;

    /**异常信息*/
    private String errorInfo;
}
