package pers.yurwisher.dota2.pudge.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author yq
 * @date 2021-02-22 16:34:44
 * @description excel工具
 * @since V1.0.0
 */
public class ExcelUtils {

    private static final String XLSX = ".xlsx";
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 导出xlsx
     *
     * @param data         数据
     * @param clazz        class
     * @param realFileName 文件名,可不带后缀
     */
    public static void exportXLSX(List data, Class clazz, String realFileName, HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //文件名称
            String fileName = URLEncoder.encode(realFileName, "UTF-8");
            if (!fileName.contains(XLSX)) {
                fileName = fileName + XLSX;
            }
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
            exportXLSX(data, clazz, response.getOutputStream());
        } catch (IOException e) {
            logger.info("导出xlsx异常:[{}]", e.getLocalizedMessage());
        }
    }

    /**
     * 导出xlsx
     *
     * @param data         数据
     * @param clazz        class
     * @param outputStream 输出流
     */
    private static void exportXLSX(List data, Class clazz, OutputStream outputStream) {
        EasyExcel.write(outputStream, clazz).sheet("sheet1").doWrite(data);
    }

    /**
     * 导出xlsx,写入指定目录下
     *
     * @param data     数据
     * @param clazz    class
     * @param fileName 文件名
     * @param dir      存储目录
     */
    public static void exportXLSX(List data, Class clazz, String fileName, String dir) {
        File dirFile = new File(dir);
        FileUtil.mkdir(dirFile);
        if (!fileName.contains(XLSX)) {
            fileName = fileName + XLSX;
        }
        File file = new File(dirFile, fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            exportXLSX(data, clazz, fileOutputStream);
        } catch (FileNotFoundException e) {
            logger.info("[{}] [{}]不存在", dir, fileName);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    logger.info("关闭 fileOutputStream 失败: [{}]", e.getLocalizedMessage());
                }
            }
        }
    }


    public static String getString(Integer cellIndex, Map<Integer, Cell> cellMap) {
        return MapUtil.isNotEmpty(cellMap) ? cellMap.get(cellIndex).toString() : null;
    }

    public static Integer getInteger(Integer cellIndex, Map<Integer, Cell> cellMap) {
        String val = getString(cellIndex,cellMap);
        return StrUtil.isNotBlank(val) ? Integer.valueOf(val) : null;
    }

    public static BigDecimal getBigDecimal(Integer cellIndex, Map<Integer, Cell> cellMap) {
        String val = getString(cellIndex,cellMap);
        return StrUtil.isNotBlank(val) ? new BigDecimal(val) : null;
    }

    public static Boolean getYesOrNo(Integer cellIndex, Map<Integer, Cell> cellMap) {
        String val = getString(cellIndex,cellMap);
        return StrUtil.isNotBlank(val) ? "是".equals(val) : null;
    }
}

