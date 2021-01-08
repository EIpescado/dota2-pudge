package pers.yurwisher.dota2.pudge.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    public static final String DOUBLE_COLON = "::";
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter YYYY_MM_DD_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final LocalTime END_OF_DAY = LocalTime.of(23, 59, 59);
    private static final Map<String,String[]> FILE_TYPE_MAP = new ConcurrentHashMap<String,String[]>(){
        private static final long serialVersionUID = 1997234982651928201L;
        {
            put("xls",new String[]{"doc","msi"});
            put("zip",new String[]{"docx","xlsx","pptx","jar","war"});
        }
    };

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
            try {
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
     *
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
     *
     * @param prefix 前缀
     * @param keys   keys
     * @return redis key
     */
    public static String generateKeyWithDoubleColon(String prefix, String... keys) {
        StringBuilder sb =  StrUtil.builder(prefix, DOUBLE_COLON);
        for (int i = 0,length = keys.length; i < length; i++) {
            sb.append(keys[i]);
            if(i != length - 1){
                sb.append(DOUBLE_COLON);
            }
        }
        return sb.toString();
    }

    public static LocalDate parseDate(String yyyyMMdd){
        return LocalDateTimeUtil.parseDate(yyyyMMdd, YYYY_MM_DD_FORMATTER);
    }

    public static LocalDateTime getStartOfDay(String yyyyMMdd){
        return LocalDateTimeUtil.parseDate(yyyyMMdd, YYYY_MM_DD_FORMATTER).atStartOfDay();
    }

    public static LocalDateTime getEndOfDay(String yyyyMMdd){
        return LocalDateTimeUtil.parseDate(yyyyMMdd, YYYY_MM_DD_FORMATTER).atTime(END_OF_DAY);
    }

    public static LocalDateTime parseTime(String yyyyMMddHHmmss){
        return LocalDateTimeUtil.parse(yyyyMMddHHmmss, DEFAULT_FORMATTER);
    }

    /**
     * 获取异常堆栈信息
     * @param throwable 异常
     * @return 字符串
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

    /**
     * 取文件内容的前28个字节.判定文件类型
     * @param bytes 文件内容字节数据
     * @return 文件类型
     */
    public static String getFileType(String fileName, byte[] bytes){
        //取前28个字节
        byte[] bytes28 = ArrayUtil.sub(bytes,0,28);
        //根据文件内容获取的文件类型
        String typeName = FileTypeUtil.getType(HexUtil.encodeHexStr(bytes28,false));
        if(StrUtil.isNotBlank(typeName)){
            String[] mayMatchTypeArray = FILE_TYPE_MAP.get(typeName);
            //部分文件根据文件内容读取类型与扩展名不符,需转化
            if(ArrayUtil.isNotEmpty(mayMatchTypeArray)){
                String extName = FileUtil.extName(fileName);
                if(Arrays.stream(mayMatchTypeArray).anyMatch(s -> s.equalsIgnoreCase(extName))){
                    return extName;
                }
            }
            return typeName;
        }else{
            //部分文件根据内容无法获取文件名 直接获取扩展名
            return FileUtil.extName(fileName);
        }
    }

    public static void main(String[] args) throws Exception{
        System.out.println(getFileType("D:\\tmp\\1.txt",IoUtil.readBytes(new FileInputStream("D:\\tmp\\1.txt"))));
        System.out.println(getFileType("D:\\tmp\\1.txt",IoUtil.readBytes(new FileInputStream("D:\\tmp\\1.txt"))));
    }
}
