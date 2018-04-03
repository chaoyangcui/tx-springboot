package com.tx.txspringboot;

import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Eric
 * @since 2018/4/3 10:15
 */
@Configuration
public class ZooConfig {

    @Bean
    public ZooKeeper zooKeeper() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 300, null);
        return zooKeeper;
    }
}
