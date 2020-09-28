package pers.yurwisher.dota2.pudge.system.pojo.fo;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author yq
 * @date 2020-09-21 15:33:47
 * @description 菜单 Fo
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemMenuFo implements Serializable {
    private static final long serialVersionUID = -1918237882723347378L;
    @NotBlank(message = "菜单标题必填")
    private String title;
    @Positive(message = "菜单排序号必须大于0")
    private Integer sortNo;
    private String component;
    /**
     * 菜单图标
     */
    private String icon;
    /**路由地址*/
    private String path;
    /**
     * 是否外链菜单
     */
    private Boolean iFrame;
    /**
     * 是否不缓存
     */
    private Boolean noCache;
    /**
     * 是否隐藏
     */
    private Boolean hidden;
    /**
     * 权限标识
     */
    private String permission;

    private Long pid;

    public static void main(String[] args) {
        SystemMenuFo f = new SystemMenuFo();
        f.setTitle("");
        System.out.println(JSON.toJSONString(f, SerializerFeature.WriteMapNullValue));
    }
}
