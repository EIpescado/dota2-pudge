package pers.yurwisher.dota2.pudge.system.pojo.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import pers.yurwisher.dota2.pudge.base.BasePageQo;

/**
 * @author yq
 * @date 2020-12-01 15:17:44
 * @description 系统日志 Qo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemLogQo extends BasePageQo {
    private static final long serialVersionUID = -2798517484858760595L;
    private Long logUserId;
    private Integer type;
}
