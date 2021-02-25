package pers.yurwisher.dota2.pudge.exception;

import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;

/**
 * @author yq 2021/2/25 11:21
 * @description ExcelDataCustomException
 */
public class ExcelDataCustomException extends CustomException {

    private static final long serialVersionUID = 8654404250231758425L;

    public ExcelDataCustomException(Integer rowNumber, Integer colNumber, String message) {
        super(SystemCustomTipEnum.IMPORT_DATA_ERROR,rowNumber,colNumber,message);
    }

    public ExcelDataCustomException(Integer rowNumber) {
        super(SystemCustomTipEnum.IMPORT_DATA_ERROR_FOR_ROW,rowNumber);
    }
}
