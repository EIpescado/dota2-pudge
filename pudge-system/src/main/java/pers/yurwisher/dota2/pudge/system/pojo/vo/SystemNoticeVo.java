package pers.yurwisher.dota2.pudge.system.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yq
 * @date 2020-11-16 13:41:32
 * @description 系统公告 Vo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemNoticeVo implements Serializable {
    private static final long serialVersionUID = 3235297024411360657L;
    private String title;
    private String content;
    private LocalDateTime dateCreated;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Integer type;
    @JSONField(format = "yyyy-MM-dd")
    private LocalDateTime startDate;
    @JSONField(format = "yyyy-MM-dd")
    private LocalDateTime endDate;
}
