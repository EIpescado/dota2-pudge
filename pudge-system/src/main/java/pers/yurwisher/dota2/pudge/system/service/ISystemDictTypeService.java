package pers.yurwisher.dota2.pudge.system.service;

import pers.yurwisher.dota2.pudge.system.entity.SystemDictType;
import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemDictTypeFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemDictTypeQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemDictTypeTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemDictTypeVo;
import pers.yurwisher.dota2.pudge.wrapper.PageR;


/**
 * @author yq
 * @date 2020-11-04 11:13:36
 * @description 字典类型
 * @since V1.0.0
 */
public interface ISystemDictTypeService extends BaseService<SystemDictType> {

    /**
     * 新增
     * @param fo 参数
     */
    void create(SystemDictTypeFo fo);

    /**
     * 更新
     * @param id 主键
     * @param fo 参数
     */
    void update(Long id,SystemDictTypeFo fo);

    /**
     * 列表
     * @param qo 查询参数
     * @return 分页对象
     */
    PageR<SystemDictTypeTo> list(SystemDictTypeQo qo);



     /**
     * 删除
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 详情
     * @param id ID
     * @return 详情
     */
    SystemDictTypeVo get(Long id);
}
