package com.epam.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.JMSException;


public class Main {

    public static void main(String[] args) throws JMSException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");

        MessageSender messageSender = context.getBean(MessageSender.class);

        String message = "Hello, friend";

        messageSender.send(message);
        System.out.println("ActiveMQ + Spring -> send: " + message);

        MessageReceiver messageReceiver = context.getBean(MessageReceiver.class);
        System.out.println("ActiveMQ + Spring -> read: " + messageReceiver.readMessage());

    }
}
