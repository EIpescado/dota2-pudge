package pers.yurwisher.dota2.pudge.system.mapper;

import pers.yurwisher.dota2.pudge.system.entity.SystemNotice;
import pers.yurwisher.dota2.pudge.base.CommonMapper;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemNoticeQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemNoticeTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemNoticeVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author yq
 * @date 2020-11-16 13:41:32
 * @description 系统公告 Mapper
 * @since V1.0.0
 */
public interface SystemNoticeMapper extends CommonMapper<SystemNotice> {

    /**
     * 列表
     * @param page mybatis-plus分页参数
     * @param qo 查询参数
     * @return 列表
     */
    IPage<SystemNoticeTo> list(Page page, @Param("qo") SystemNoticeQo qo);

    /**
    * 详情
    * @param id ID
    * @return 详情
    */
    SystemNoticeVo get(@Param("id")Long id);


}
