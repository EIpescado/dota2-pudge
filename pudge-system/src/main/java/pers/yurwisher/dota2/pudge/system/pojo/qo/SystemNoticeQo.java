package pers.yurwisher.dota2.pudge.system.pojo.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import pers.yurwisher.dota2.pudge.base.BasePageQo;

/**
 * @author yq
 * @date 2020-11-16 13:41:32
 * @description 系统公告 Qo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemNoticeQo extends BasePageQo {
    private static final long serialVersionUID = 1390060745327547110L;
    private String type;
    private String title;
}
