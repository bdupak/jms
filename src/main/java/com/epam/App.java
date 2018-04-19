package com.epam;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        ActiveMq activeMq = new ActiveMq();
        activeMq.connection(ActiveMq.QUEUE_JMS_TEST);
        activeMq.send("Hello people");
        activeMq.close();
    }
}
