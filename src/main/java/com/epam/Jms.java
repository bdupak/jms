package com.epam;

public interface Jms {
    void connection(String queueName) throws Exception;

    void close() throws Exception;

    void send(String payload) throws Exception;

    void read() throws Exception;
}
