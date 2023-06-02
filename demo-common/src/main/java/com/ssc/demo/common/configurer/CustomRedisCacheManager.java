package com.ssc.demo.common.configurer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;

/**
 * 类说明 （必填）
 *
 * @author Administrator
 * @version v1.0
 * @date 2021/10/26 16:35
 * @change 2021/10/26 16:35 Administrator@v1.0 创建
 */
public class CustomRedisCacheManager extends RedisCacheManager {
    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    /*
     * @description 重写父类createRedisCache方法解析表达式@Cacheable(value = "e_cer#60")去#号后面数字设置为过期时间默认单位分钟
     **/
    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        //名称中存在#标记进行到期时间配置
        if (name.contains("#")) {
            String[] spel = name.split("#");
            if (StringUtils.isNumeric(spel[1])) {
                //配置缓存到期时间
                int cycle = Integer.parseInt(spel[1]);
                return super.createRedisCache(spel[0], cacheConfig.entryTtl(Duration.ofSeconds(cycle)));
            }
        }
        return super.createRedisCache(name, cacheConfig);
    }

}
