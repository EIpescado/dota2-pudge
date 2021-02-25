package pers.yurwisher.dota2.pudge.system.pojo.imports;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yq
 * @date 2021年2月22日 16:14:11
 * @description 字典 导入对象
 * @since V1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemDictImports implements Serializable {
    private static final long serialVersionUID = -6194048962377091161L;
    @ExcelProperty(index = 0)
    private String typeCode;
    @ExcelProperty(index = 1)
    private Integer seq;
    @ExcelProperty(index = 2)
    private String name;
    @ExcelProperty(index = 3)
    private String val;
    @ExcelProperty(index = 4)
    private Boolean fixed;
}
