package com.epam;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.util.Random;

public class ActiveMqTopic extends JmsAbstract {

    @Override
    public void connection(String name) throws JMSException {
        LOG.info("ActiveMQ -> topic: " + name + ", url broker: " + URL_BROKER);
        connectionFactory = new ActiveMQConnectionFactory(URL_BROKER);
        connection = connectionFactory.createConnection();
        connection.setClientID("12345" + new Random().nextInt(100));
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = new ActiveMQTopic(name);
    }

    @Override
    public void read() throws JMSException {
        LOG.info("ActiveMQ -> read message: ");
        Topic topic = session.createTopic(TOPIC_JMS_TEST);
        consumer = session.createDurableSubscriber(topic, "12345");
        consumer.setMessageListener((message) -> {
            try {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    LOG.info("ActiveMQ -> received: " + textMessage.getText());
                    if (textMessage.getText().equals("END")) {
                        close();
                    }
                }
            } catch (JMSException e) {
                System.out.println("Caught:" + e);
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
