package cuhk.junit;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.jms.*;
import junit.framework.TestCase;
import cuhk.ale.*;
import cuhk.ale.ejb.interfaces.NotifierUtil;
import epcglobal.ale.*;

public class NotifierTestCase extends TestCase {

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(NotifierTestCase.class);
	}
		
	public final void testSendMessage() throws Exception {
		//ECReports reports = new ECReports("spec1");
	
		ECReports reports = new ECReports();
		reports.setSpecName("EventCycle1");
		reports.setDate(new Date());
		reports.setALEID("Edge34");
		reports.setTotalMilliseconds(3034);
		reports.setTerminationCondition(ECTerminationCondition.DURATION);
		
		ECReportList reportList = new ECReportList();
		reportList.getReport().add(createReport1());
		reportList.getReport().add(createReport2());
		reportList.getReport().add(createReport3());
		
		reports.setReports(reportList);
		
		List<URI> notificationURIs = new Vector<URI>();
//		notificationURIs.add(new URI("http://localhost:8080/tagviewer/subscriber"));
//		notificationURIs.add(new URI("http://localhost:9090/rfid/http.jsp"));
//		notificationURIs.add(new URI("http://137.189.33.78:8080/tagviewer/subscriber"));
		
		notificationURIs.add(new URI("tcp://localhost:4444"));
//		notificationURIs.add(new URI("file://localhost/c:/notify1.txt"));
		notificationURIs.add(new URI("file:///c:/notify2.txt"));
		NotifyRequest nr = new NotifyRequest(reports,notificationURIs);
		
		Queue queue = NotifierUtil.getQueue();
		QueueConnection connection = NotifierUtil.getQueueConnection(null);
		QueueSession session = connection.createQueueSession(true,Session.AUTO_ACKNOWLEDGE);
		QueueSender sender = session.createSender(queue);
		ObjectMessage message = session.createObjectMessage();
		message.setObject(nr);
		sender.send(message);
		session.commit();
		session.close();
		connection.close();
	}
	
	private ECReport createReport1() {
		ECReport report = new ECReport();
		report.setReportName("report1");
		ECReportGroup reportGroup = new ECReportGroup();
		ECReportGroupList reportGroupList = new ECReportGroupList();
		
		ECReportGroupListMember reportGroupListMember1 = new ECReportGroupListMember();
		EPC epc1 = new EPC();
		epc1.setValue("urn:epc:tag:gid-96:10.50.1000");
		reportGroupListMember1.setTag(epc1);

		ECReportGroupListMember reportGroupListMember2 = new ECReportGroupListMember();
		EPC epc2 = new EPC();
		epc2.setValue("urn:epc:tag:gid-96:10.50.1001");
		reportGroupListMember2.setTag(epc2);		
		
		reportGroupList.getMember().add(reportGroupListMember1);
		reportGroupList.getMember().add(reportGroupListMember2);
		reportGroup.setGroupList(reportGroupList);
		report.getGroup().add(reportGroup);
		return report;
	}
	
	private ECReport createReport2() {
		ECReport report = new ECReport();
		report.setReportName("report2");
		ECReportGroup reportGroup = new ECReportGroup();
		ECReportGroupCount reportGroupCount = new ECReportGroupCount();
		reportGroupCount.setCount(6847);
		reportGroup.setGroupCount(reportGroupCount);
		report.getGroup().add(reportGroup);
		return report;
	}	
	
	private ECReport createReport3() {
		ECReport report = new ECReport();
		report.setReportName("report3");
		
		ECReportGroup reportGroup1 = new ECReportGroup();
		reportGroup1.setGroupName("urn:epc:pat:sgtin-64:3.0037000.12345.*");
		ECReportGroupCount reportGroupCount1 = new ECReportGroupCount();
		reportGroupCount1.setCount(2);
		reportGroup1.setGroupCount(reportGroupCount1);

		ECReportGroup reportGroup2 = new ECReportGroup();
		reportGroup2.setGroupName("urn:epc:pat:sgtin-64:3.0037000.55555.*");
		ECReportGroupCount reportGroupCount2 = new ECReportGroupCount();
		reportGroupCount2.setCount(3);
		reportGroup2.setGroupCount(reportGroupCount2);		

		ECReportGroup reportGroup3 = new ECReportGroup();
		ECReportGroupCount reportGroupCount3 = new ECReportGroupCount();
		reportGroupCount3.setCount(6842);
		reportGroup3.setGroupCount(reportGroupCount3);		
		
		report.getGroup().add(reportGroup1);
		report.getGroup().add(reportGroup2);
		report.getGroup().add(reportGroup3);
		return report;
	}		
}
