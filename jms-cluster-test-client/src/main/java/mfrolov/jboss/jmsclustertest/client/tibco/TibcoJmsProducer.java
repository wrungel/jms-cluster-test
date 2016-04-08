package mfrolov.jboss.jmsclustertest.client.tibco;

import mfrolov.jboss.jmsclustertest.client.MessageGroupingSender;

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

    public void send(String queueName) {
        try {
            Destination queue = getDestination(queueName);
            msgProducer = session.createProducer(queue);
            new MessageGroupingSender(msgProducer, session).send();
        } catch (JMSException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws JMSException {
        TibcoJmsProducer messageSender = new TibcoJmsProducer(TibcoServerConfigurationFactory.localTibcoEms());
        messageSender.send("Queue1");
    }
}
