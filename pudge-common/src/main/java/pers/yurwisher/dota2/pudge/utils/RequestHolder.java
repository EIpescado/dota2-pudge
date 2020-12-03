package pers.yurwisher.dota2.pudge.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 获取当前请求
 * @author yq 2020年12月3日 21:34:46
 */
public class RequestHolder {

    public static HttpServletRequest currentRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
