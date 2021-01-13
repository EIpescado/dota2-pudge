package pers.yurwisher.dota2.pudge.system.service;

import org.springframework.web.multipart.MultipartFile;
import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.system.entity.SystemFile;
import pers.yurwisher.dota2.pudge.system.pojo.SystemFileUploadBack;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemFileQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemFileTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemFileVo;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


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

    /**
     * 上传文件
     *
     * @param file    文件
     * @param fileTag 文件标记,区分用途,详见字典system_file_tag
     * @param mimeType MIME_TYPE
     * @param request 当前请求
     * @return 文件集合
     */
    SystemFileUploadBack upload(MultipartFile file, Integer fileTag, String mimeType, HttpServletRequest request);

    /**
     * 获取实体关联附件
     * @param entityId 实体ID
     * @return 附件列表
     */
    List<SystemFileVo> getEntityFiles(Long entityId);

    /**
     * 下载文件
     * @param id 文件ID
     * @param response 响应流
     */
    void download(Long id, HttpServletResponse response);
}
