package pers.yurwisher.dota2.pudge.system.pojo.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yq
 * @date 2020-12-01 15:17:44
 * @description 用户操作日志 To
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserSystemLogTo implements Serializable {
    private static final long serialVersionUID = -7345328671525979102L;
    private String action;
    private String ip;
    private String address;
    private LocalDateTime dateCreated;
}
