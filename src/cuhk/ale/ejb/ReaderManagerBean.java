package cuhk.ale.ejb;

import java.util.List;

import javax.ejb.SessionBean;

import org.apache.log4j.Logger;

import cuhk.ale.PhysicalReaderInfo;
import cuhk.ale.TagDataServiceCallback;
import cuhk.ale.exceptions.ImplementationException;
import cuhk.ale.reader.DataResult;

/**
 * @ejb.bean name="ReaderManager" display-name="ReaderManager"
 *           description="Description for ReaderManager"
 *           jndi-name="ejb/ReaderManager" type="Stateless" view-type="both"
 * 
 * @ejb.dao class="cuhk.ale.dao.ReaderManagerDAO" impl-class="cuhk.ale.dao.ReaderManagerDAOImpl"
 * 
 * @ejb.resource-ref res-ref-name="mySQLDS" res-type="javax.sql.Datasource"
 *                   res-auth="Container"
 * 
 * @jboss.resource-ref res-ref-name="mySQLDS" jndi-name="java:/mySQLDS"
 * 
 */

public abstract class ReaderManagerBean implements SessionBean {

	static Logger logger = Logger.getLogger(ReaderManagerBean.class.getName());

	public ReaderManagerBean() {
		super();
	}
	
	/**
	 * Business method
	 * 
	 * @ejb.interface-method view-type = "both"
	 */
	public void submitALETags(String readerID, List<String> tags) throws Exception {
		logger.info("[submitTags] readerID:" + readerID + " tags:" + tags);
		if (isPhysicalReaderExists(readerID)) {
			submitTags(readerID,tags);
		}
		else {
			logger.error("Reader " + readerID + " is not yet registered.");
			throw new Exception("Reader " + readerID + " is not yet registered.");
		}
	}

	/**
	 * Business method
	 * 
	 * @ejb.interface-method view-type = "both"
	 */
	
	public void submitALEPhysicalReaderInfo(String readerID, PhysicalReaderInfo physicalReaderInfo) throws Exception {
		logger.info("[submitInfo] readerID:" + readerID + 
				" manufacturer:" + physicalReaderInfo.getManufacturer() +
				" model:" + physicalReaderInfo.getModel() +
				" IPAddress:" + physicalReaderInfo.getIPAddress());
		
		if (isPhysicalReaderExists(readerID)) {
			updatePhysicalReaderInfo(readerID,physicalReaderInfo);
		}
		else {
			insertPhysicalReaderInfo(readerID,physicalReaderInfo);	
		}
	}
	
	/**
	 * Business method
	 * @ejb.interface-method view-type = "both"
	 */
	public void submitReadData(String readerID, int status, byte[] data, String guid) {
		logger.info("[submitReadData] readerID:" + readerID + " status:" + status + " guid:" + guid );
		DataResult d = new DataResult( readerID, status, data, guid);
		try {
			TagDataServiceCallback.sendMessage(d, "guid", guid);
		} catch (ImplementationException e) {
			// FIXME
			e.printStackTrace();
		}
	}

	/**
	 * Business method
	 * @ejb.interface-method view-type = "both"
	 */
	public void submitWriteData(String readerID, int status, String guid) {

		logger.info("[submitWriteData] readerID:" + readerID + " status:" + status + " guid:" + guid );
		DataResult d = new DataResult( readerID, status, null, guid);
		
		try {
			TagDataServiceCallback.sendMessage(d, "guid", guid);
		} catch (ImplementationException e) {
			// FIXME
			e.printStackTrace();
		}
	}
	
	/**
	 * @dao.call
	 */

	abstract protected void submitTags(String readerID, List<String> tags) throws Exception;

	/**
	 * @dao.call
	 */	
	
	abstract protected boolean isPhysicalReaderExists(String readerID) throws Exception;
	
	/**
	 * @dao.call
	 */	
	
	abstract protected void insertPhysicalReaderInfo(String readerID, PhysicalReaderInfo physicalReaderInfo) throws Exception;
	
	/**
	 * @dao.call
	 */	
	
	abstract protected void updatePhysicalReaderInfo(String readerID, PhysicalReaderInfo physicalReaderInfo) throws Exception;
	
	/**
	 * @dao.call
	 * @ejb.interface-method view-type = "both"
	 */	
	
	abstract protected List<String> retrieveLogicalReaderNames() throws Exception ;
	
	/**
	 * @dao.call
	 */	
	
	abstract protected List<String> retrieveReaderNamesFromTag(String tag) throws Exception ;
	
		

}
