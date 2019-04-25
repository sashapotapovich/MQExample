package com.example.jms;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import javax.jms.JMSException;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.jms")
public class JmsConfiguration {

    private String host;
    private int port;
    private String userID;
    private String password;
    private String queueManager;
    private String channel;

    @Bean
    public JmsConnectionFactory jmsConnectionFactory() throws JMSException {
        JmsConnectionFactory jmsConnectionFactory = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER).createConnectionFactory();
        jmsConnectionFactory.setStringProperty(WMQConstants.WMQ_HOST_NAME, host);
        jmsConnectionFactory.setIntProperty(WMQConstants.WMQ_PORT, port);
        jmsConnectionFactory.setStringProperty(WMQConstants.USERID, userID);
        jmsConnectionFactory.setStringProperty(WMQConstants.PASSWORD, password);
        jmsConnectionFactory.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, queueManager);
        jmsConnectionFactory.setStringProperty(WMQConstants.WMQ_CHANNEL, channel);
        jmsConnectionFactory.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
        return jmsConnectionFactory;
    }


}
