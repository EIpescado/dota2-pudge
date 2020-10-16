package pers.yurwisher.dota2.pudge.system.service;

import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.enums.SystemConfigEnum;
import pers.yurwisher.dota2.pudge.system.entity.SystemConfig;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemConfigFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemConfigQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemConfigTo;
import pers.yurwisher.dota2.pudge.wrapper.PageR;


/**
 * @author yq
 * @date 2020-10-14 18:59:48
 * @description 系统配置
 * @since V1.0.0
 */
public interface ISystemConfigService extends BaseService<SystemConfig> {

    /**
     * 新增
     * @param fo 参数
     */
    void create(SystemConfigFo fo);

    /**
     * 更新
     * @param id 主键
     * @param fo 参数
     */
    void update(Long id,SystemConfigFo fo);

    /**
     * 列表
     * @param qo 查询参数
     * @return 分页对象
     */
    PageR<SystemConfigTo> list(SystemConfigQo qo);

    /**
     * 根据配置编码获取配置
     * @param configEnum 配置编码枚举
     * @return 配置值
     */
    String getValByCode(SystemConfigEnum configEnum);
}
