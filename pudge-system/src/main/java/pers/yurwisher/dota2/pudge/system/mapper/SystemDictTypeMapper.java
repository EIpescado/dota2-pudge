package pers.yurwisher.dota2.pudge.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import pers.yurwisher.dota2.pudge.base.CommonMapper;
import pers.yurwisher.dota2.pudge.system.entity.SystemDictType;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemDictTypeQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemDictTypeTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemDictTypeVo;

/**
 * @author yq
 * @date 2020-11-04 11:13:36
 * @description 字典类型 Mapper
 * @since V1.0.0
 */
public interface SystemDictTypeMapper extends CommonMapper<SystemDictType> {

    /**
     * 列表
     *
     * @param page mybatis-plus分页参数
     * @param qo   查询参数
     * @return 列表
     */
    IPage<SystemDictTypeTo> list(Page page, @Param("qo") SystemDictTypeQo qo);

    /**
     * 详情
     *
     * @param id ID
     * @return 详情
     */
    SystemDictTypeVo get(@Param("id") Long id);

}
