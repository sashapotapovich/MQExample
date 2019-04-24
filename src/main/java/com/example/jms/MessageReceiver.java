package com.example.jms;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MessageReceiver implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

    private final MessageBrokerEndpoint endpoint;
    private final MessageConsumer consumer;

    public MessageReceiver(MessageBrokerEndpoint endpoint, MessageConsumer consumer) {
        this.endpoint = endpoint;
        this.consumer = consumer;
    }

    public MessageReceiver(MessageBrokerEndpoint endpoint) throws JMSException {
        this(endpoint,
             endpoint.getSession().createConsumer(endpoint.getDestination())
        );
    }

    public Message receive() throws JMSException, IllegalStateException {
        endpoint.getConnection().start();
        return consumer.receive();
    }

    public Object receive(long timeout) throws JMSException, IllegalStateException {
        LOGGER.info("Trying to receive message");
        endpoint.getConnection().start();
        CompletableFuture future = CompletableFuture.supplyAsync(() -> WrappedRuntimeException.get(this::receive));
        Optional<Object> message = Optional.empty();
        try {
            message = Optional.of(future.get(timeout, TimeUnit.SECONDS));
        } catch (InterruptedException | TimeoutException | ExecutionException ex) {
            LOGGER.error("{}", ex);
        }
        LOGGER.info("Message received");
        return message.orElseThrow(() -> new AssertionError("Message was not received"));
    }

    @Override
    public void close() {
        try {
            Stream.of(consumer, endpoint.getSession(), endpoint.getConnection())
                  .forEachOrdered(closeable -> {
                      try {
                          closeable.close();
                      } catch (Exception ex) {
                          LOGGER.warn("{}", ex);
                      }
                  });
        } catch (Exception ex) {
            LOGGER.warn("{}", ex);
        }
    }
}
