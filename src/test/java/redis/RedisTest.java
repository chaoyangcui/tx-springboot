package redis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by Intellij IDEA.
 *
 * @author Eric Cui
 * @since 2018/3/15 23:58
 * <p>
 */
@RunWith(JUnit4.class)
public class RedisTest {

    private Jedis jedis;

    @Before
    public void init() {
        this.jedis = new Jedis();
    }

    @Test
    public void redisTest() {
    }
}
