package pers.yurwisher.dota2.pudge.system.mapper;

import pers.yurwisher.dota2.pudge.system.entity.SystemRole;
import pers.yurwisher.dota2.pudge.base.CommonMapper;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemRoleQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemRoleTo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author yq
 * @date 2020-09-21 14:45:55
 * @description 角色 Mapper
 * @since V1.0.0
 */
public interface SystemRoleMapper extends CommonMapper<SystemRole> {

    /**
     * 列表
     * @param page mybatis-plus分页参数
     * @param qo 查询参数
     * @return 列表
     */
    IPage<SystemRoleTo> list(Page page, @Param("qo") SystemRoleQo qo);

    /**
     * 获取指定用户角色
     * @param userId 用户ID
     * @return 角色集合
     */
    List<String> getUserRole(@Param("userId")Long userId);
}
