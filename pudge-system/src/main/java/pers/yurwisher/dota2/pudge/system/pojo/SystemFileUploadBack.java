package pers.yurwisher.dota2.pudge.system.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yq
 * @date 2021/01/09 20:57
 * @description 文件上传后台返回,前端基于此显示上传状态
 * @since V1.0.0
 */
@Data
public class SystemFileUploadBack implements Serializable {
    private static final long serialVersionUID = 7303968798567828582L;

    /**文件上传标识ID*/
    private String uid;

    /**文件ID*/
    private Long id;

    /**文件名称*/
    private String fileName;

}
