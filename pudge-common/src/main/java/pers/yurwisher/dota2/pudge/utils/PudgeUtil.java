package pers.yurwisher.dota2.pudge.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import pers.yurwisher.dota2.pudge.wrapper.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author yq
 * @date 2019/07/15 16:17
 * @description 一般工具
 * @since V1.0.0
 */
public class PudgeUtil {

    private static final Logger logger = LoggerFactory.getLogger(PudgeUtil.class);

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
    private static final List<String> LOCALHOST_IP_LIST = CollectionUtil.newArrayList("127.0.0.1", "0:0:0:0:0:0:0:1");
    private static final String APPLICATION_JSON_UTF_8 = "application/json;charset=UTF-8";
    private static final Digester SHA256_DIGESTER = new Digester(DigestAlgorithm.SHA256);
    private static final DES DES = SecureUtil.des();
    private static final String DOUBLE_COLON = "::";

    public static void responseJSON(HttpServletResponse response, R r) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(APPLICATION_JSON_UTF_8);
        response.getWriter().append(JSON.toJSONString(r));
        response.getWriter().flush();
        response.getWriter().close();
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
        if (StrUtil.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }
        //ip可能形如 117.1.1.1,192.168.0.01, 取第一个
        if (ip.contains(StrUtil.COMMA)) {
            ip = ip.split(StrUtil.COMMA)[0];
        }
        //本机IP
        if (LOCALHOST_IP_LIST.contains(ip)) {
            //获取真正的本机内网IP
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return ip;
    }

    private static DbSearcher ipSearcher;

    static {
        SpringContextHolder.addCallBacks(() -> {
            String path = "ip2region/ip2region.db";
            String name = "ip2region.db";
            try {
                //File file = IoUtil.copy (new ClassPathResource(path).getInputStream(), name);
                //IoUtil.close();
                ipSearcher = new DbSearcher(new DbConfig(), IoUtil.readBytes(new ClassPathResource(path).getInputStream()));
            } catch (IOException | DbMakerConfigException e) {
                logger.error("初始化ip2region DbSearcher 失败:[{}]", e.getLocalizedMessage());
            }
        });
    }

    /**
     * 根据ID获取实际地址
     *
     * @param ip ip
     * @return 地址
     */
    public static String getAddress(String ip) {
        //先通过IP2region从本地获取
        try {
            DataBlock dataBlock = ipSearcher.memorySearch(ip);
            String region = dataBlock.getRegion();
            String address = region.replace("0|", "");
            char symbol = '|';
            if (address.charAt(address.length() - 1) == symbol) {
                address = address.substring(0, address.length() - 1);
            }
            return address;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }


    /**
     * 获取用户客户端信息
     *
     * @param request 请求
     * @return UserAgentInfo
     */
    public static UserClientInfo getUserClientInfo(HttpServletRequest request) {
        UserClientInfo info = new UserClientInfo();
        info.setIp(getIp(request));
        info.setAddress(getAddress(info.getIp()));
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        info.setBrowser(userAgent.getBrowser().getName());
        info.setSystem(userAgent.getOs().getName());
        return info;
    }

    /**
     * 解密 HEX + DES
     *
     * @param data 待解密数据
     * @return 明文
     */
    public static String decrypt(String data) {
        return DES.decryptStr(HexUtil.decodeHex(data));
    }

    /**
     * 加密 DES + HEX
     *
     * @param data 待加密数据
     * @return 密文
     */
    public static String encrypt(String data) {
        return HexUtil.encodeHexStr(DES.encrypt(data)).toUpperCase();
    }

    /**
     * 明文密码加密
     * @param plainText 明文密码
     * @return 加密后密码
     */
    public static String encodePwd(String plainText) {
        return SHA256_DIGESTER.digestHex(plainText);
    }

    @Data
    public static class UserClientInfo {
        private String ip;
        private String address;
        private String browser;
        private String system;
    }

    /**
     * 生成redisKey
     * @param prefix 前缀
     * @param key key
     * @return redis key
     */
    public static String generateKeyWithDoubleColon(String prefix,String key){
        return  StrUtil.builder(prefix,DOUBLE_COLON,key).toString();
    }

}
