package com.tx.txspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author Eric Cui
 * @since 2018/3/15 19:00
 *     <p>
 */
@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    private final Jedis jedis;

    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate, Jedis jedis) {
        this.redisTemplate = redisTemplate;
        this.jedis = jedis;
    }

    public Object redisOperation(final String param) {
        List<RedisClientInfo> redisClientInfos = redisTemplate.getClientList();

        // Client client = jedis.getClient();
        jedis.set("id", "value", "NX", "PX", 1000L);
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String val = jedis.get("id");
        System.out.println(val);

        return redisClientInfos;
    }
}
