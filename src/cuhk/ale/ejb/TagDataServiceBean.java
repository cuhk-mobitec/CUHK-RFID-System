/**
 * TagDataServiceBean.java
 * $Id: TagDataServiceBean.java,v 1.2 2007/03/30 16:03:49 kyyu Exp $
 */
package cuhk.ale.ejb;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import cuhk.ale.ECSpecEventCallback;
import cuhk.ale.ECSpecValidator;
import cuhk.ale.ECTag;
import cuhk.ale.NotifyRequest;
import cuhk.ale.TagDataServiceCallback;
import cuhk.ale.TagWriteActivateRequest;
import cuhk.ale.ejb.interfaces.ECSpecInstanceLocalHome;
import cuhk.ale.ejb.interfaces.ECSpecInstanceUtil;
import cuhk.ale.ejb.interfaces.TagDataServiceUtil;
import cuhk.ale.exceptions.DuplicateNameException;
import cuhk.ale.exceptions.ImplementationException;
import cuhk.ale.exceptions.ImplementationExceptionSeverity;
import cuhk.ale.reader.DataResult;
import cuhk.ale.reader.DataSpec;
import epcglobal.ale.ECReports;

/**
 * /**
 * 
 * @version $Id: TagDataServiceBean.java,v 1.2 2007/03/30 16:03:49 kyyu Exp $
 * 
 * @ejb.bean name="TagDataService" view-type = "both" display-name="Name for
 *           TagDataService" description="Description for TagDataService"
 *           type="Stateless" jndi-name = "ejb/TagDataService"
 * 
 * @ejb.dao class="cuhk.ale.dao.TagDataServiceDAO"
 *          impl-class="cuhk.ale.dao.TagDataServiceDAOImpl"
 * 
 * @ejb.resource-ref res-ref-name="mySQLDS" res-type="javax.sql.Datasource"
 *                   res-auth="Container"
 * 
 * @jboss.resource-ref res-ref-name="mySQLDS" jndi-name="java:/mySQLDS"
 */
public abstract class TagDataServiceBean implements SessionBean {

	private static final long TIMEOUT = 30000L;

	static final Logger logger = Logger.getLogger(TagDataServiceBean.class
			.getName());
  
	public TagDataServiceBean() {
		super();
	}

	/**
	 * @param data
	 * @ejb.interface-method view-type = "both"
	 */
	public byte[] readData(DataSpec data) {

		logger.info("TagDataService readData");
		// 1. get the Reader ID
		List<List> readers = getReaders(data.getTagID());
		if (readers.isEmpty()) {
			return null;
		}
		String guid = TagDataServiceUtil.generateGUID(data);

		byte[] retval = null;

		// 2. send the request to reader
		for (List reader : readers) {
			String reader_id = (String) reader.get(0);
			String reader_ip = (String) reader.get(1);
			if (reader_ip.equals("0.0.0.0")) {
				reader_ip = "127.0.0.1";
			}
			if (reader_ip.indexOf(":")==-1) {
				reader_ip = reader_ip + ":2001";
			}

			TagWriteActivateRequest tr = new TagWriteActivateRequest(reader_id,
					reader_ip, data, guid, "read");
			try {
				tr.sendMessage();
			} catch (NamingException e) {
				// FIXME
				e.printStackTrace();
			} catch (JMSException e) {
				// FIXME
				e.printStackTrace();
			}
		}
		// 3. listen to the queue

		long start = System.currentTimeMillis();
		long now = start;
		do {
			try {
				now = System.currentTimeMillis();
				Object o = TagDataServiceCallback.recvMessage("guid", guid,
						TIMEOUT - (now - start));
				if (o != null && (o instanceof DataResult)) {
					DataResult d = (DataResult) o;
					readers.remove(d.getReaderID());
					if (d.getStatus() == 0) {
						retval = d.getData();
					}
				}
			} catch (ImplementationException e) {
				e.printStackTrace();
			}
		} while (((now - start) > TIMEOUT) && (readers.isEmpty() == false));

		logger.info("return status: " + retval);
		return retval;

	}

	/**
	 * @param data
	 * @ejb.interface-method view-type = "both"
	 */
	public int writeData(DataSpec data) {

		logger.info("TagDataService writeData");
		// 1. get the Reader ID
		List<List> readers = getReaders(data.getTagID());
		String guid = TagDataServiceUtil.generateGUID(data);

		if (readers.isEmpty()) {
			return -2;
		}
		int retval = -1;

		// 2. send the request to reader
		for (List reader : readers) {
			String reader_id = (String) reader.get(0);
			String reader_ip = (String) reader.get(1);
			if (reader_ip.equals("0.0.0.0")) {
				reader_ip = "127.0.0.1";
			}
			if (reader_ip.indexOf(":")==-1) {
				reader_ip = reader_ip + ":2001";
			}

			TagWriteActivateRequest tr = new TagWriteActivateRequest(reader_id,
					reader_ip, data, guid, "write");
			try {
				tr.sendMessage();
			} catch (NamingException e) {
				// FIXME
				e.printStackTrace();
			} catch (JMSException e) {
				// FIXME
				e.printStackTrace();
			}
		}
		// 3. listen to the queue

		long start = System.currentTimeMillis();
		long now = start;
		do {
			try {
				now = System.currentTimeMillis();
				Object o = TagDataServiceCallback.recvMessage("guid", guid,
						TIMEOUT - (now - start));
				if (o != null && (o instanceof DataResult)) {
					DataResult d = (DataResult) o;
					readers.remove(d.getReaderID());
					if (d.getStatus() == 0) {
						data.setDatabytes(d.getData());
						retval = 0;
					}
				}
			} catch (ImplementationException e) {
				e.printStackTrace();
			}
		} while (((now - start) > TIMEOUT) && (readers.isEmpty() == false));

		logger.info("return status: " + retval);
		return retval;

	}

	/**
	 * @dao.call
	 */
	abstract protected List<List> getReaders(String tagID);

}
