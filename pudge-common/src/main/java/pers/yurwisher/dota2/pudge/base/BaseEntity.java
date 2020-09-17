package pers.yurwisher.dota2.pudge.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author yq
 * @date 2018/11/14 15:54
 * @description 基础Entity
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity {

    @TableId
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    /**
     * 启用,禁用
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean enabled;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime dateCreated;

    /**
     * 最后修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastUpdated;

}
