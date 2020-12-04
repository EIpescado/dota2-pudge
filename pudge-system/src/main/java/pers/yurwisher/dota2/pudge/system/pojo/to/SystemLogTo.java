package pers.yurwisher.dota2.pudge.system.pojo.to;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author yq
 * @date 2020-12-01 15:17:44
 * @description 系统日志 To
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemLogTo implements Serializable {
    private static final long serialVersionUID = 6600822090324708328L;
    private String userId;
    private String nickname;
    private String action;
    private String method;
    private String params;
    private String ip;
    private String address;
    private Integer timeCost;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Integer type;
    private String errorInfo;
    private LocalDateTime dateCreated;
    private String browser;
    private String system;
}
