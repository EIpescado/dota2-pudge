package pers.yurwisher.dota2.pudge.base;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author yq
 * @date 2018/11/15 15:51
 * @description 分页查询对象 基类
 * @since V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BasePageQo extends BaseQo {

    private static final long serialVersionUID = 6482628540038042720L;
    /**
     * 页数
     */
    protected Long page = 1L;

    /**
     * 单页显示总条数
     */
    protected Long size = 10L;
}
