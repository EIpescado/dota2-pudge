package pers.yurwisher.dota2.pudge.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import pers.yurwisher.dota2.pudge.properties.LoginProperties;
import pers.yurwisher.dota2.pudge.properties.SecurityProperties;
import pers.yurwisher.dota2.pudge.system.service.CustomRedisCacheService;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yq
 * @date 2020/09/18 14:11
 * @description redis配置
 * @since V1.0.0
 */
@Slf4j
@Configuration
@EnableCaching
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 自定义redis 缓存管理
     *
     * @param redisConnectionFactory     工厂
     * @param redisCacheConfigurationMap 自定义key策略
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, Map<String, RedisCacheConfiguration> redisCacheConfigurationMap) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                // 默认策略，未配置的 key 会使用这个, 默认过期时间 8 小时
                this.createRedisCacheConfigurationWithTtl(Duration.ofHours(8).toMinutes()),
                // 指定 key 策略
                redisCacheConfigurationMap
        );
    }

    @Bean
    public Map<String, RedisCacheConfiguration> redisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>(CustomRedisCacheService.CONFIG_MAP.size());
        //遍历自定义缓存过期配置
        CustomRedisCacheService.CONFIG_MAP.forEach((k, v) ->
                redisCacheConfigurationMap.put(k, this.createRedisCacheConfigurationWithTtl(v.getMinutes()))
        );
        return redisCacheConfigurationMap;
    }

    /**
     * 创建redis缓存 key过期配置
     *
     * @param minutes 过期时间分钟
     * @return RedisCacheConfiguration
     */
    private RedisCacheConfiguration createRedisCacheConfigurationWithTtl(long minutes) {
        GenericFastJsonRedisSerializer fastSerializer = new GenericFastJsonRedisSerializer();
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(fastSerializer)
        ).entryTtl(Duration.ofMinutes(minutes));
        return redisCacheConfiguration;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //序列化
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // key的序列化采用StringRedisSerializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        template.setDefaultSerializer(fastJsonRedisSerializer);
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public CustomRedisCacheService customRedisCacheService(RedisTemplate<String, Object> redisTemplate,
                                                           SecurityProperties securityProperties,
                                                           LoginProperties loginProperties) {
        return new CustomRedisCacheService(redisTemplate, securityProperties, loginProperties);
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        // 异常处理，当Redis发生异常时，打印日志，但是程序正常走
        log.info("初始化Redis CacheErrorHandler");
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.error("Redis occur handleCacheClearError：", e);
            }
        };
    }
}
