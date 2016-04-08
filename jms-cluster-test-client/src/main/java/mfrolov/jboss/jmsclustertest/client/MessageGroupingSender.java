package mfrolov.jboss.jmsclustertest.client;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Random;

/**
 * Sends multiple messages for 4 different JMS groups (<code>JMSXGroupID</code>): <code>{A, B, C, D}</code>.
 */
public class MessageGroupingSender {
    private static final int MESSAGES_PER_GROUP_TO_SEND = 25;
    private static final String[] groups = new String[] {"A", "B", "C", "D"};
    private Random random = new Random();
    private final MessageProducer messageProducer;
    private final Session session;

    public MessageGroupingSender(MessageProducer messageProducer, Session session) {
        this.messageProducer = messageProducer;
        this.session = session;
    }

    public void send() throws JMSException, InterruptedException {
        int i = 0;
        while (i < groups.length * MESSAGES_PER_GROUP_TO_SEND) {
            String groupId = groups[i % groups.length];
            int groupSeq = (i++) / groups.length;
            send(groupId, groupSeq);
        }
    }

    public void sendOutOfOrder() throws InterruptedException, JMSException {
        send("X", 1);
        send("X", 4);
        send("X", 3);
        send("X", 2);
    }

    void send(String groupId, int groupSeq) throws JMSException, InterruptedException {
        TextMessage message = session.createTextMessage();
        message.setStringProperty("JMSXGroupID", groupId);
        message.setIntProperty("JMSXGroupSeq", groupSeq);
        String text = String.format("%02d", groupSeq);
        message.setText(text);
        messageProducer.send(message);
        System.out.println(String.format("sent %s:%02d", groupId, groupSeq));
        Thread.sleep(random.nextInt(100));
    }




}
