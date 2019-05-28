/**
 * TagDataServiceBean.java
 * $Id: TagWriteActivatorBean.java,v 1.1 2007/01/29 16:14:58 kyyu Exp $
 */

package cuhk.ale.ejb;

import java.rmi.Naming;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import cuhk.ale.TagWriteActivateRequest;

/**
 * @ejb.bean name="TagWriteActivator" display-name="Name for TagWriteActivator"
 *           description="Description for TagWriteActivator"
 *           destination-type="javax.jms.Queue"
 *           acknowledge-mode="Auto-acknowledge"
 *           destination-jndi-name="queue/TagWriteActivatorQueue"
 *           connection-factory-jndi-name="ConnectionFactory"
 * 
 * @jboss.destination-jndi-name name="queue/TagWriteActivatorQueue"
 */
public class TagWriteActivatorBean implements MessageDrivenBean,
		MessageListener {

	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(TagWriteActivatorBean.class
			.getName());

	public void onMessage(Message message) {
		logger.info("MDB got message:" + message);
		try {
			if (message instanceof ObjectMessage) {
				TagWriteActivateRequest tr = (TagWriteActivateRequest) ((ObjectMessage) message)
						.getObject();

				logger.info("MDB got tr's " + tr.getReaderID() + " "
						+ tr.getMethod());

				if (tr.getMethod().equals("read")) {
					cuhk.ale.reader.TagData obj = (cuhk.ale.reader.TagData) Naming
							.lookup("//" + tr.getReaderIP() + "/TAGDATA-SERVER");
					obj.read(tr.getReaderID(), tr.getDSpec().getTagID(), tr
							.getDSpec(), tr.getGuid());
				} else if (tr.getMethod().equals("write")) {
					cuhk.ale.reader.TagData obj = (cuhk.ale.reader.TagData) Naming
							.lookup("//" + tr.getReaderIP() + "/TAGDATA-SERVER");
					obj.write(tr.getReaderID(), tr.getDSpec().getTagID(), tr
							.getDSpec(), tr.getGuid());
				} else {
					logger.info("unknown method:" + tr.getMethod());
				}
			}
		} catch (Exception e) {
			logger.info("Exception ... ", e);
			
		}

	}

	public void ejbRemove() throws EJBException {
	}

	public void setMessageDrivenContext(MessageDrivenContext arg0)
			throws EJBException {

	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 */
	public void ejbCreate() {
	}

}
