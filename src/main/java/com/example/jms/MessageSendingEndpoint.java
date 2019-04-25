package com.example.jms;

import javax.jms.JMSException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageSendingEndpoint {

    private MessageSender sender;

    @Autowired
    public MessageSendingEndpoint(MessageSender sender) {
        this.sender = sender;
    }

    public void sendMessage(String queueName, String message) {
        try {
            sender.send(queueName, message);
        } catch (JMSException e) {
            log.error(e.toString());
        }
    }

}
