package com.epam;


public class App {
    public static void main( String[] args ) throws Exception {
        send();
        read();
    }

    private static void send() throws Exception {
        ActiveMq activeMq = new ActiveMq();
        activeMq.connection(ActiveMq.QUEUE_JMS_TEST);
        activeMq.send("Hello people");
        activeMq.send("END");
        activeMq.close();
    }

    private static void read() throws Exception {
        ActiveMq activeMq = new ActiveMq();
        activeMq.connection(ActiveMq.QUEUE_JMS_TEST);
        activeMq.read();
        activeMq.close();
    }

}
