package com.example.jms;

import java.util.stream.Stream;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public final class MessageSender implements AutoCloseable {

    private final MessageBrokerEndpoint endpoint;
    private MessageProducer producer;

    @Autowired
    public MessageSender(MessageBrokerEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public Message send(String queueName, String messageString) throws JMSException, IllegalStateException {
        setQueue(queueName);
        producer = endpoint.getSession().createProducer(endpoint.getDestination());
        log.info("Start sending message");
        endpoint.getConnection().start();
        TextMessage message = endpoint.getSession().createTextMessage();
        if (messageString != null) {
            message.setText(messageString);
        }
        producer.send(message);
        log.info("Message sent");
        producer.close();
        return message;
    }

    private void setQueue(String queueName) throws JMSException {
        endpoint.setDestination(queueName);
    }

    @Override
    public void close() {
        try {
            if (producer != null) 
                producer.close();
            endpoint.getSession().close();
            endpoint.getConnection().close();
        } catch (JMSException ex){
            log.error(ex.toString());
        }
    }

}
