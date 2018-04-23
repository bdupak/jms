package com.epam;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.log4j.Logger;

import javax.jms.*;

public abstract class JmsAbstract implements Jms {

    protected static final Logger LOG = Logger.getLogger(ActiveMqQueue.class);
    protected static final String URL_BROKER = "tcp://localhost:61616";
    public static final String QUEUE_JMS_TEST = "test";
    public static final String TOPIC_JMS_TEST = "testTopic";

    protected String payload;
    protected ConnectionFactory connectionFactory;
    protected Connection connection;
    protected Session session;
    protected Destination destination;
    protected Message message;
    protected MessageProducer producer;
    protected MessageConsumer consumer;

    public JmsAbstract() {
        this.payload = "";
    }

    @Override
    public void connection(String name) throws Exception {
        LOG.info("ActiveMQ -> queue: " + name + ", url broker: " + URL_BROKER);
        connectionFactory = new ActiveMQConnectionFactory(URL_BROKER);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        if (this instanceof ActiveMqQueue) {
            destination = new ActiveMQQueue(name);
        } else if (this instanceof ActiveMqTopic) {
            destination = new ActiveMQTopic(name);
        }
    }

    @Override
    public void close() throws Exception {
        LOG.info("ActiveMQ -> close connection");
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
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.send(message);
    }

    @Override
    public void read() throws Exception {
        LOG.info("ActiveMQ -> read message: ");
        consumer = session.createConsumer(destination);
        connection.start();
        TextMessage textMessage;
        while (true) {
            textMessage = (TextMessage) consumer.receive();
            LOG.info("ActiveMQ -> received: " + textMessage.getText());
            if (textMessage.getText().equals("END")) {
                break;
            }
        }
    }
}
