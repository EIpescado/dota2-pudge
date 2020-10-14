package pers.yurwisher.dota2.pudge.system.pojo.qo;

import pers.yurwisher.dota2.pudge.base.BasePageQo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author yq
 * @date 2020-09-21 14:45:55
 * @description 角色 Qo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemRoleQo extends BasePageQo {
    private static final long serialVersionUID = -3915449507367561347L;
    private String name;
}
