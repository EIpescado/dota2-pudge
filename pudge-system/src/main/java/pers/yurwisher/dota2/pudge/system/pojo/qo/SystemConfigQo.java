package pers.yurwisher.dota2.pudge.system.pojo.qo;

import pers.yurwisher.dota2.pudge.base.BasePageQo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author yq
 * @date 2020-10-14 18:59:48
 * @description 系统配置 Qo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemConfigQo extends BasePageQo {
    private static final long serialVersionUID = 818764765533060701L;
    private String code;
    private String description;
}
