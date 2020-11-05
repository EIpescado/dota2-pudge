package pers.yurwisher.dota2.pudge.system.pojo.qo;

import pers.yurwisher.dota2.pudge.base.BasePageQo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author yq
 * @date 2020-11-04 11:18:42
 * @description 字典 Qo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemDictQo extends BasePageQo {
    private static final long serialVersionUID = 8887784568652377229L;
    @NotBlank(message = "字典类型必选")
    private String typeCode;
}
