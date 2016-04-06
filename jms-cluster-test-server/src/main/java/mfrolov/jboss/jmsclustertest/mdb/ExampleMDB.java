package mfrolov.jboss.jmsclustertest.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/test")})
public class ExampleMDB implements MessageListener {

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				System.out.println("----------------");
				System.out.println("Received: "
						+ ((TextMessage) message).getText());
				System.out.println("----------------");
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("----------------");
			System.out.println("Received: " + message);
			System.out.println("----------------");
		}
	}

}
