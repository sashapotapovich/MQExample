package com.example.jms;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;


public class MessageBrokerEndpointFactory {

    public static MessageBrokerEndpoint getInstance(MessageBrokerEndpointFactory.Acknowledge acknowledge) throws JMSException {
        return getInstanceforQueue("DEV.QUEUE.1", acknowledge);
    }

    private static MessageBrokerEndpoint getInstanceforQueue(String queueName , MessageBrokerEndpointFactory.Acknowledge acknowledge) throws JMSException {
        JmsConnectionFactory jmsConnectionFactory = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER).createConnectionFactory();
        jmsConnectionFactory.setStringProperty(WMQConstants.WMQ_HOST_NAME, "localhost");
        jmsConnectionFactory.setIntProperty(WMQConstants.WMQ_PORT, 1414);
        jmsConnectionFactory.setStringProperty(WMQConstants.USERID, "sci");
        jmsConnectionFactory.setStringProperty(WMQConstants.PASSWORD, "sci");
        jmsConnectionFactory.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, "QM1");
        jmsConnectionFactory.setStringProperty(WMQConstants.WMQ_CHANNEL, "PASSWORD.SVRCONN");
        jmsConnectionFactory.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
        Connection connection = jmsConnectionFactory.createConnection();
        Session session = connection.createSession(false, acknowledge.value);
        Destination destination = session.createQueue("queue:///" + queueName);
        return new MessageBrokerEndpoint(connection, session, destination);
    }

    public enum Acknowledge {
        AUTOMATIC(Session.AUTO_ACKNOWLEDGE),
        MANUAL(Session.CLIENT_ACKNOWLEDGE);

        int value;

        Acknowledge(int value) {
            this.value = value;
        }
    }
}
