package cuhk.ale;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.NamingException;

import cuhk.ale.ejb.interfaces.NotifierUtil;
import epcglobal.ale.*;


public class NotifyRequest implements Serializable {

	private static final long serialVersionUID = -5079072885369878299L;
	private ECReports reports;
	private List<URI> notificationURIs;

	public NotifyRequest(ECReports reports, List<URI> notificationURIs) {
		super();
		this.reports = reports;
		this.notificationURIs = notificationURIs;
	}

	public List<URI> getNotificationURIs() {
		return notificationURIs;
	}
	
	public void setNotificationURIs(List<URI> notificationURIs) {
		this.notificationURIs = notificationURIs;
	}
	
	public ECReports getReports() {
		return reports;
	}
	
	public void setReports(ECReports reports) {
		this.reports = reports;
	}
	
	public void sendMessage() throws NamingException, JMSException{
		QueueConnection queueConnection = NotifierUtil.getQueueConnection();
		QueueSession session = queueConnection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
		QueueSender sender = session.createSender(NotifierUtil.getQueue());
		ObjectMessage message = session.createObjectMessage((Serializable) this);
		message.setJMSExpiration(60000);
		sender.send(message);
		session.commit();
		session.close();
		queueConnection.close();
	}
}
