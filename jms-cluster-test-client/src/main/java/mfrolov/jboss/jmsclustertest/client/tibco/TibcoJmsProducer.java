package mfrolov.jboss.jmsclustertest.client.tibco;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

public class TibcoJmsProducer {

    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer msgProducer;

    private Map<String, Destination> destinationMap;

    public TibcoJmsProducer() throws JMSException {
        this(TibcoServerConfigurationFactory.localTibcoEms());
    }


    public TibcoJmsProducer(TibcoServerConfiguration serverConfiguration) throws JMSException {
        factory = new com.tibco.tibjms.TibjmsConnectionFactory(serverConfiguration.getServerUrl());
        connection = factory.createConnection(serverConfiguration.getUserName(), serverConfiguration.getPassword());
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destinationMap = new HashMap<>();
        msgProducer = session.createProducer(null);
        System.out.println("Setting up JMS connection to " + serverConfiguration.getServerUrl());
    }

    private Destination getDestination(String destinationName) throws JMSException {
        Destination destination = destinationMap.get(destinationName);
        if (destination == null) {
            destination = session.createQueue(destinationName);
            destinationMap.put(destinationName, destination);
        }
        return destination;
    }

    public void sendMessage(String queueName, String messageText) {
        try {
            Destination queue = getDestination(queueName);
            TextMessage msg = session.createTextMessage();
            msg.setText(messageText);
            msgProducer.send(queue, msg);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws JMSException {
        TibcoJmsProducer messageSender = new TibcoJmsProducer(TibcoServerConfigurationFactory.localTibcoEms());
        messageSender.sendMessage("Queue1", "hello from Globe");
    }
}
