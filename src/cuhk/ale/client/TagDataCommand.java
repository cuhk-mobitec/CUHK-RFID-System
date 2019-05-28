package cuhk.ale.client;

import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Properties;

import javax.xml.namespace.QName;

import cuhk.ale.soap.DataSpec;
import cuhk.ale.soap.TagDataService;
import cuhk.ale.soap.TagDataService_Service;

public class TagDataCommand {

	private static String server_ip;

	private static String server_port;

	private static String mode;

	private static String reader_id;

	private static String tag_id;

	private static String ascii_data;

	public static void main(String[] args) {
		try {
			Properties props = System.getProperties();
			// Enumeration enum1 = props.propertyNames();
			// for (; enum1.hasMoreElements(); ) {
			// String propName = (String)enum1.nextElement();
			// String propValue = (String)props.get(propName);
			// System.out.println(propName + ":" + propValue);
			// }
			// props.load(new FileInputStream("applications.properties"));
			server_ip = props.getProperty("soap_ip");
			server_port = props.getProperty("soap_port");
			mode = props.getProperty("mode");			
			tag_id = props.getProperty("tag_id");
			ascii_data = props.getProperty("data");

			if (mode.equals("-R")) {
				readData();
			} else if (mode.equals("-W")) {
				writeData();
			} else
				throw new Exception("Unrecongized option");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			printUsage();
		} finally {
			return;
		}
	}

	private static void printUsage() {
		System.out.println("USAGE:");
		System.out.println("> tagRW SOAPSERVER -R <TagID>");
		System.out.println("> tagRW SOAPSERVER -W <TagID> <ASCII_Text>");

	} 

	public final static void readData() throws Exception {
		try {
			TagDataService_Service service = new TagDataService_Service(
					new URL("http://" + server_ip + ":" + server_port
							+ "/ale-ws/aletagdata?wsdl"), new QName(
							"urn:cuhk:ale:reader:wsdl:1", "TagDataService"));
			TagDataService port = service.getTagDataServicePort();
			DataSpec data = new DataSpec();
			data.setAddress(0);
			data.setDatabytes(null);
			data.setFormatID(1);
			data.setLength(10000);
			data.setTagID(tag_id);
			
			byte[] bytes = port.readData(data);
			if ( bytes == null) {
				System.out.println("No reader identify the tag, no tag is written-into.");
			}
			System.out.println(new String(bytes));
			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public final static void writeData() throws Exception {
		try {
			TagDataService_Service service = new TagDataService_Service(
					new URL("http://" + server_ip + ":" + server_port
							+ "/ale-ws/aletagdata?wsdl"), new QName(
							"urn:cuhk:ale:reader:wsdl:1", "TagDataService"));
			TagDataService port = service.getTagDataServicePort();
			DataSpec data = new DataSpec();
			data.setAddress(0);

//			ByteBuffer buf = ByteBuffer.allocate(ascii_data.length()*2);
//			CharBuffer cbuf = buf.asCharBuffer();
//			cbuf.put(ascii_data);
//			byte[] bytes = buf.array();
//			for( int i=0; i<bytes.length; i++) System.out.print(bytes[i]);
			data.setDatabytes(ascii_data.getBytes());

			data.setFormatID(1);
			data.setLength(ascii_data.length());
			data.setTagID(tag_id);
			int i = port.writeData(data);
			printResult(i);			
			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	private static void printResult(int i) {
		System.out.print("Result: ");
		switch (i){
		case 0: 
			System.out.println("Successful.");
			break;
		case -1: 
			System.out.println("Tag not found");
			break;
		case -2: 
			System.out.println("Read error.");
			break;
		default:
			System.out.println("Unrecongized error.");
			break;
		}
		
	}

}