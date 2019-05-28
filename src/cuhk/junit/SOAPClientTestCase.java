package cuhk.junit;

import java.net.URL;

import javax.xml.namespace.QName;

import junit.framework.TestCase;
import epcglobal.ale.soap.*;

public class SOAPClientTestCase extends TestCase {

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(SOAPClientTestCase.class);
	}

	public final void testSOAP() throws Exception {
		try {
			ALEService service = new ALEService(new URL(
					"http://localhost:8080/ale-ws/aleservice?wsdl"), new QName(
					"urn:epcglobal:ale:wsdl:1", "ALEService"));
			ALEServicePortType port = service.getALEServicePort();
			System.out.println("GetStandardVersion\n");
			String result = port.getStandardVersion(null);
			System.out.println("Result:" + result);

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}
}
