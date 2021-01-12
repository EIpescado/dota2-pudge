package pers.yurwisher.dota2.pudge.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import pers.yurwisher.dota2.pudge.base.CommonMapper;
import pers.yurwisher.dota2.pudge.system.entity.SystemFile;
import pers.yurwisher.dota2.pudge.system.pojo.SystemFileUploadBack;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemFileQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemFileTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemFileVo;

import java.util.List;

/**
 * @author yq
 * @date 2020-12-31 17:12:48
 * @description 系统文件 Mapper
 * @since V1.0.0
 */
public interface SystemFileMapper extends CommonMapper<SystemFile> {

    /**
     * 列表
     *
     * @param page mybatis-plus分页参数
     * @param qo   查询参数
     * @return 列表
     */
    IPage<SystemFileTo> list(Page page, @Param("qo") SystemFileQo qo);

    /**
     * 根据文件hash获取文件信息
     * @param fileHash 文件摘要值
     * @return SystemFileUploadBack
     */
    SystemFileUploadBack findByFileHash(@Param("fileHash") String fileHash);

    /**
     * 获取实体关联附件信息
     * @param entityId 实体ID
     * @return 附件信息集合
     */
    List<SystemFileVo> getEntityFiles(@Param("entityId")Long entityId);
}
