package pers.yurwisher.dota2.pudge.system.mapper;

import pers.yurwisher.dota2.pudge.system.entity.SystemDict;
import pers.yurwisher.dota2.pudge.base.CommonMapper;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemDictQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemDictTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemDictVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import pers.yurwisher.dota2.pudge.wrapper.Selector;

import java.util.List;

/**
 * @author yq
 * @date 2020-11-04 11:18:42
 * @description 字典 Mapper
 * @since V1.0.0
 */
public interface SystemDictMapper extends CommonMapper<SystemDict> {

    /**
     * 列表
     * @param page mybatis-plus分页参数
     * @param qo 查询参数
     * @return 列表
     */
    IPage<SystemDictTo> list(Page page, @Param("qo") SystemDictQo qo);

    /**
    * 详情
    * @param id ID
    * @return 详情
    */
    SystemDictVo get(@Param("id")Long id);

    /**
     * 根据字典类型获取字典下拉框
     * @param dictType 字典类型
     * @return list
     */
    List<Selector<String>> select(@Param("dictType")String dictType);

    /**
     * 根据字典类型和值 获取描述
     * @param dictType 字典类型
     * @param val 值
     * @return 描述
     */
    String getNameByTypeAndVal(@Param("dictType")String dictType,@Param("val")String val);

}
