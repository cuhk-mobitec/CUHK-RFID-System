package cuhk.ale.ejb;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.ejb.CreateException;
import cuhk.ale.NotifyRequest;

import java.io.*;
import java.net.*;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.log4j.Logger;

import epcglobal.ale.*;


/**
 * @ejb.bean name="Notifier"
 *           display-name="Name for Notifier"
 *           description="Description for Notifier"
 *           destination-type="javax.jms.Queue"
 *           acknowledge-mode="Auto-acknowledge"
 *           destination-jndi-name="queue/NotifierQueue"
 *           connection-factory-jndi-name="ConnectionFactory"
 *           
 * @jboss.destination-jndi-name name="queue/NotifierQueue"           
 */
public class NotifierBean implements MessageDrivenBean, MessageListener {

	static Logger logger = Logger.getLogger(NotifierBean.class.getName( ));
	
	public NotifierBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setMessageDrivenContext(MessageDrivenContext arg0)
		throws EJBException {
		// TODO Auto-generated method stub

	}

	public void ejbRemove() throws EJBException {
		// TODO Auto-generated method stub

	}

	public void onMessage(Message message) {
		logger.info("MDB got message:" + message);

		try {
			if (message instanceof ObjectMessage) {
				NotifyRequest nr = (NotifyRequest) ((ObjectMessage) message).getObject();
				ECReports reports = nr.getReports();
				
				logger.info("ECReports:" + reports);
				logger.info("Notification URIs:"+nr.getNotificationURIs());

	            // create a JAXBContext
	            JAXBContext jc = JAXBContext.newInstance("epcglobal.ale");				
			
	    	    // create an element for marshalling
	    	    JAXBElement<ECReports> reportsElement = (new ObjectFactory()).createECReports(reports);

                // create a Marshaller and marshal to System.out
                Marshaller m = jc.createMarshaller();
                m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                m.marshal( reportsElement, System.out );

                // perform HTTP Notification
                for (URI notificationURI : nr.getNotificationURIs()) {
                	try {
                    	logger.info("notify ->" + notificationURI);
                    	String scheme = notificationURI.getScheme(); 
                    	
                    	if (scheme.equals("http")) {
                    		// implement the ALE HTTP Notification
    	                	URL url = notificationURI.toURL();
    	                	URLConnection connection = url.openConnection();	
    	                	connection.setDoOutput(true);
    	                	OutputStream out = connection.getOutputStream();
    	                	m.marshal(reportsElement,out);
    	                	out.close();
    	                	if (connection instanceof HttpURLConnection) {
    	                		int code=((HttpURLConnection) connection).getResponseCode();
    	                		logger.info("Response code:" + code);
    	                	}                    		
                    	}
                    	else if (scheme.equals("tcp")) {
                    		// implement the ALE TCP Notification
                            Socket socket = new Socket(notificationURI.getHost(), notificationURI.getPort());
                            OutputStream out = socket.getOutputStream();
    	                	m.marshal(reportsElement,out);
    	                	out.close();
    	                	socket.close();  
                    	}
                    	else if (scheme.equals("file")) {
                    		// implement the FILE Noetification
                    		String path = notificationURI.getPath();
                    		logger.info(path);
                    		
                    		FileOutputStream out = new FileOutputStream(path,true);
                    		m.marshal(reportsElement,out);
                    		out.close();
                    	}
                	}
                	catch(IOException e) {
                		logger.info(e);
                	}
                }
			}
		}
/*		catch( JAXBException je ) {
            je.printStackTrace();
        }*/ 				
		catch(Exception e) {
			logger.info(e);
		}
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 */
	public void ejbCreate() {
		// TODO Auto-generated method stub
	}
}
