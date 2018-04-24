package com.epam.spring;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class MessageReceiver {

    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public String readMessage() throws JMSException {
        return ((TextMessage) jmsTemplate.receive()).getText();
    }
}
