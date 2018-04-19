package com.epam;

public interface JmsAbstract {
    void connection(String queueName) throws Exception;

    void close() throws Exception;

    void send(String payload) throws Exception;
}
