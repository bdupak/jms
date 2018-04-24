package com.epam.spring;

import org.springframework.jms.core.JmsTemplate;

public class MessageSender {

    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(String message) {
        jmsTemplate.send(session -> session.createTextMessage(message));
    }
}
