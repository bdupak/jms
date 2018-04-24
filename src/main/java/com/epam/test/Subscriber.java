package com.epam.test;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Subscriber {
    // URL of the JMS server
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // Name of the topic from which we will receive messages from = " testt"

    public static void main(String[] args) throws JMSException {
        // Getting JMS connection from the server

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();

        // need to setClientID value, any string value you wish
        connection.setClientID("12345");

        try{
            connection.start();
        }catch(Exception e){
            System.err.println("NOT CONNECTED!!!");
        }
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic("testTopic");

        //need to use createDurableSubscriber() method instead of createConsumer() for topic
        // MessageConsumer consumer = session.createConsumer(topic);
        MessageConsumer consumer = session.createDurableSubscriber(topic,
                "SUB1234");

        MessageListener listner = message -> {
            try {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("Received message"
                            + textMessage.getText() + "'");
                }
            } catch (JMSException e) {
                System.out.println("Caught:" + e);
                e.printStackTrace();
            }
        };

        consumer.setMessageListener(listner);
        //connection.close();

    }
}
