package pers.yurwisher.dota2.pudge.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import pers.yurwisher.dota2.pudge.filter.DictValueFastJSONFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yq
 * @date 2018/04/18 14:05
 * @description web上下文配置
 * @since V1.0.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //不可使用*/*,强制用户自己定义支持的MediaTypes
        fastConverter.setSupportedMediaTypes(new ArrayList<MediaType>() {
            private static final long serialVersionUID = 2644645137309978808L;

            {
                add(MediaType.APPLICATION_JSON);
                add(MediaType.APPLICATION_JSON_UTF8);
                add(MediaType.TEXT_PLAIN);
            }
        });
        //全局配置
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                //是否输出值为null的字段,默认false
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                //全局修改日期格式,默认false,格式为 yyyy-MM-dd
                SerializerFeature.WriteDateUseDateFormat,
                //字符串输出null值
                SerializerFeature.WriteNullStringAsEmpty
        );
        //整型转字符串 防止前端精度丢失
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        fastJsonConfig.setSerializeConfig(serializeConfig);
        //日期格式 yyyy-MM-dd
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        //注册自定义json过滤器
        fastJsonConfig.setSerializeFilters(new DictValueFastJSONFilter());
        converters.add(fastConverter);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
