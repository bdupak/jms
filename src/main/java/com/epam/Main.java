package com.epam;


public class Main {
    public static void main( String[] args ) throws Exception {
        send();
        read();
    }

    private static void send() throws Exception {
        Jms activeMq = new ActiveMqTopic();
        activeMq.connection(ActiveMqQueue.TOPIC_JMS_TEST);
        activeMq.send("Hello people");
        activeMq.send("END");
        activeMq.close();
    }

    private static void read() throws Exception {
        Jms activeMq = new ActiveMqTopic();
        activeMq.connection(ActiveMqQueue.TOPIC_JMS_TEST);
        activeMq.read();
//        activeMq.close();
    }

}
