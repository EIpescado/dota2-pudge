package pers.yurwisher.dota2.pudge.system.pojo.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import pers.yurwisher.dota2.pudge.base.BasePageQo;

/**
 * @author yq
 * @date 2020-12-31 17:12:48
 * @description 系统文件 Qo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemFileQo extends BasePageQo {
    private static final long serialVersionUID = 3779387221225971949L;
    private Integer fileTag;
}
