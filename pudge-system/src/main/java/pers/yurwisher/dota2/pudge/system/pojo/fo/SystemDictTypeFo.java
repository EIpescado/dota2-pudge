package pers.yurwisher.dota2.pudge.system.pojo.fo;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author yq
 * @date 2020-11-04 11:13:36
 * @description 字典类型 Fo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemDictTypeFo implements Serializable {
    private static final long serialVersionUID = 4626277756859852181L;
    private String code;
    private String name;
}
