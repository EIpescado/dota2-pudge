package pers.yurwisher.dota2.pudge.system.service;

import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.system.entity.SystemFile;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemFileQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemFileTo;
import pers.yurwisher.dota2.pudge.wrapper.PageR;


/**
 * @author yq
 * @date 2020-12-31 17:12:48
 * @description 系统文件
 * @since V1.0.0
 */
public interface ISystemFileService extends BaseService<SystemFile> {


    /**
     * 列表
     *
     * @param qo 查询参数
     * @return 分页对象
     */
    PageR<SystemFileTo> list(SystemFileQo qo);

}
