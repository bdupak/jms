package com.epam;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.Logger;

import javax.jms.*;

public class ActiveMq implements JmsAbstract {

    private static final Logger LOG = Logger.getLogger(ActiveMq.class);

    private static final String URL_BROKER = "tcp://localhost:61616";
    public static final String QUEUE_JMS_TEST = "test";

    private String payload;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Queue queue;
    private Message message;
    private MessageProducer producer;
    MessageConsumer consumer;

    public ActiveMq() {
        this.payload = "";
    }

    @Override
    public void connection(String queueName) throws Exception {
        LOG.info("ActiveMQ -> queue: " + queueName + ", url broker: " + URL_BROKER);
        connectionFactory = new ActiveMQConnectionFactory(URL_BROKER);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = new ActiveMQQueue(queueName);
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
        producer = session.createProducer(queue);
        producer.send(message);
    }

    public void read() throws JMSException {
        LOG.info("ActiveMQ -> read message: ");
        consumer = session.createConsumer(queue);
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
