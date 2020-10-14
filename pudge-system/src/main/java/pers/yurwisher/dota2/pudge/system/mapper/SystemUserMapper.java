package pers.yurwisher.dota2.pudge.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import pers.yurwisher.dota2.pudge.system.entity.SystemUser;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemUserQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemUserTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemUserVo;

/**
 * @author yq
 * @date 2020/09/21 11:49
 * @description 系统用户mapper
 * @since V1.0.0
 */
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 分页查询对象
     *
     * @param page mybatis-plus分页参数
     * @param qo 查询对象
     * @return 分页结果
     */
    IPage<SystemUserTo> list(Page<SystemUserTo> page,@Param("qo") SystemUserQo qo);

    /**
     * 用户详情
     * @param id 用户ID
     * @return 详情
     */
    SystemUserVo get(@Param("id") Long id);
}
