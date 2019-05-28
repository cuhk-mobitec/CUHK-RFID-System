package cuhk.ale.editor.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import cuhk.ale.editor.model.Messages;

import epcglobal.ale.ECReports;
import epcglobal.ale.ECSpec;
import epcglobal.ale.soap.ALEService;
import epcglobal.ale.soap.ALEServicePortType;
import epcglobal.ale.soap.ArrayOfString;
import epcglobal.ale.soap.Define;
import epcglobal.ale.soap.DuplicateNameExceptionResponse;
import epcglobal.ale.soap.DuplicateSubscriptionExceptionResponse;
import epcglobal.ale.soap.ECSpecValidationExceptionResponse;
import epcglobal.ale.soap.EmptyParms;
import epcglobal.ale.soap.GetECSpec;
import epcglobal.ale.soap.GetSubscribers;
import epcglobal.ale.soap.Immediate;
import epcglobal.ale.soap.ImplementationExceptionResponse;
import epcglobal.ale.soap.InvalidURIExceptionResponse;
import epcglobal.ale.soap.NoSuchNameExceptionResponse;
import epcglobal.ale.soap.NoSuchSubscriberExceptionResponse;
import epcglobal.ale.soap.Poll;
import epcglobal.ale.soap.SecurityExceptionResponse;
import epcglobal.ale.soap.Subscribe;
import epcglobal.ale.soap.Undefine;
import epcglobal.ale.soap.Unsubscribe;

public class AleSoapClient {
	static Logger logger = Logger.getLogger(AleSoapClient.class);

	static final String wspath = Messages.getString("SoapClient.WSPath");
	// static final String wspath = "http://137.189.33.76:8080/ale-ws/aleservice?wsdl";

	static final boolean _debug = false;

	static ALEServicePortType aleServicePort = null;

	public static ALEServicePortType retrieveALEServicePort() {

		if (_debug)
			return null;

		if (aleServicePort != null)
			return aleServicePort;

		ALEService service;
		try {
			service = new ALEService(new URL(wspath), new QName(
					"urn:epcglobal:ale:wsdl:1", "ALEService"));
			aleServicePort = service.getALEServicePort();
			return aleServicePort;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void runUnsubscribe(String specName, String notificationUri)
			throws ImplementationExceptionResponse,
			InvalidURIExceptionResponse, NoSuchNameExceptionResponse,
			NoSuchSubscriberExceptionResponse, SecurityExceptionResponse {
		ALEServicePortType port = retrieveALEServicePort();
		Unsubscribe parms = new Unsubscribe();
		parms.setSpecName(specName);
		parms.setNotificationURI(notificationUri);
		port.unsubscribe(parms);

	}

	public static void runSubscribe(String specName, String notificationUri)
			throws DuplicateSubscriptionExceptionResponse,
			ImplementationExceptionResponse, InvalidURIExceptionResponse,
			NoSuchNameExceptionResponse, SecurityExceptionResponse {
		ALEServicePortType port = retrieveALEServicePort();
		Subscribe parms = new Subscribe();
		parms.setSpecName(specName);
		parms.setNotificationURI(notificationUri);
		port.subscribe(parms);
	}

	public static ArrayOfString runGetECSpecNames()
			throws ImplementationExceptionResponse, SecurityExceptionResponse {
		ALEServicePortType port = retrieveALEServicePort();
		logger.info("runGetECSpecNames");
		if (_debug)
			return null;
		return port.getECSpecNames(new EmptyParms());
	}

	public static ECSpec runGetECSpec(String specName)
			throws ImplementationExceptionResponse, SecurityExceptionResponse {
		ALEServicePortType port = retrieveALEServicePort();
		GetECSpec parms = new GetECSpec();
		parms.setSpecName(specName);
		return port.getECSpec(parms);
	}

	public static void runUndefine(String sampleSpecName)
			throws ImplementationExceptionResponse,
			NoSuchNameExceptionResponse, SecurityExceptionResponse {
		ALEServicePortType port = retrieveALEServicePort();
		Undefine parms = new Undefine();
		parms.setSpecName(sampleSpecName);
		port.undefine(parms);
	}

	public static void runDefine(ECSpec sampleECSpec, String sampleSpecName)
			throws DuplicateNameExceptionResponse,
			ECSpecValidationExceptionResponse, ImplementationExceptionResponse,
			SecurityExceptionResponse {
		ALEServicePortType port = retrieveALEServicePort();
		Define parms = new Define();
		parms.setSpec(sampleECSpec);
		parms.setSpecName(sampleSpecName);
		port.define(parms);
	}

	public static ArrayOfString runGetSubscribers(String sampleSpecName) throws ImplementationExceptionResponse, NoSuchNameExceptionResponse, SecurityExceptionResponse {
		ALEServicePortType port = retrieveALEServicePort();
		GetSubscribers parms = new GetSubscribers();
		parms.setSpecName(sampleSpecName);
		return port.getSubscribers(parms);
	}
	
	public static String runGetVendorVersion() throws ImplementationExceptionResponse{
		ALEServicePortType port = retrieveALEServicePort();
		return port.getVendorVersion(new EmptyParms());
		
	}

	public static String runGetStandardVersion() throws ImplementationExceptionResponse{
		ALEServicePortType port = retrieveALEServicePort();
		return port.getStandardVersion(new EmptyParms());		
	}
	
	public static ECReports runPoll(String sampleSpecName) throws ImplementationExceptionResponse, NoSuchNameExceptionResponse, SecurityExceptionResponse {
		ALEServicePortType port = retrieveALEServicePort();
		Poll parms = new Poll();
		parms.setSpecName(sampleSpecName);
		return port.poll(parms);
	}
	
	public static ECReports runImmediate(ECSpec sampleECSpec) throws ECSpecValidationExceptionResponse, ImplementationExceptionResponse, SecurityExceptionResponse {
		ALEServicePortType port = retrieveALEServicePort();
		Immediate parms = new Immediate();
		parms.setSpec(sampleECSpec);
		return port.immediate(parms);
	}
	
	
	public static ECSpec getECSpecFromFile(String filename) {
		try {
			JAXBContext jc;
			jc = JAXBContext.newInstance("epcglobal.ale");
			Unmarshaller u = jc.createUnmarshaller();
			JAXBElement element = (JAXBElement) u
					.unmarshal(new FileInputStream(filename));
			ECSpec retval = (ECSpec) element.getValue();
			return retval;
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
