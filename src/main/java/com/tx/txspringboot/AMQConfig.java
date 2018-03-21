package com.tx.txspringboot;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by Intellij IDEA.
 *
 * @author Eric Cui
 * @since 2018/3/18 15:41
 * <p>
 */
@Configuration
public class AMQConfig {

    @Bean
    ActiveMQConnectionFactory activeMQConnectionFactory() {
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    @Bean
    CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(activeMQConnectionFactory());
    }

    @Bean
    ActiveMQQueue destination() {
        return new ActiveMQQueue("FOO.TEST");
    }

    @Bean
    JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory());
        jmsTemplate.setDefaultDestination(destination());
        return jmsTemplate;
    }
}
