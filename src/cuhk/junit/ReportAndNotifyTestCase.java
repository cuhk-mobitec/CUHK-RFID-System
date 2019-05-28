package cuhk.junit;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import cuhk.ale.EventCycle;
import cuhk.ale.NotifyRequest;
import cuhk.ale.PatternURI;
import cuhk.ale.ejb.interfaces.NotifierUtil;
import cuhk.ale.ejb.interfaces.ReportGenerator;
import cuhk.ale.ejb.interfaces.ReportGeneratorHome;
import cuhk.ale.ejb.interfaces.ReportGeneratorUtil;
import epcglobal.ale.*;
import junit.framework.TestCase;

public class ReportAndNotifyTestCase extends TestCase {

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(ReportAndNotifyTestCase.class);
	}

	/*
	 * Test method for 'cuhk.ale.ejb.ReportGeneratorBean.generateReports(EventCycle)'
	 */
	public final void testReportAndNotify() throws Exception {
		ECSpec spec = new ECSpec();
		
		ECLogicalReaders logicalReaders = new ECLogicalReaders();
		logicalReaders.getLogicalReader().add("READER1");
		
		spec.setLogicalReaders(logicalReaders);
		
		ECBoundarySpec boundarySpec = new ECBoundarySpec();
		spec.setBoundarySpec(boundarySpec);
		
		ECReportSpecs reportSpecs = new ECReportSpecs();

		ECReportSpec reportSpec = new ECReportSpec();
		reportSpec.setReportName("test_report");

		ECReportSetSpec reportSetSpec = new ECReportSetSpec();
		reportSetSpec.setSet(ECReportSetEnum.CURRENT);
			
		reportSpec.setReportSet(reportSetSpec);
		
		// setup the filterSpec
		ECFilterSpec filterSpec = new ECFilterSpec();

		ObjectFactory factory = new ObjectFactory();
		
		ECIncludePatterns includePatterns = new ECIncludePatterns();
//		includePatterns.getIncludePattern().add(factory.createECIncludePatternsIncludePattern(new PatternURI("urn:epc:pat:gid-96:20.300.*")));
		includePatterns.getIncludePattern().add("urn:epc:pat:gid-96:20.300.*");
//		includePatterns.getIncludePattern().add(factory.createECIncludePatternsIncludePattern(new PatternURI("urn:epc:pat:gid-96:20.301.401")));
		includePatterns.getIncludePattern().add("urn:epc:pat:gid-96:20.301.401");

		ECExcludePatterns excludePatterns = new ECExcludePatterns();
//		excludePatterns.getExcludePattern().add(factory.createECExcludePatternsExcludePattern(new PatternURI("urn:epc:pat:gid-96:20.300.[401-402]")));
		excludePatterns.getExcludePattern().add("urn:epc:pat:gid-96:20.300.[401-402]");

		filterSpec.setIncludePatterns(includePatterns);
		filterSpec.setExcludePatterns(excludePatterns);
		reportSpec.setFilterSpec(filterSpec);
		
		// setup the groupSpec 
		Vector<PatternURI> patternList = new Vector<PatternURI>();
//		patternList.add(new PatternURI("urn:epc:pat:gid-96:20.*.[400-401]"));
//		patternList.add(new PatternURI("urn:epc:pat:gid-96:21.X.*"));
		ECGroupSpec groupSpec = new ECGroupSpec();
//		groupSpec.getPattern().add(factory.create)
//		patternList
		reportSpec.setGroupSpec(groupSpec);
		
		ECReportOutputSpec reportOutputSpec = new ECReportOutputSpec();
		reportOutputSpec.setIncludeEPC(false);
		reportOutputSpec.setIncludeTag(true);
		reportOutputSpec.setIncludeRawHex(false);
		reportOutputSpec.setIncludeRawDecimal(false);
		reportOutputSpec.setIncludeCount(true);
		reportSpec.setOutput(reportOutputSpec);
		
		reportSpec.setReportIfEmpty(true);
		reportSpec.setReportOnlyOnChange(false);
		
		reportSpecs.getReportSpec().add(reportSpec);
		spec.setReportSpecs(reportSpecs);
		
		spec.setIncludeSpecInReports(true);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss");		
		
//		ALEServiceHome aleServiceHome = (ALEServiceHome) ALEServiceUtil.getHome();
//		ALEService aleService = aleServiceHome.create();
//		aleService.define("test_spec",spec);

		Date previousStartDate = format.parse("20060107 000000");
		Date previousEndDate = format.parse("20060108 235959");		

		Date startDate = format.parse("20060514 000000");
		Date endDate = format.parse("20060515 235959");		
		
		
//		Date startDate = format.parse("20060108 000000");
//		Date endDate = format.parse("20060111 235959");
			
		ReportGeneratorHome reportGeneratorHome = (ReportGeneratorHome) ReportGeneratorUtil.getHome();
		ReportGenerator reportGenerator = reportGeneratorHome.create();

		EventCycle eventCycle = new EventCycle("test_spec", spec, startDate, endDate, previousStartDate, previousEndDate, ECTerminationCondition.TRIGGER);
		ECReports reports = reportGenerator.generateReports(eventCycle);
		System.out.println(reports);
		
		List<URI> notificationURIs = new Vector<URI>();
		notificationURIs.add(new URI("http://localhost:9090/rfid/http.jsp"));
		notificationURIs.add(new URI("socket://192.168.0.3:4888"));
		notificationURIs.add(new URI("file://localhost/c:/notify1.txt"));
		notificationURIs.add(new URI("file:///c:/notify2.txt"));
		NotifyRequest nr = new NotifyRequest(reports,notificationURIs);
		
		Queue queue = NotifierUtil.getQueue();
		QueueConnection connection = NotifierUtil.getQueueConnection();
		QueueSession session = connection.createQueueSession(true,Session.AUTO_ACKNOWLEDGE);
		QueueSender sender = session.createSender(queue);
		ObjectMessage message = session.createObjectMessage();
		message.setObject(nr);
		sender.send(message);
		session.commit();
		session.close();
		connection.close();	
		assert(true);
	}
	
	
}
