package com.jf.system.socket;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description: Redis Session 序列化
 * User: xujunfei
 * Date: 2018-11-23
 * Time: 10:43
 */
@Component("springSessionDefaultRedisSerializer")
public class SessionSerializer extends GenericJackson2JsonRedisSerializer {

}
