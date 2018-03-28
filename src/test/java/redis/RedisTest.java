package redis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Intellij IDEA.
 *
 * @author Eric Cui
 * @since 2018/3/15 23:58
 * <p>
 */
@RunWith(JUnit4.class)
public class RedisTest {

    // seconds
    private final long EX_HOUR = 60 * 60L;
    // millseconds
    private final long PX_HOUR = 60 * 60 * 1000L;
    // channel
    private final String channel = "redis.test.pubsub";

    private Jedis jedis;

    @Before
    public void init() {
        this.jedis = new Jedis();
    }

    @Test
    public void redisTest() {
        String result = jedis.set("language", "Java", "NX", "PX", 3000L);
        System.out.println(result);
    }

    @Test
    public void redisDBSize() {
        System.out.println(jedis.get("340712"));
        System.out.println(jedis.dbSize());
        // jedis.flushAll();
        // jedis.getDB();
        // jedis.flushDB();
    }

    public void write() {
        long start = System.currentTimeMillis();
        String key = "";
        for (int i = 0; i < 1000 * 10000; i++) {
            // key = String.valueOf(i);
            key = i + "";
            jedis.set(key, key, "NX", "EX", 3 * EX_HOUR);
        }
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(key);
    }

    @Test
    public void pipelineWrite() {
        long start = System.currentTimeMillis();
        String key = "";
        Pipeline pipeline = jedis.pipelined();
        for (int i = 0; i < 1000 * 10000; i++) {
            key = i + "";
            pipeline.set(key, key, "NX", "EX", (int) EX_HOUR);
        }
        long split = System.currentTimeMillis();
        System.out.println("pipeline set : " + (split - start));
        Response<String> response = pipeline.multi();
        try {
            pipeline.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Pipeline close Error.");
        }
        System.out.println(response.get());
        System.out.println("pipeline multi: " + (System.currentTimeMillis() - split));
        System.out.println(key);
    }

    @Test
    public void redisPubSub() {
        /*new Thread(() -> jedis.psubscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                super.onMessage(channel, message);
                System.out.println(String.format("channel:%s,message:%s", channel, message));
            }
        }, channel)).start();*/
        JedisPubSub jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println(String.format("channel:[%s], message:[%s]", channel, message));
            }

            @Override
            public void subscribe(String... channels) {
                System.out.println(Arrays.toString(channels));
            }
        };

        // Client client = jedis.getClient();
        // jedisPubSub.proceed(client, channel);
        // jedisPubSub.subscribe(channel);
        jedis.subscribe(jedisPubSub, channel);
        // new Thread(() -> jedis.subscribe(jedisPubSub, channel)).start();
    }

    @Test
    public void redisSub() {
        Jedis jSubscriber = new Jedis();
        jSubscriber.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println(String.format("channel:[%s], message:[%s]", channel, message));
            }
        }, channel);
    }

    @Test
    public void redisPub() {
        Jedis jPublisher = new Jedis();
        jPublisher.publish(channel, "test message");

        jedis.publish(channel, "test message");
    }

    @Test
    public void hset() {
        Long hdel = jedis.hdel("person:1", "name");
        System.out.println(hdel);

        Long hset = jedis.hset("person:1", "name", "Eric");
        System.out.println(hset);

        String hget = jedis.hget("person:1", "name");
        System.out.println(hget);
    }
}
