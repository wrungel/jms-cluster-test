package mfrolov.jboss.jmsclustertest.mdb;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.String.format;

public class MessageProcessor implements MessageListener {

    @Override
    public void onMessage(Message message) {
        String jmsxGroupID;
        int randomMillis = ThreadLocalRandom.current().nextInt(1000);

        try {
            jmsxGroupID = message.getStringProperty("JMSXGroupID");
        } catch (JMSException e) {
            jmsxGroupID = "JMSException: " + e.getMessage();
        }
        try {
            String text = ((TextMessage) message).getText();
            System.out.println(format("[S]  %s:%s - sleep %d",
                    jmsxGroupID, text, randomMillis));
            Thread.sleep(randomMillis);
            System.out.println(format("[E]  %s:%s", jmsxGroupID, text));
        } catch (InterruptedException | JMSException  e) {
            e.printStackTrace();
        }
    }
}
