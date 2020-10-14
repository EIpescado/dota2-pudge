package pers.yurwisher.dota2.pudge.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yq
 * @date 2018/11/15 15:51
 * @description 查询对象基类
 * @since V1.0.0
 */
@Data
public class BaseQo implements Serializable {

    private static final long serialVersionUID = -5414534189079839739L;
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 关键字搜索,用于全局搜索
     */
    private String keyWord;
}
