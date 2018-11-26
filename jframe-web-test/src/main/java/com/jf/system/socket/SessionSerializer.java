package com.jf.system.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * Description: Redis Session 序列化 [Jackson2]
 * User: xujunfei
 * Date: 2018-11-23
 * Time: 10:43
 */
@Component("springSessionDefaultRedisSerializer")
public class SessionSerializer implements RedisSerializer {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null) {
            return new byte[0];
        }
        return JSON.toJSONString(o, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, Object.class);
    }
}
