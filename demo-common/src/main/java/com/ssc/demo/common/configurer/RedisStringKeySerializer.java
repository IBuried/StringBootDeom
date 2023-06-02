package com.ssc.demo.common.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 类说明 （必填）
 *
 * @author Administrator
 * @version v1.0
 * @date 2021/07/08 14:57
 * @change 2021/07/08 14:57 Administrator@v1.0 创建
 */
@Component
public class RedisStringKeySerializer implements RedisSerializer<String> {
    /**
     * 默认为null
     */
    @Value("${spring.cache.redis.key-prefix:#{null}}")
    private String prefix;

    private final Charset charset = StandardCharsets.UTF_8;

    @Override
    public byte[] serialize(String s) throws SerializationException {
        if (prefix != null && prefix.trim().length() > 0) {
            s = prefix + s;
        }
        return (s == null ? null : s.getBytes(charset));
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        String str = new String(bytes, charset);
        if (prefix != null && prefix.trim().length() > 0) {
            return str.replace(prefix, "");
        }
        return str;
    }
}
