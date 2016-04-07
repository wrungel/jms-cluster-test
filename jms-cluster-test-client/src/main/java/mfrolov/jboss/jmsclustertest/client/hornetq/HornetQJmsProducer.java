package mfrolov.jboss.jmsclustertest.client.hornetq;

import mfrolov.jboss.jmsclustertest.client.MessageGroupingSender;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class HornetQJmsProducer {

	private String user;
	private String pass;

	private ConnectionFactory connectionFactory;
	private Queue queue;

	public static void main(String[] args) throws Exception {

		HornetQJmsProducer sender;

		switch (args.length) {
		case 4:
			sender = new HornetQJmsProducer(args[0], args[1], args[2], args[3]);
			break;
		case 2:
			sender = new HornetQJmsProducer(args[0], args[1]);
			break;
		default:
			sender = new HornetQJmsProducer();
		}

		sender.sendTextMessages("Message number ");
	}

	public HornetQJmsProducer() throws Exception {
		this(null, null);
	}

	public HornetQJmsProducer(String ip, String port) throws Exception {
		this(ip, port, "admin", "secret");
	}

	public HornetQJmsProducer(String ip, String port, String user, String pass)
			throws Exception {
		this.user = user;
		this.pass = pass;
		HornetQJndiLookup jndiLookup = new HornetQJndiLookup(ip, port, user, pass);
		connectionFactory = jndiLookup.lookup(ConnectionFactory.class,
				"jms/RemoteConnectionFactory");
		queue = jndiLookup.lookup(Queue.class, "jms/queue/test");
	}

	private void sendTextMessages(final String text) throws Exception {
		Connection connection = null;
		Session session = null;
		MessageProducer messageProducer = null;
		try {
			if (user != null && pass != null) {
				connection = connectionFactory.createConnection(user, pass);
			} else {
				connection = connectionFactory.createConnection();
			}
			
			connection.setExceptionListener(new ExceptionListener() {
				
				@Override
				public void onException(JMSException exception) {
					exception.printStackTrace();
					
				}
			});
		
			
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			messageProducer = session.createProducer(queue);
			connection.start();
			TextMessage message = session.createTextMessage();

            new MessageGroupingSender().send(messageProducer, message);

		} finally {
			try {
				if (messageProducer != null)
					messageProducer.close();
				if (connection != null)
					connection.close();
				if (session != null)
					session.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
