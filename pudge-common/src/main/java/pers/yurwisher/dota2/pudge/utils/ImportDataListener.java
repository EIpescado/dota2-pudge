package pers.yurwisher.dota2.pudge.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.yurwisher.dota2.pudge.exception.ExcelDataCustomException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * EasyExcel导入监听
 *
 * @author yq 2021/2/23 9:45
 * @description ImportDataListener
 */
public class ImportDataListener<T> extends AnalysisEventListener<T> {

    private final static Logger logger = LoggerFactory.getLogger(ImportDataListener.class);

    private DataTransfer<T> dataTransfer;
    private AfterAllAnalysedHandle<T> afterAllAnalysedHandle;
    private Map<Integer, T> dataMap;
    private AtomicBoolean haveCreateMap = new AtomicBoolean(false);

    public ImportDataListener(DataTransfer<T> dataTransfer, AfterAllAnalysedHandle<T> afterAllAnalysedHandle) {
        this.dataTransfer = dataTransfer;
        this.afterAllAnalysedHandle = afterAllAnalysedHandle;
    }

    public ImportDataListener(DataTransfer<T> dataTransfer) {
        this.dataTransfer = dataTransfer;
    }

    /**
     * 每一行数据都会执行, 校验数据
     *
     * @param row             每一行数据
     * @param analysisContext 分析上下文
     */
    @Override
    public void invoke(T row, AnalysisContext analysisContext) {
        //初始化map
        if (haveCreateMap.compareAndSet(false, true)) {
            logger.info("创建map");
            dataMap = new ConcurrentHashMap<>(analysisContext.readSheetHolder().getApproximateTotalRowNumber());
        }
        //当前行信息
        ReadRowHolder rowHolder = analysisContext.readRowHolder();
        //当前行
        int currentRowNumber = rowHolder.getRowIndex() + 1;
        T resultRow = dataTransfer.transform(rowHolder);
        logger.info("第[{}]行数据:[{}]",currentRowNumber, JSON.toJSONString(resultRow));
        //转化并存入数据集合
        dataMap.put(currentRowNumber, resultRow);
    }

    /**
     * 所有数据解析完毕了调用
     *
     * @param analysisContext 分析上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        logger.info("文件读取完毕,共[{}]条", CollectionUtil.size(dataMap));
        if (afterAllAnalysedHandle != null) {
            afterAllAnalysedHandle.handle(dataMap);
        }
    }

    /**
     * 解析 额外信息（批注、超链接、合并单元格信息读取）
     */
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        logger.info("额外信息: [{}]", extra.getText());
    }


    @Override
    public void onException(Exception exception, AnalysisContext context){
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            logger.error("第 [{}] 行 [{}] 列解析异常: [{}]", excelDataConvertException.getRowIndex(), excelDataConvertException.getColumnIndex(), exception.getLocalizedMessage());
            throw new ExcelDataCustomException(excelDataConvertException.getRowIndex(), excelDataConvertException.getColumnIndex(), exception.getLocalizedMessage());
        } else {
            logger.error("解析数据失败: [{}]", exception.getLocalizedMessage());
            throw new ExcelDataCustomException(context.readRowHolder().getRowIndex() + 1);
        }
    }

    /**
     * 校验并转化数据
     *
     * @param <T> 数据对应的对象
     */
    public interface DataTransfer<T> {
        /**
         * 校验
         *
         * @param readRowHolder 行数据上下文
         * @return T
         * @throws ExcelDataCustomException 转化异常
         */
        T transform(ReadRowHolder readRowHolder) throws ExcelDataCustomException;

    }

    /**
     * 解析完excel后回调
     *
     * @param <T> 数据对应的对象
     */
    public interface AfterAllAnalysedHandle<T> {
        /**
         * 解析完的数据执行
         *
         * @param dataMap 数据map
         * @throws ExcelDataCustomException 异常
         */
        void handle(Map<Integer, T> dataMap) throws ExcelDataCustomException;
    }
}