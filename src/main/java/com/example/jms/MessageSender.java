package com.example.jms;

import java.util.stream.Stream;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class MessageSender implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

    private final MessageBrokerEndpoint endpoint;
    private final MessageProducer producer;


    public MessageSender(MessageBrokerEndpoint endpoint, MessageProducer producer) {
        this.endpoint = endpoint;
        this.producer = producer;
    }

    public MessageSender(MessageBrokerEndpoint endpoint) throws JMSException {
        this(endpoint,
             endpoint.getSession().createProducer(endpoint.getDestination())
        );
    }

    public Message send(String string) throws JMSException, IllegalStateException {
        LOGGER.info("Start sending message");
        endpoint.getConnection().start();
        TextMessage message = endpoint.getSession().createTextMessage();
        if (string != null) {
            message.setText(string);
        }
        producer.send(message);
        LOGGER.info("Message sent");
        return message;
    }

    @Override
    public void close() {
        Stream.of(producer, endpoint.getSession(), endpoint.getConnection())
              .forEachOrdered(closeable -> {
                  try {
                      closeable.close();
                  } catch (Exception ex) {
                      LOGGER.warn("{}", ex);
                  }
              });
    }

}
