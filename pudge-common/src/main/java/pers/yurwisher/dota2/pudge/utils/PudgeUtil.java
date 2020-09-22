package pers.yurwisher.dota2.pudge.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import pers.yurwisher.dota2.pudge.enums.ICustomTipEnum;
import pers.yurwisher.dota2.pudge.wrapper.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yq
 * @date 2019/07/15 16:17
 * @description 一般工具
 * @since V1.0.0
 */
public class PudgeUtil {

    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"
    };
    private static final String UN_KNOWN = "unknown";
    private static final String APPLICATION_JSON_UTF_8 = "application/json;charset=UTF-8";

    public static void responseJSON(HttpServletResponse response, R r) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(APPLICATION_JSON_UTF_8);
        response.getWriter().append(JSON.toJSONString(r));
        response.getWriter().flush();
        response.getWriter().close();
    }

    public static void responseJSON(HttpServletResponse response, ICustomTipEnum customTipEnum) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(APPLICATION_JSON_UTF_8);
        response.getWriter().append(JSON.toJSONString(R.fail(customTipEnum)));
    }


    /**
     * 根据请求获取用户ip 取第一个非unknown的ip,穿透代理
     *
     * @param request 请求
     */
    public static String getIp(HttpServletRequest request) {
        String ip = "";
        for (String head : HEADERS_TO_TRY) {
            ip = request.getHeader(head);
            if (StrUtil.isNotEmpty(ip) && !UN_KNOWN.equalsIgnoreCase(ip)) {
                break;
            }
        }
        return StrUtil.isEmpty(ip) ? request.getRemoteAddr() : ip;
    }

}
