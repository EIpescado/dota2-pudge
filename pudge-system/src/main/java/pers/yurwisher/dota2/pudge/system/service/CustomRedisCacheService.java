package pers.yurwisher.dota2.pudge.system.service;

import cn.hutool.core.collection.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import pers.yurwisher.dota2.pudge.constants.CacheConstant;
import pers.yurwisher.dota2.pudge.security.bean.LoginProperties;
import pers.yurwisher.dota2.pudge.security.bean.SecurityProperties;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author yq
 * @date 2020/10/16 09:07
 * @description 自定义redis过期时间缓存配置
 * @since V1.0.0
 */
public class CustomRedisCacheService {


    /**
     * 缓存过期配置
     */
    public static Map<String, CustomCacheConfig> CONFIG_MAP = new ConcurrentHashMap<>();
    /**
     * 默认缓存过期时间 8小时
     */
    private static final long DEFAULT_EXPIRE_MINUTES = 640;
    private RedisTemplate<String, Object> redisTemplate;
    private SecurityProperties securityProperties;
    private LoginProperties loginProperties;

    public CustomRedisCacheService(RedisTemplate<String, Object> redisTemplate, SecurityProperties securityProperties, LoginProperties loginProperties) {
        this.redisTemplate = redisTemplate;
        this.securityProperties = securityProperties;
        this.loginProperties = loginProperties;
        init();
    }

    /**
     * 统一初始化缓存过期时间
     */
    private void init() {
        put(CacheConstant.AnName.SYSTEM_USER_INFO, Duration.ofDays(10), true);
        put(CacheConstant.AnName.SYSTEM_CONFIG, Duration.ofDays(-1), true);

        put(CacheConstant.MaName.LOGIN_CODE, Duration.ofMinutes(loginProperties.getCodeConfig().getExpiration()), false);
        //自定义的token 过期时间
        if (CollectionUtil.isNotEmpty(securityProperties.getClientTokenConfigs())) {
            securityProperties.getClientTokenConfigs().forEach(
                    c -> put(PudgeUtil.generateKeyWithDoubleColon(CacheConstant.MaName.ONLINE_USER, c.getType().name()), c.getExpireTime(), false)
            );
        }
        put(CacheConstant.MaName.SYSTEM_USER_TREE, Duration.ofDays(10), false);

        put(CacheConstant.Key.SYSTEM_WHOLE_TREE, Duration.ofDays(7), false);
        put(CacheConstant.Key.ROLE_SELECT, Duration.ofDays(5), false);
    }

    public RedisTemplate<String, Object> redisTemplate() {
        return redisTemplate;
    }

    /**
     * redis 缓存环绕增强
     *
     * @param redisKey   缓存key
     * @param ttlMinutes 过期时间
     * @param getter     数据获取
     * @param <T>        类型
     * @return 需要的数据
     */
    @SuppressWarnings("unchecked")
    public <T> T cacheRound(String redisKey, long ttlMinutes, RealDataGetter<T> getter) {
        //从缓存中提取数据
        T result = (T) redisTemplate.opsForValue().get(redisKey);
        if (result != null) {
            return result;
        } else {
            if (getter != null) {
                result = getter.get();
            }
            if (result != null) {
                //将数据存入redis
                redisTemplate.opsForValue().set(redisKey, result, ttlMinutes, TimeUnit.MINUTES);
            }
            return result;
        }
    }

    public <T> T cacheRound(String redisKey, RealDataGetter<T> getter) {
        return cacheRound(redisKey, getCacheExpireMinutes(redisKey), getter);
    }

    /**
     * redis 缓存环绕增强
     *
     * @param name   缓存名称 用于取缓存过期时间
     * @param key    缓存名称后跟随的唯一表示 ,如 SYSTEM_USER_TREE::test 的test即为key
     * @param getter 数据获取
     * @param <T>    类型
     * @return 需要的数据
     */
    public <T> T cacheRoundPlus(String name, String key, RealDataGetter<T> getter) {
        String redisKey = PudgeUtil.generateKeyWithDoubleColon(name, key);
        long ttl = getCacheExpireMinutes(name);
        return cacheRound(redisKey, ttl, getter);
    }

    /**
     * 设置缓存
     *
     * @param redisKey 缓存key
     * @param name     缓存名称 用于取缓存过期时间
     * @param getter   数据获取
     * @param <T>      类型
     * @return 需要的数据
     */
    public <T> T setCache(String redisKey, String name, RealDataGetter<T> getter) {
        T result = null;
        if (getter != null) {
            result = getter.get();
            if (result != null) {
                //将数据存入redis
                redisTemplate.opsForValue().set(redisKey, result, getCacheExpireMinutes(name), TimeUnit.MINUTES);
            }
        }
        return result;
    }

    /**
     * 设置缓存
     *
     * @param name   缓存名称 用于取缓存过期时间
     * @param key    缓存名称后跟随的唯一表示 ,如 SYSTEM_USER_TREE::test 的test即为key
     * @param getter 数据获取
     * @param <T>    类型
     * @return 需要的数据
     */
    public <T> T setCachePlus(String name, String key, RealDataGetter<T> getter) {
        String redisKey = PudgeUtil.generateKeyWithDoubleColon(name, key);
        return setCache(redisKey, name, getter);
    }

    /**
     * 获取缓存,处理完成后移除缓存
     *
     * @param redisKey redis key
     * @param handler  数据处理
     * @param <T>      缓存类型
     * @return 缓存处理后的数据
     */
    @SuppressWarnings("unchecked")
    public <T> T getCacheAndDelete(String redisKey, CacheHandler<T> handler) {
        T result = (T) redisTemplate.opsForValue().get(redisKey);
        if (handler != null) {
            handler.handle(result);
        }
        redisTemplate.delete(redisKey);
        return result;
    }

