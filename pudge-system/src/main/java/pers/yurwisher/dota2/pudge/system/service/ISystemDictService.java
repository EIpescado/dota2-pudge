package pers.yurwisher.dota2.pudge.system.service;

import pers.yurwisher.dota2.pudge.system.entity.SystemDict;
import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemDictFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemDictQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemDictTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemDictVo;
import pers.yurwisher.dota2.pudge.wrapper.PageR;
import pers.yurwisher.dota2.pudge.wrapper.Selector;

import java.util.List;

/**
 * @author yq
 * @date 2020-11-04 11:18:42
 * @description 字典
 * @since V1.0.0
 */
public interface ISystemDictService extends BaseService<SystemDict> {

    /**
     * 新增
     * @param fo 参数
     */
    void create(SystemDictFo fo);

    /**
     * 更新
     * @param id 主键
     * @param fo 参数
     */
    void update(Long id,SystemDictFo fo);

    /**
     * 列表
     * @param qo 查询参数
     * @return 分页对象
     */
    PageR<SystemDictTo> list(SystemDictQo qo);


    /**
    * 详情
    * @param id 主键
    * @return SystemDictVo
    */
    SystemDictVo get(Long id);

    /**
     * 删除
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 根据字典类型删除字典
     * @param typeCode 字典类型编码
     */
    void deleteByTypeCode(String typeCode);

    /**
     * 字典下拉框
     * @param dictType 字典类型
     * @return so
     */
    List<Selector<String>> select(String dictType);
}
