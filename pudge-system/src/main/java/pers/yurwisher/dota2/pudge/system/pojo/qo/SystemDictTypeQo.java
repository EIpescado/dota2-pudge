package pers.yurwisher.dota2.pudge.system.pojo.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import pers.yurwisher.dota2.pudge.base.BasePageQo;

/**
 * @author yq
 * @date 2020-11-04 11:13:36
 * @description 字典类型 Qo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemDictTypeQo extends BasePageQo {
    private static final long serialVersionUID = -7427027575150657727L;
}
