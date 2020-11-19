package pers.yurwisher.dota2.pudge.system.service;

import pers.yurwisher.dota2.pudge.system.entity.SystemNotice;
import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemNoticeFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemNoticeQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemNoticeTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemNoticeVo;
import pers.yurwisher.dota2.pudge.wrapper.PageR;


/**
 * @author yq
 * @date 2020-11-16 13:41:32
 * @description 系统公告
 * @since V1.0.0
 */
public interface ISystemNoticeService extends BaseService<SystemNotice> {

    /**
     * 新增
     * @param fo 参数
     */
    void create(SystemNoticeFo fo);

    /**
     * 更新
     * @param id 主键
     * @param fo 参数
     */
    void update(Long id,SystemNoticeFo fo);

    /**
     * 列表
     * @param qo 查询参数
     * @return 分页对象
     */
    PageR<SystemNoticeTo> list(SystemNoticeQo qo);


    /**
    * 详情
    * @param id 主键
    * @return SystemNoticeVo
    */
    SystemNoticeVo get(Long id);

     /**
     * 删除
     * @param id 主键
     */
    void delete(Long id);
}
