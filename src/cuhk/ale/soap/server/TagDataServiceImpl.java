package cuhk.ale.soap.server;

import javax.ejb.CreateException;
import javax.jws.WebService;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import cuhk.ale.ejb.interfaces.TagDataServiceLocal;
import cuhk.ale.ejb.interfaces.TagDataServiceLocalHome;
import cuhk.ale.ejb.interfaces.TagDataServiceUtil;
import cuhk.ale.reader.DataSpec;

@WebService(
		name="TagDataService",
		targetNamespace="urn:cuhk:ale:reader:wsdl:1",
		serviceName="TagDataService",
		portName="TagDataServicePort"
		)
public class TagDataServiceImpl {
	static private final Logger logger = Logger
			.getLogger(TagDataServiceImpl.class.getName());
	
	public byte[] readData( DataSpec data) {
		logger.info("readData is triggered");
		
		try { 
			TagDataServiceLocalHome TagDataServiceHome = (TagDataServiceLocalHome) TagDataServiceUtil.getLocalHome();
			TagDataServiceLocal tagDataService = TagDataServiceHome.create();
			return tagDataService.readData(data) ;
		} catch (CreateException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		logger.info(data.getTagID());
		return null;
	}
	
	public int writeData( DataSpec data) {
		logger.info("writeData is triggered");
		
		try {
			TagDataServiceLocalHome TagDataServiceHome = (TagDataServiceLocalHome) TagDataServiceUtil.getLocalHome();
			TagDataServiceLocal tagDataService = TagDataServiceHome.create();
			return tagDataService.writeData(data) ;
		} catch (CreateException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		logger.info(data.getTagID());
		return -1;
	}
	
}
