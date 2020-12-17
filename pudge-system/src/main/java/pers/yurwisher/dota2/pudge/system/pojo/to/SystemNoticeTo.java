package pers.yurwisher.dota2.pudge.system.pojo.to;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import pers.yurwisher.dota2.pudge.serializer.DictSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yq
 * @date 2020-11-16 13:41:32
 * @description 系统公告 To
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemNoticeTo implements Serializable {
    private static final long serialVersionUID = -198100227468344506L;
    private String title;
    private Long id;
    @JSONField(serializeUsing = DictSerializer.class,format = "system_notice_type")
    private Integer type;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
    private String expiredDate;
}
