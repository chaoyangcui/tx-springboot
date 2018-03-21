package com.tx.txspringboot.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Date;

/**
 * Created by Intellij IDEA.
 *
 * @author Eric Cui
 * @since 2018/3/18 15:34
 *     <p>
 */
public class SimpleMessageProducer {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleMessageProducer.class);

    @Autowired protected JmsTemplate jmsTemplate;

    protected int numberOfMessages = 100;

    public void sendMessages() throws JMSException {
        StringBuilder payload;
        for (int i = 0; i < numberOfMessages; ++i) {
            payload = new StringBuilder();
            payload.append("Message [").append(i).append("] sent at: ").append(new Date());

            StringBuilder finalPayload = payload;
            int i1 = i;

            jmsTemplate.send(
                    session -> {
                        TextMessage message = session.createTextMessage(finalPayload.toString());
                        message.setIntProperty("messageCount", i1);
                        LOG.info("Sending message number [" + i1 + "]");
                        return message;
                    });
        }
    }
}
