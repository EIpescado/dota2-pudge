package pers.yurwisher.dota2.pudge.system.pojo.fo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yq
 * @date 2020-11-16 13:41:32
 * @description 系统公告 Fo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemNoticeFo implements Serializable {
    private static final long serialVersionUID = -5135350856507740953L;
    @NotNull(message = "公告类型必填")
    private Integer type;
    @NotBlank(message = "公告标题必填")
    private String title;
    @NotBlank(message = "公告内容必填")
    private String content;
    @NotBlank(message = "失效日期必填")
    private String expiredDate;
}
