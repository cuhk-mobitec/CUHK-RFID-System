/**
 * $Id: TagDataServiceCallback.java,v 1.1 2007/01/29 16:14:58 kyyu Exp $
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
 * @version $Id: TagDataServiceCallback.java,v 1.1 2007/01/29 16:14:58 kyyu Exp $
 * @author kyyu
 * 
 * This class is mainly used for implementing the ALE service Bean's immediate and poll method .
 * 
 */
public class TagDataServiceCallback {

	static final Logger logger = Logger.getLogger(TagDataServiceCallback.class.getName());


	/**
	 * @return
	 * @throws ImplementationException
	 */
	/**
	 * @param selectorKey
	 * @param selectorValue
	 * @param l 
	 * @return
	 * @throws ImplementationException
	 */
	public static Serializable recvMessage(String selectorKey, String selectorValue, long timeout)
			throws ImplementationException {

		TagDataServiceCallback.logger.info("recvMessage");

		TopicConnection topicConnection = null;
		String selector = selectorKey + "='" + selectorValue + "'";
		try {
			InitialContext jndiContext = new InitialContext();
			TopicConnectionFactory tcf = (TopicConnectionFactory) jndiContext.lookup("ConnectionFactory");
			Topic topic = (Topic) jndiContext.lookup("topic/TagDataServiceCallback");
			topicConnection = tcf.createTopicConnection();
			TopicSession topicSession = topicConnection.createTopicSession(false,
					Session.AUTO_ACKNOWLEDGE);
			TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic, selector, false);

			topicConnection.start();

			Message msg = null;
			if (msg == null || ! (msg instanceof ObjectMessage) ) {
				msg = topicSubscriber.receive(timeout);
				TagDataServiceCallback.logger.info("recvMessage ... ");
			}
			
			if (msg == null ) {
				return null;
			}
			
			TagDataServiceCallback.logger.info("recvMessage ---> msg received.");
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
		
		TagDataServiceCallback.logger.info("sendMessage...");

		try {
			InitialContext jndiContext = new InitialContext();
			TopicConnectionFactory tcf = (TopicConnectionFactory) jndiContext.lookup("ConnectionFactory");
			Topic topic = (Topic) jndiContext.lookup("topic/TagDataServiceCallback");
			topicConnection = tcf.createTopicConnection();
			TopicSession topicSession = topicConnection.createTopicSession(false,
					Session.AUTO_ACKNOWLEDGE);
			TopicPublisher topicPublisher = topicSession.createPublisher(topic);

			ObjectMessage message = topicSession.createObjectMessage(obj);
			message.setStringProperty(selectorKey, selectorValue);
			message.setJMSExpiration(60000);
			
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
