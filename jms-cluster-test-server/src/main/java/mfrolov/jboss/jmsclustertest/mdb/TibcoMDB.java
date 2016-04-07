package mfrolov.jboss.jmsclustertest.mdb;

import org.jboss.ejb3.annotation.ResourceAdapter;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "connectionFactory", propertyValue = "java:global/remoteTibcoJMS/XAQCF"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:global/remoteTibcoJMS/Queue1"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
@ResourceAdapter(value="org.jboss.genericjms")
public class TibcoMDB extends MessageProcessor {

}
