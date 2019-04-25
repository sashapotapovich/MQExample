package com.example.jms;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(scopeName = SCOPE_PROTOTYPE)
public final class MessageBrokerEndpoint {
    private final Connection connection;
    private final Session session;
    private Destination destination;

    @Autowired
    public MessageBrokerEndpoint(JmsConnectionFactory connectionFactory) throws JMSException {
        this.connection = connectionFactory.createConnection();
        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    
    public void setDestination(String queueName) throws JMSException {
        this.destination = session.createQueue("queue:///" + queueName);
    }


    public Connection getConnection() {
        return connection;
    }

    public Session getSession() {
        return session;
    }

    public Destination getDestination() {
        return destination;
    }
}
