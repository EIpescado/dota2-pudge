package pers.yurwisher.dota2.pudge.system.pojo.to;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author yq
 * @date 2021/01/19 22:27
 * @since V1.0.0 实体关联文件数量TO
 * @description
 */
@Data
public class EntityFileCountTo implements Serializable {
    private static final long serialVersionUID = 8000473611145565116L;

    private Integer fileCount = 0;

    @JSONField(serialize = false)
    private Long entityId;
}
