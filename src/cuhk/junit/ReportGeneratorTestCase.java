package cuhk.junit;

import java.util.*;
import java.text.SimpleDateFormat;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.naming.Context;
import junit.framework.TestCase;
import cuhk.ale.*;
import cuhk.ale.ejb.interfaces.*;
import epcglobal.ale.*;

public class ReportGeneratorTestCase extends TestCase {

	public ReportGeneratorTestCase() throws Exception {
		super();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'cuhk.ale.ejb.ReportGeneratorBean.generateReports(EventCycle)'
	 */
	public final void testGenerateReports() throws Exception {
		ECSpec spec = new ECSpec();
		
		ECLogicalReaders logicalReaders = new ECLogicalReaders();
		logicalReaders.getLogicalReader().add("LREADER1");
		logicalReaders.getLogicalReader().add("LREADER2");
		
		spec.setLogicalReaders(logicalReaders);
		
		ECBoundarySpec boundarySpec = new ECBoundarySpec();
		spec.setBoundarySpec(boundarySpec);
		
		ECReportSpecs reportSpecs = new ECReportSpecs();
		// Vector<ECReportSpec> reportSpecs = new Vector<ECReportSpec>();

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

		Hashtable<String, String> props = new Hashtable<String, String>();
			
		ReportGeneratorHome reportGeneratorHome = (ReportGeneratorHome) ReportGeneratorUtil.getHome();
		ReportGenerator reportGenerator = reportGeneratorHome.create();

		EventCycle eventCycle = new EventCycle("test_spec", spec, startDate, endDate, previousStartDate, previousEndDate, ECTerminationCondition.TRIGGER);
		ECReports reports = reportGenerator.generateReports(eventCycle);
		System.out.println(reports);
		assert(true);
	}
	
//	public void test() {
//		System.out.println(Grammar.RANGE_COMPONENT);
//	
//		
//	}
}
