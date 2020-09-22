package pers.yurwisher.dota2.pudge.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;


/**
 * @author yq
 * @date 2018/11/14 15:01
 * @description mybatis-plus config
 * @since V1.0.0
 */
@Configuration
@MapperScan(basePackages = "pers.yurwisher.dota2.pudge.*.mapper")
public class MybatisPlusConfig {

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }


    /**
     * 性能分析拦截器，不建议生产使用
     */
//    @Bean
//    public PerformanceInterceptor performanceInterceptor(){
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        //格式化sql
////        performanceInterceptor.setFormat(true);
//        return performanceInterceptor;
//    }

    /**
     * 自动填充值
     */
    @Bean
    public MetaObjectHandler hfyMetaObjectHandler(){
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.setFieldValByName("enabled", true, metaObject);
                this.setFieldValByName("dateCreated", LocalDateTime.now(), metaObject);
                this.setFieldValByName("lastUpdated", LocalDateTime.now(), metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName("lastUpdated", LocalDateTime.now(), metaObject);
            }
        };
    }
}
