package pers.yurwisher.dota2.pudge.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author yq
 * @date 2018/11/14 14:58
 * @description 通用mapper
 * @since V1.0.0
 */
public interface CommonMapper<T extends BaseEntity> extends BaseMapper<T> {

    Integer switchEnabledById(@Param("id")Long id);
}
