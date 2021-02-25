package pers.yurwisher.dota2.pudge.exception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.exception.ExcelAnalysisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedRuntimeException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import pers.yurwisher.dota2.pudge.enums.CustomTipEnum;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.wrapper.R;

import java.util.List;
import java.util.Optional;

/**
 * controller  全局异常回调
 *
 * @author yq on 2020年9月17日 15:06:25
 */
@RestControllerAdvice
public class GlobalDefaultExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    /**
     * 全局异常
     */
    @ExceptionHandler(Throwable.class)
    public R<String> defaultErrorHandler(Throwable e) {
        logger.error(e.getMessage(), e);
        return R.fail(CustomTipEnum.FAIL);
    }

    /**
     * 请求参数缺失
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R<String> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        return R.fail(e.getParameterName() + "缺失");
    }

    /**
     * 全局参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        if (CollectionUtil.isNotEmpty(errors)) {
            //返回第一个错误的信息
            Optional<ObjectError> optional = errors.stream().filter(error -> StrUtil.isNotEmpty(error.getDefaultMessage())).findFirst();
            return optional.map(error -> R.fail(error.getDefaultMessage())).orElseGet(() -> R.fail(CustomTipEnum.FAIL));
        } else {
            logger.error("自定义校验错误...", e);
            return R.fail(CustomTipEnum.FAIL);
        }
    }

    /**
     * spring 抛出的异常
     */
    @ExceptionHandler(NestedRuntimeException.class)
    public R<String> nestedRuntimeExceptionHandler(NestedRuntimeException e) {
        logger.info("spring 异常", e);
        return R.fail(CustomTipEnum.FAIL);
    }

    /**
     * 405
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<String> httpRequestMethodNotSupportedExceptionHandle(HttpRequestMethodNotSupportedException e) {
        return R.fail(CustomTipEnum.METHOD_NOT_ALLOWED);
    }

    /**
     * 415
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public R<String> httpMediaTypeNotSupportedExceptionHandle(HttpMediaTypeNotSupportedException e) {
        return R.fail(CustomTipEnum.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 404
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public R<String> noHandlerFoundExceptionHandle(NoHandlerFoundException e) {
        return R.fail(CustomTipEnum.NOT_FOUND);
    }

    /**
     * BadCredentialsException 帐号密码错误
     */
    @ExceptionHandler(BadCredentialsException.class)
    public R<String> noHandlerFoundExceptionHandle(BadCredentialsException e) {
        return R.fail(SystemCustomTipEnum.AUTH_USERNAME_OR_PASSWORD_ERROR);
    }

    /**
     * EasyExcel解析异常
     */
    @ExceptionHandler(ExcelAnalysisException.class)
    public R<String> excelAnalysisExceptionHandle(ExcelAnalysisException e) {
        Throwable throwable =  e.getCause();
        if(throwable instanceof ExcelDataCustomException){
            ExcelDataCustomException exception = (ExcelDataCustomException) throwable;
            return R.fail(exception.tip());
        }
        return R.fail(CustomTipEnum.FAIL);
    }

    /**
     * 自定义异常回调
     */
    @ExceptionHandler(CustomException.class)
    public R<String> customExceptionHandler(CustomException e) {
        return R.fail(e.tip());
    }


}
