package cuhk.junit;

import java.net.URL;

import javax.xml.namespace.QName;

import junit.framework.TestCase;
import cuhk.ale.soap.DataSpec;
import cuhk.ale.soap.TagDataService;
import cuhk.ale.soap.TagDataService_Service;

public class SOAPTagDataServiceClientTestCase extends TestCase {

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(SOAPTagDataServiceClientTestCase.class);
	}

	public final void testSOAPTagData() throws Exception {
		try {
			TagDataService_Service service = new TagDataService_Service(
					new URL("http://localhost:8080/ale-ws/aletagdata?wsdl"),
					new QName("urn:cuhk:ale:reader:wsdl:1",
							"TagDataService")); 
			TagDataService port = service.getTagDataServicePort();
			DataSpec data = new DataSpec();
	   		data.setAddress(1);
			data.setDatabytes(null); 
			data.setFormatID(2);
			data.setLength(3);
			data.setTagID("test");
	 		byte[] i = port.readData(data); 
	 		System.out.println("result="+i); 
			int j = port.writeData(data);
			System.out.println("result="+j); 

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

}
