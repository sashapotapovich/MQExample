package com.example.jms;

import com.example.email.EmailService;
import javax.jms.JMSException;
import javax.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageReceiverEndpoint {

    private MessageReceiver receiver;
    private EmailService emailService;

    @Autowired
    public MessageReceiverEndpoint(MessageReceiver receiver, EmailService emailService) {
        this.receiver = receiver;
        this.emailService = emailService;
    }

    public void receiveAndSendEmail(String queueName) {
        try {
            Message receive = receiver.receive(queueName, 3);
            String body = receive.getBody(String.class);
            emailService.sendSimpleMessage("IDK@sci.com", "IDK", body);
        } catch (JMSException e) {
            log.error(e.toString());
        }
    }
}
