package mfrolov.jboss.jmsclustertest.client.hornetq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;


public class HornetQJmsReceiver implements MessageListener {

	private ConnectionFactory connectionFactory;

	private Queue queue;
	
	private String user;
	private String pass;

	public static void main(String[] args) throws Exception {

		HornetQJmsReceiver reciever;
		
		switch (args.length) {
		case 4:
			reciever = new HornetQJmsReceiver(args[0], args[1], args[2], args[3]);
			break;
		case 2:
			reciever = new HornetQJmsReceiver(args[0], args[1]);
			break;
		default:
			reciever = new HornetQJmsReceiver();
		}
		
		reciever.consum();

		while (true) {
			Thread.sleep(1000);
		}

	}

	public HornetQJmsReceiver() throws Exception {
		this(null, null);
	}
	
	public HornetQJmsReceiver(String ip, String port) throws Exception {
		this(ip, port, "admin", "secret");
	}

	public HornetQJmsReceiver(String ip, String port, String user, String pass) throws Exception {
		this.user = user;
		this.pass = pass;
		HornetQJndiLookup jndiLookup = new HornetQJndiLookup(ip, port, user, pass);
		connectionFactory = jndiLookup.lookup(ConnectionFactory.class,
				"jms/RemoteConnectionFactory");
		queue = jndiLookup.lookup(Queue.class, "jms/queue/test");
	}

	private void consum() throws Exception {
		final Connection connection;;

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
		
		final Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);

		final MessageConsumer consumer = session.createConsumer(queue);

		consumer.setMessageListener(this);

		connection.start();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					consumer.close();
					session.close();
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void onMessage(Message message) {

		if (message instanceof TextMessage) {
			try {
				System.out.println("Received: "
						+ ((TextMessage) message).getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Received: " + message);
		}
	}

}
