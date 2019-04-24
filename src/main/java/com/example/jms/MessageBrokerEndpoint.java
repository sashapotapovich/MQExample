package com.example.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Session;


public final class MessageBrokerEndpoint {
    private final Connection connection;
    private final Session session;
    private final Destination destination;

   
    public MessageBrokerEndpoint(Connection connection, Session session, Destination destination) {
        this.connection = connection;
        this.session = session;
        this.destination = destination;
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
