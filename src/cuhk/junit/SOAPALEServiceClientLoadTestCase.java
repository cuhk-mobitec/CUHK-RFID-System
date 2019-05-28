package cuhk.junit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import junit.framework.TestCase;
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
import epcglobal.ale.soap.ImplementationExceptionResponse;
import epcglobal.ale.soap.InvalidURIExceptionResponse;
import epcglobal.ale.soap.NoSuchNameExceptionResponse;
import epcglobal.ale.soap.NoSuchSubscriberExceptionResponse;
import epcglobal.ale.soap.SecurityExceptionResponse;
import epcglobal.ale.soap.Subscribe;
import epcglobal.ale.soap.Undefine;
import epcglobal.ale.soap.Unsubscribe;

public class SOAPALEServiceClientLoadTestCase extends TestCase {

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(SOAPALEServiceClientLoadTestCase.class);
	}

	public final void testSOAPAle() throws Exception {
		try {
			ALEService service = new ALEService(new URL("http://localhost:8080/ale-ws/aleservice?wsdl"), new QName(
					"urn:epcglobal:ale:wsdl:1", "ALEService"));
			ALEServicePortType port = service.getALEServicePort();
			
			ECSpec sampleECSpec;
			
			sampleECSpec = getSampleECSpec(1);
			// String sampleSpecName = "testSpecName";
			for (int i = 0; i<10; i++)
			{
				String sampleSpecName = "ECSpecLoad_1"+i;
				runDefine(port, sampleECSpec, sampleSpecName);
				runSubscribe(port, sampleSpecName, "socket://localhost:80"+(10+i));
			}

			sampleECSpec = getSampleECSpec(2);
			// String sampleSpecName = "testSpecName";
			for (int i = 0; i<10; i++)
			{
				String sampleSpecName = "ECSpecLoad_2"+i;
				runDefine(port, sampleECSpec, sampleSpecName);
				runSubscribe(port, sampleSpecName, "socket://localhost:80"+(20+i));
			}

			return ;
			// API 1
			
			// API 2
//			ECSpec retSpec = runGetECSpec(port, "testSpecName");
//			
//			System.out.println(retSpec);
//			
//			// API 3
//			ArrayOfString retSpecNames = runGetECSpecNames(port);
//			
//			runSubscribe(port, "testSpecName", "file:///c:/notify3.txt");
//			
//			Thread.sleep(10000);
//			
//			runUnsubscribe(port, "testSpecName", "file:///c:/notify3.txt");
//			
//			// API 1
//			runUndefine(port, sampleSpecName);

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	

	
	private void runUnsubscribe(ALEServicePortType port, String specName, String notificationUri) throws ImplementationExceptionResponse, InvalidURIExceptionResponse, NoSuchNameExceptionResponse, NoSuchSubscriberExceptionResponse, SecurityExceptionResponse {
		Unsubscribe parms = new Unsubscribe();
		parms.setSpecName(specName);
		parms.setNotificationURI(notificationUri);
		port.unsubscribe(parms);
		
	}

	private void runSubscribe(ALEServicePortType port, String specName, String notificationUri) throws DuplicateSubscriptionExceptionResponse, ImplementationExceptionResponse, InvalidURIExceptionResponse, NoSuchNameExceptionResponse, SecurityExceptionResponse {
		Subscribe parms = new Subscribe();
		parms.setSpecName(specName);
		parms.setNotificationURI(notificationUri);
		port.subscribe(parms);
	}

	private ArrayOfString runGetECSpecNames(ALEServicePortType port) throws ImplementationExceptionResponse, SecurityExceptionResponse {
		return port.getECSpecNames(new EmptyParms());
	}

	private ECSpec runGetECSpec(ALEServicePortType port, String specName) throws ImplementationExceptionResponse, SecurityExceptionResponse {
		GetECSpec parms = new GetECSpec();
		parms.setSpecName(specName);
		return port.getECSpec(parms);		
	}

	private void runUndefine(ALEServicePortType port, String sampleSpecName) throws ImplementationExceptionResponse,
			NoSuchNameExceptionResponse, SecurityExceptionResponse {
		Undefine parms = new Undefine();
		parms.setSpecName(sampleSpecName);
		port.undefine(parms);
	}

	private void runDefine(ALEServicePortType port, ECSpec sampleECSpec, String sampleSpecName)
			throws DuplicateNameExceptionResponse, ECSpecValidationExceptionResponse, ImplementationExceptionResponse,
			SecurityExceptionResponse {
		Define parms = new Define();
		parms.setSpec(sampleECSpec);
		parms.setSpecName(sampleSpecName);
		port.define(parms);
	}

	private ECSpec getSampleECSpec(int i) {
		try {
			JAXBContext jc;
			jc = JAXBContext.newInstance("epcglobal.ale");
			Unmarshaller u = jc.createUnmarshaller();
			// JAXBElement element = (JAXBElement) u.unmarshal(new FileInputStream("samples/ecspec1.xml"));
			JAXBElement element = (JAXBElement) u.unmarshal(new FileInputStream("C:/ecspeceditor/samples/ecspec_load"+ i +".xml"));
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
