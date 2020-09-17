package pers.yurwisher.dota2.pudge.wrapper;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页包装类 根据前端需要变更
 * @author yq
 */
@Data
public class PageR<T> implements Serializable {

    private static final long serialVersionUID = -2047195214836327951L;
    /**
     * 总条数
     */
    private long total;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 数据
     */
    private List<T> rows;

}
