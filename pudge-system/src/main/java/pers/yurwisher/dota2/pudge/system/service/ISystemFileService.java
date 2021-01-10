package pers.yurwisher.dota2.pudge.system.service;

import org.springframework.web.multipart.MultipartFile;
import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.system.entity.SystemFile;
import pers.yurwisher.dota2.pudge.system.pojo.SystemFileUploadBack;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemFileQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemFileTo;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

import javax.servlet.http.HttpServletRequest;
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
     * @param files   文件集合
     * @param fileTag 文件标记,区分用途,详见字典system_file_tag
     * @param uidList 前端文件id集合,用于区分是否已经上传
     * @param request 当前请求
     * @return 文件集合
     */
    List<SystemFileUploadBack> upload(MultipartFile[] files, Integer fileTag, List<String> uidList, HttpServletRequest request);

}
