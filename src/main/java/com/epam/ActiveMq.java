package com.epam;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.Logger;

import javax.jms.*;

public class ActiveMq implements JmsAbstract {

    private static final Logger LOG = Logger.getLogger(ActiveMq.class);

    private static final String URL_BROKER = "tcp://0.0.0.0:61616";
    public static final String QUEUE_JMS_TEST = "test";

    private String payload;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private Message message;
    private MessageProducer producer;

    public ActiveMq() {
        this.payload = "";
    }

    @Override
    public void connection(String queueName) throws Exception {
        LOG.info(" ActiveMQ -> queue: " + queueName + ", url broker: " + URL_BROKER);
        connectionFactory = new ActiveMQConnectionFactory(URL_BROKER);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = new ActiveMQQueue(queueName);
    }

    @Override
    public void close() throws Exception {
        LOG.info(" ActiveMQ -> close connection");
        if (session != null) {
            session.close();
        }

        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public void send(String payload) throws Exception {
        LOG.info("ActiveMQ -> payload: " + payload);
        this.payload = payload;
        message = session.createTextMessage(payload);
        producer = session.createProducer(destination);
        producer.send(message);
    }
}
