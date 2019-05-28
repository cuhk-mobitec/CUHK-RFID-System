package cuhk.ale;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.NamingException;

import cuhk.ale.ejb.interfaces.TagWriteActivatorUtil;
import cuhk.ale.reader.DataSpec;

public class TagWriteActivateRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	String readerID;
	String readerIP;
	DataSpec dSpec;
	String guid;
	String method;
	
	public TagWriteActivateRequest(String readerID, String readerIP, DataSpec spec, String guid, String method) {
		super();
		this.readerID = readerID;
		this.readerIP = readerIP;
		this.guid = guid;
		this.dSpec = spec;
		this.method = method;
	}


	public void sendMessage() throws NamingException, JMSException{
		QueueConnection queueConnection = TagWriteActivatorUtil.getQueueConnection();
		QueueSession session = queueConnection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
		QueueSender sender = session.createSender(TagWriteActivatorUtil.getQueue());
		ObjectMessage message = session.createObjectMessage((Serializable) this);
		
		sender.send(message);
		session.commit();
		session.close();
		queueConnection.close();
	}


	public DataSpec getDSpec() {
		return dSpec;
	}


	public String getReaderID() {
		return readerID;
	}


	public String getGuid() {
		return guid;
	}


	public String getMethod() {
		return method;
	}


	public String getReaderIP() {
		return readerIP;
	}
}
