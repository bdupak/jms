package com.epam;


public class Main {

    private static Object object = new Object();

    public static void main( String[] args ) throws Exception {

        Thread threadRead = new Thread(()->{
            try {
                read();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread threadTwoRead = new Thread(()->{
            try {
                read();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread threadThreeRead = new Thread(()->{
            try {
                read();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread threadSend = new Thread(() -> {
            try {
                send();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threadThreeRead.start();
        threadRead.start();
        threadTwoRead.start();
        threadSend.start();
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
    }

}
