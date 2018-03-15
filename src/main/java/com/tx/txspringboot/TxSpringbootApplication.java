package com.tx.txspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SpringBootApplication
@Import({WebConfig.class})
public class TxSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxSpringbootApplication.class, args);
    }

    @Bean
    Jedis jedis() {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
        return pool.getResource();
        // return new Jedis("localhost", 6379);
    }
}
