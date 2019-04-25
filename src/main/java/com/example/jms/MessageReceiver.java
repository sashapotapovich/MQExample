package com.example.jms;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public final class MessageReceiver implements AutoCloseable {

    private final MessageBrokerEndpoint endpoint;
    private MessageConsumer consumer;

    @Autowired
    public MessageReceiver(MessageBrokerEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public Message receive(String queueName, long timeoutSeconds) throws JMSException, IllegalStateException {
        log.info("Trying to receiveAndSendEmail message");
        endpoint.getConnection().start();
        setQueue(queueName);
        consumer = endpoint.getSession().createConsumer(endpoint.getDestination());
        CompletableFuture<Message> future = CompletableFuture.supplyAsync(() -> {
            try {
                return consumer.receive();
            } catch (JMSException e) {
                log.error(e.toString());
            }
            return null;
        });
        Optional<Message> message = Optional.empty();
        try {
            message = Optional.of(future.get(timeoutSeconds, TimeUnit.SECONDS));
        } catch (InterruptedException | TimeoutException | ExecutionException ex) {
            log.error("{}", ex);
        }
        consumer.close();
        return message.orElseThrow(() -> new AssertionError("Message was not received"));
    }

    private void setQueue(String queueName) throws JMSException {
        endpoint.setDestination(queueName);
    }

    @Override
    public void close() {
        try {
            if (consumer != null)
                consumer.close();
            endpoint.getSession().close();
            endpoint.getConnection().close();
        } catch (JMSException ex){
            log.error(ex.toString());
        }
    }
}
