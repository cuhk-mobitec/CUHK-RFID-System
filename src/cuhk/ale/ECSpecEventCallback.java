/**
 * $Id: ECSpecEventCallback.java,v 1.1 2006/06/16 16:49:18 alfred Exp $
 */
package cuhk.ale;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

// import cuhk.epcglobal.ExceptionSeverity;
import cuhk.ale.exceptions.ImplementationException;
import cuhk.ale.exceptions.ImplementationExceptionSeverity;

/**
 * @version $Id: ECSpecEventCallback.java,v 1.1 2006/06/16 16:49:18 alfred Exp $
 * @author kyyu
 * 
 * This class is mainly used for implementing the ALE service Bean's immediate and poll method .
 * 
 */
public class ECSpecEventCallback {

	static final Logger logger = Logger.getLogger(ECSpecEventCallback.class.getName());


	/**
	 * @return
	 * @throws ImplementationException
	 */
	/**
	 * @param selectorKey
	 * @param selectorValue
	 * @return
	 * @throws ImplementationException
	 */
	public static Serializable recvMessage(String selectorKey, String selectorValue)
			throws ImplementationException {

		ECSpecEventCallback.logger.info("recvMessage");

		TopicConnection topicConnection = null;
		String selector = selectorKey + "='" + selectorValue + "'";
		try {
			InitialContext jndiContext = new InitialContext();
			TopicConnectionFactory tcf = (TopicConnectionFactory) jndiContext.lookup("ConnectionFactory");
			Topic topic = (Topic) jndiContext.lookup("topic/EcspecEventCycleCallback");
			topicConnection = tcf.createTopicConnection();
			TopicSession topicSession = topicConnection.createTopicSession(false,
					Session.AUTO_ACKNOWLEDGE);
			TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic, selector, false);

			topicConnection.start();

			Message msg = null;
			while (msg == null || ! (msg instanceof ObjectMessage) ) {
				msg = topicSubscriber.receive();
				ECSpecEventCallback.logger.info("recvMessage ... ");
			}
			
			ECSpecEventCallback.logger.info("recvMessage ---> msg received.");
			return ((ObjectMessage) msg).getObject();
			
		} catch (NamingException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (JMSException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} finally {
			if (topicConnection != null) {
				try {
					topicConnection.close();
				} catch (JMSException e) {
				}
			}
		}
	}

	/**
	 * @param text
	 * @param selectorKey
	 * @param selectorValue
	 * @return
	 * @throws ImplementationException
	 */
	public static boolean sendMessage(Serializable obj, String selectorKey, String selectorValue)
			throws ImplementationException {

		TopicConnection topicConnection = null;
		
		ECSpecEventCallback.logger.info("sendMessage...");

		try {
			InitialContext jndiContext = new InitialContext();
			TopicConnectionFactory tcf = (TopicConnectionFactory) jndiContext.lookup("ConnectionFactory");
			Topic topic = (Topic) jndiContext.lookup("topic/EcspecEventCycleCallback");
			topicConnection = tcf.createTopicConnection();
			TopicSession topicSession = topicConnection.createTopicSession(false,
					Session.AUTO_ACKNOWLEDGE);
			TopicPublisher topicPublisher = topicSession.createPublisher(topic);

			ObjectMessage message = topicSession.createObjectMessage(obj);
			message.setStringProperty(selectorKey, selectorValue);
			
			topicPublisher.publish(message);

			return true;

		} catch (NamingException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (JMSException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} finally {
			if (topicConnection != null) {
				try {
					topicConnection.close();
				} catch (JMSException e) {
				}
			}

		}

	}

}
