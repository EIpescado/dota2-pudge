package pers.yurwisher.dota2.pudge.system.mapper;

import pers.yurwisher.dota2.pudge.system.entity.SystemLog;
import pers.yurwisher.dota2.pudge.base.CommonMapper;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemLogQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemLogTo;
import pers.yurwisher.dota2.pudge.system.pojo.to.UserSystemLogTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemLogVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author yq
 * @date 2020-12-01 15:17:44
 * @description 系统日志 Mapper
 * @since V1.0.0
 */
public interface SystemLogMapper extends CommonMapper<SystemLog> {

    /**
     * 列表
     * @param page mybatis-plus分页参数
     * @param qo 查询参数
     * @return 列表
     */
    IPage<SystemLogTo> list(Page page, @Param("qo") SystemLogQo qo);

    /**
    * 详情
    * @param id ID
    * @return 详情
    */
    SystemLogVo get(@Param("id")Long id);

    /**
     * 用户操作日志列表
     * @param page mybatis-plus分页参数
     * @param qo 查询参数
     * @return 列表
     */
    IPage<UserSystemLogTo> userLogList(Page page, @Param("qo")SystemLogQo qo);

}
