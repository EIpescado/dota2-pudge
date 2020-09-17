package pers.yurwisher.dota2.pudge.wrapper;

import lombok.Data;
import pers.yurwisher.dota2.pudge.enums.ICustomTipEnum;

import java.io.Serializable;

/**
 * 返回结果
 * @author yq 2018年12月27日 16:03:56
 */
@Data
public class R<T> implements Serializable {

    private static final String SUCCESS_CODE = "0";
    private static final String FAILURE_CODE = "1";
    private static final String SUCCESS_MESSAGE = "success";
    private static final long serialVersionUID = 4547631837147011279L;

    /**返回结果 编码 1：成功 0：失败*/
    private String code ;
    /**返回结果 描述信息*/
    private String message ;
    /**返回结果*/
    private T res  ;

    public R(String code, String message, T res) {
        this.code = code;
        this.message = message;
        this.res = res;
    }

    public R(String code, String message) {
        this.code = code;
        this.message = message;
        this.res = null;
    }

    /**判断返回是否成功*/
    public boolean succeed(){
        return code.equals(SUCCESS_CODE);
    }

    /**返回成功*/
    public static <T> R<T> ok(T res){
        return new R<>(SUCCESS_CODE,SUCCESS_MESSAGE,res);
    }

    public static <T> R<T> ok(){
        return new R<>(SUCCESS_CODE,SUCCESS_MESSAGE,null);
    }

    /**返回失败*/
    public static R<String> fail(String message){
        return new R<>(FAILURE_CODE,message);
    }

    public static R<String> fail(ICustomTipEnum tip){
        return new R<>(tip.getCode(),tip.getMsg());
    }

    public static R<String> fail(ICustomTipEnum tip,Object...args){
        return new R<>(tip.getCode(),String.format(tip.getMsg(),args));
    }

    public static R<String> fail(CustomTip tip){
        return new R<>(tip.getCode(),tip.getMsg());
    }

    public static R<String> fail(CustomTip tip,Object...args){
        return new R<>(tip.getCode(),String.format(tip.getMsg(),args));
    }
}