    /**
     * 获取缓存,处理完成后移除缓存
     *
     * @param name    缓存名称 用于取缓存过期时间
     * @param key     缓存名称后跟随的唯一表示 ,如 SYSTEM_USER_TREE::test 的test即为key
     * @param handler 缓存处理
     * @param <T>     缓存类型
     * @return 处理后的缓存数据
     */
    public <T> T getCacheAndDeletePlus(String name, String key, CacheHandler<T> handler) {
        String redisKey = PudgeUtil.generateKeyWithDoubleColon(name, key);
        return getCacheAndDelete(redisKey, handler);
    }

    /**
     * 获取缓存
     *
     * @param name 缓存名称 用于取缓存过期时间
     * @param key  缓存名称后跟随的唯一表示 ,如 SYSTEM_USER_TREE::test 的test即为key
     * @param <T>  缓存数据类型
     * @return 缓存
     */
    public <T> T getCachePlus(String name, String key) {
        String redisKey = PudgeUtil.generateKeyWithDoubleColon(name, key);
        return getCache(redisKey);
    }

    /**
     * 是否存在指定缓存
     *
     * @param name 缓存名称 用于取缓存过期时间
     * @param key  缓存名称后跟随的唯一表示 ,如 SYSTEM_USER_TREE::test 的test即为key
     * @return boolean 是否存在
     */
    public boolean haveExistPlus(String name, String key) {
        String redisKey = PudgeUtil.generateKeyWithDoubleColon(name, key);
        Boolean has = redisTemplate.hasKey(redisKey);
        return has != null && has;
    }

    /**
     * 获取指定key的过期时间
     *
     * @param name 缓存名称 用于取缓存过期时间
     * @param key  缓存名称后跟随的唯一表示 ,如 SYSTEM_USER_TREE::test 的test即为key
     * @return 过期时间 单位s
     */
    public Long getExpireTimePlus(String name, String key) {
        String redisKey = PudgeUtil.generateKeyWithDoubleColon(name, key);
        return redisTemplate.getExpire(redisKey);
    }

    /**
     * 获取指定key的过期时间
     *
     * @param redisKey 缓存key
     * @return 过期时间 单位s
     */
    public Long getExpireTime(String redisKey) {
        return redisTemplate.getExpire(redisKey);
    }

    /**
     * 获取缓存
     *
     * @param redisKey 缓存key
     * @param <T>      缓存数据类型
     * @return 缓存数据
     */
    @SuppressWarnings("unchecked")
    public <T> T getCache(String redisKey) {
        return (T) redisTemplate.opsForValue().get(redisKey);
    }

    /**
     * 删除缓存
     *
     * @param redisKey 缓存key
     * @return 成功失败
     */
    public Boolean deleteCache(String redisKey) {
        return redisTemplate.delete(redisKey);
    }

    /**
     * 删除缓存
     *
     * @param name 缓存名称
     * @param key  缓存名称后跟随的唯一表示 ,如 SYSTEM_USER_TREE::test 的test即为key
     * @return 成功失败
     */
    public Boolean deleteCachePlus(String name, String key) {
        String redisKey = PudgeUtil.generateKeyWithDoubleColon(name, key);
        return this.deleteCache(redisKey);
    }

    /**
     * 批量删除指定缓存名称的所有key
     *
     * @param name 缓存名称
     */
    public void batchDelete(String name) {
        String keyPattern = PudgeUtil.generateKeyWithDoubleColon(name, "*");
        Set<String> keys = redisTemplate.keys(keyPattern);
        if (CollectionUtil.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    public void batchDelete(String name, List<String> keys) {
        if (CollectionUtil.isNotEmpty(keys)) {
            Set<String> redisKeys = keys.stream().map(s -> PudgeUtil.generateKeyWithDoubleColon(name, s)).collect(Collectors.toSet());
            redisTemplate.delete(redisKeys);
        }
    }

    private void put(String name, Duration ttl, boolean annotation) {
        long minutes = ttl.toMinutes();
        if (minutes <= 0) {
            minutes = -1;
        }
        CONFIG_MAP.put(name, new CustomCacheConfig(name, minutes, annotation));
    }

    private long getCacheExpireMinutes(String name) {
        CustomCacheConfig config = CONFIG_MAP.get(name);
        return config != null ? config.getMinutes() : DEFAULT_EXPIRE_MINUTES;
    }

    /**
     * @author yq
     * @date 2020/10/15 17:51
     * @description 自定义缓存名称
     * @since V1.0.0
     */
    @Data
    @AllArgsConstructor
    public class CustomCacheConfig {
        /**
         * cache name
         */
        private String name;
        /**
         * 缓存过期时间 分钟
         */
        private Long minutes;
        /**
         * 是否注解, true 表示由spring cache注解类自动设置, false,表示手动填写的过期时间
         */
        private Boolean annotation;
    }

    /**
     * 实际数据获取
     *
     * @param <T> 获取的数据类型
     */
    public interface RealDataGetter<T> {
        /**
         * 调用接口或sql获取实际数据
         *
         * @return 实际数据
         */
        T get();
    }

    /**
     * 获取缓存后的处理
     */
    public interface CacheHandler<T> {
        /**
         * 处理数据
         *
         * @param t 缓存数据
         */
        void handle(T t);
    }
}
