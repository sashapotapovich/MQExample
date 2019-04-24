package com.example.demo;

import com.example.jms.MessageBrokerEndpoint;
import com.example.jms.MessageBrokerEndpointFactory;
import com.example.jms.MessageSender;
import javax.jms.JMSException;


public class DemoApplication {

    public static void main(String[] args) throws JMSException {
        MessageBrokerEndpoint messageBrokerEndpoint = MessageBrokerEndpointFactory.getInstance(MessageBrokerEndpointFactory.Acknowledge.AUTOMATIC);
        MessageSender sender = new MessageSender(messageBrokerEndpoint);
        sender.send("Aloha");
    }

}
