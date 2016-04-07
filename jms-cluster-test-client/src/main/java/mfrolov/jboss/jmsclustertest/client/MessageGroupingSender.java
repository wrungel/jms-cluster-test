package mfrolov.jboss.jmsclustertest.client;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import java.util.Random;

public class MessageGroupingSender {

    Random random = new Random();

    public void send(MessageProducer messageProducer, TextMessage message) throws JMSException, InterruptedException {
        int i = 0;
        while (i < 50) {
            String groupId = (i % 2 == 0) ? "A" : "B";
            message.setStringProperty("JMSXGroupID", groupId);
            String text = String.format("%03d", (i++)/2);
            message.setText(text);
            messageProducer.send(message);
            System.out.println(String.format("sent %s:%s", groupId, text));
            Thread.sleep(random.nextInt(100));
        }

    }
}
