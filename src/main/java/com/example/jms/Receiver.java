package com.example.jms;

import com.example.email.EmailService;
import com.example.email.EmailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Receiver {

    private EmailService emailService;
    
    public Receiver(EmailServiceImpl emailService){
        this.emailService = emailService;
    }
    
    @JmsListener(destination = "DEV.QUEUE.1")
    public void receiveMessage(String message) {
        log.info("Message from the queue DEV.QUEUE.1 received, sending a email");
        emailService.sendSimpleMessage("sci@sci.comn", "Message Received", message);
    }

}