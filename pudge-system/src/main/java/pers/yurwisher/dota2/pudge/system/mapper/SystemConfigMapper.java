package pers.yurwisher.dota2.pudge.system.mapper;

import pers.yurwisher.dota2.pudge.system.entity.SystemConfig;
import pers.yurwisher.dota2.pudge.base.CommonMapper;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemConfigQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemConfigTo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author yq
 * @date 2020-10-14 18:59:48
 * @description 系统配置 Mapper
 * @since V1.0.0
 */
public interface SystemConfigMapper extends CommonMapper<SystemConfig> {

    /**
     * 列表
     * @param page mybatis-plus分页参数
     * @param qo 查询参数
     * @return 列表
     */
    IPage<SystemConfigTo> list(Page page, @Param("qo") SystemConfigQo qo);



}
