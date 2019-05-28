package cuhk.ale.ejb;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.ejb.SessionBean;
import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;

import cuhk.ale.ECTag;
import cuhk.ale.EventCycle;
import cuhk.ale.PatternURI;
import cuhk.ale.exceptions.ParseURIException;
import epcglobal.ale.ECFilterSpec;
import epcglobal.ale.ECGroupSpec;
import epcglobal.ale.ECReport;
import epcglobal.ale.ECReportGroup;
import epcglobal.ale.ECReportGroupCount;
import epcglobal.ale.ECReportGroupList;
import epcglobal.ale.ECReportGroupListMember;
import epcglobal.ale.ECReportList;
import epcglobal.ale.ECReportOutputSpec;
import epcglobal.ale.ECReportSetEnum;
import epcglobal.ale.ECReportSpec;
import epcglobal.ale.ECReports;
import epcglobal.ale.ECSpec;
import epcglobal.ale.EPC;

/**
 * @ejb.bean name="ReportGenerator" display-name="ReportGenerator"
 *           description="Description for ReportGenerator"
 *           jndi-name="ejb/ReportGenerator" type="Stateless" view-type="both"
 * 
 * @ejb.dao class="cuhk.ale.dao.ReportGeneratorDAO" impl-class="cuhk.ale.dao.ReportGeneratorDAOImpl"
 * 
 * @ejb.resource-ref res-ref-name="mySQLDS" res-type="javax.sql.Datasource"
 *                   res-auth="Container"
 * 
 * @jboss.resource-ref res-ref-name="mySQLDS" jndi-name="java:/mySQLDS"
 * 
 */

public abstract class ReportGeneratorBean implements SessionBean {

	static Logger logger = Logger.getLogger(ReportGeneratorBean.class.getName());

	public ReportGeneratorBean() {
		super();
	}

	/**
	 * Business method
	 * 
	 * @ejb.interface-method view-type = "both"
	 */
	public ECReports generateReports(EventCycle eventCycle) throws ParseURIException {

		logger.info("*** ReportGeneratorBean:generateReports() is called ***");

		ECSpec spec = eventCycle.getSpec();
		ECReports result = new ECReports();
		result.setSpecName(eventCycle.getSpecName());
		result.setDate(eventCycle.getEnd());
		result.setTotalMilliseconds(eventCycle.getEnd().getTime() - eventCycle.getStart().getTime());
		result.setTerminationCondition(eventCycle.getTerminationCondition());

		if (spec.isIncludeSpecInReports()) {
			result.setECSpec(spec);
		}

		// tags for current event cycle
		List<ECTag> tags;
		tags = getTags(spec.getLogicalReaders().getLogicalReader(), eventCycle.getStart(), eventCycle.getEnd());

		// tags for previous event cycle
		List<ECTag> previousTags;
		previousTags = getTags(spec.getLogicalReaders().getLogicalReader(), eventCycle.getPreviousStart(), eventCycle
				.getPreviousEnd());

		logger.info("initial current ECTags:" + tags);
		logger.info("initial previous ECTags:" + previousTags);

		List<ECReport> reports = new Vector<ECReport>();

		if (spec.getReportSpecs() != null && spec.getReportSpecs().getReportSpec() != null) {
			logger.info("# of ReportSpecs =" + spec.getReportSpecs().getReportSpec().size());

			for (ECReportSpec reportSpec : spec.getReportSpecs().getReportSpec()) {
				ECReport report = new ECReport();
				report.setReportName(reportSpec.getReportName());
				logger.info("ReportSpec:" + reportSpec.getReportName());
				List<ECTag> filterTags = filterTags(tags, reportSpec.getFilterSpec());
				List<ECTag> filterPreviousTags = filterTags(previousTags, reportSpec.getFilterSpec());
				logger.info("filtered current ECTags:" + filterTags);
				logger.info("filtered previous ECTags:" + filterPreviousTags);

				if (reportSpec.isReportOnlyOnChange()) {
					if (filterTags.size() == filterPreviousTags.size() && filterTags.containsAll(filterPreviousTags)) {
						logger.info("omit because current=previous after filtering");
						continue;
					}
				}

				List<ECTag> tagSet = null;

				if (reportSpec.getReportSet().getSet() == ECReportSetEnum.CURRENT) {
					tagSet = filterTags;
					logger.info("CURRENT reportset ECTags:" + tagSet);
				} else if (reportSpec.getReportSet().getSet() == ECReportSetEnum.ADDITIONS) {
					filterTags.removeAll(filterPreviousTags);
					tagSet = filterTags;
					logger.info("ADDITIONS reportset ECTags:" + tagSet);
				} else if (reportSpec.getReportSet().getSet() == ECReportSetEnum.DELETIONS) {
					filterPreviousTags.removeAll(filterTags);
					tagSet = filterPreviousTags;
					logger.info("DELETIONS reportset ECTags:" + tagSet);
				}

				if (!reportSpec.isReportIfEmpty()) {
					if (tagSet.size() == 0) {
						logger.info("omit because tag set is empty");
						continue;
					}
				}

				List<ECReportGroup> reportGroup = groupTags(tagSet, reportSpec.getGroupSpec(), reportSpec.getOutput());
				logger.info("after group:" + reportGroup);
				report.unsetGroup();
				report.getGroup().addAll(reportGroup);
				reports.add(report);
			}
		}

		ECReportList reportList = new ECReportList();
		reportList.getReport().addAll(reports);
		result.setReports(reportList);

		logger.info("*** ReportGeneratorBean:generateReports() end. ***");

		return result;
	}

	/**
	 * @dao.call
	 */

	abstract protected List<ECTag> getTags(List readers, Date start, Date end);

	private List<ECTag> filterTags(List<ECTag> tags, ECFilterSpec filter) throws ParseURIException {
		List<ECTag> result = new Vector<ECTag>();
		List<PatternURI> includePatterns = new Vector<PatternURI>();
		List<PatternURI> excludePatterns = new Vector<PatternURI>();
		
		if (filter != null) {
			if( filter.getIncludePatterns()!=null ) {
				// conversion for List<String> to List<PatternURI>
				for (String str : filter.getIncludePatterns().getIncludePattern()) {
					includePatterns.add(new PatternURI(str));
				}
			}			

			if( filter.getExcludePatterns()!=null ) {
				for (String str : filter.getExcludePatterns().getExcludePattern()) {
					excludePatterns.add(new PatternURI(str));
				}
			}			
		}

		// includePatterns = filter.getIncludePatterns().getIncludePattern();
		// excludePatterns = filter.getExcludePatterns().getExcludePattern();

		for (ECTag tag : tags) {

			boolean matchOneInclude = false;
			boolean matchOneExclude = false;

			if (includePatterns == null || includePatterns.isEmpty()) {
				matchOneInclude = true;
			} else {
				for (PatternURI includePattern : includePatterns) {
					if (tag.matchesPattern(includePattern)) {
						matchOneInclude = true;
						break;
					}
				}
			}

			if (excludePatterns == null) {
				matchOneExclude = false;
			} else {
				for (PatternURI excludePattern : excludePatterns) {
					if (tag.matchesPattern(excludePattern)) {
						matchOneExclude = true;
						break;
					}
				}
			}

			if (matchOneInclude && !matchOneExclude) {
				result.add(tag);
			}
		}
		return result;
	}

	private List<ECReportGroup> groupTags(List<ECTag> tags, ECGroupSpec group, ECReportOutputSpec output)
			throws ParseURIException {
		List<ECReportGroup> result = new Vector<ECReportGroup>();
		Hashtable<String, ECReportGroup> grouping = new Hashtable<String, ECReportGroup>();
		List<PatternURI> patternList = new Vector<PatternURI>();

		if (group != null) {
			// conversion for List<String> to List<PatternURI>
			for (String str : group.getPattern()) {
				patternList.add(new PatternURI(str));
			}
			// patternList = group.getPattern();
		}

		if (group == null || patternList == null || patternList.isEmpty()) {
			ECReportGroup reportGroup = new ECReportGroup();
			reportGroup.setGroupName("null");
			grouping.put("null", reportGroup);
			ECReportGroup defaultGroup = grouping.get("null");
			for (ECTag tag : tags) {
				addTagToGroup(defaultGroup, tag, output);
			}
		} else {
			// assuming the group pattern list is disjoint after validation
			for (ECTag tag : tags) {
				boolean matchGroup = false;
				ECReportGroup reportGroup = null;

				for (PatternURI pattern : patternList) {
					if (tag.matchesGroup(pattern)) {
						String groupName = pattern.formGroupName(tag);
						reportGroup = grouping.get(groupName);
						if (reportGroup == null) {
							reportGroup = new ECReportGroup();
							reportGroup.setGroupName(groupName);
							grouping.put(groupName, reportGroup);
						}
						matchGroup = true;
						break;
					}
				}

				if (!matchGroup) {
					reportGroup = grouping.get("null");
					if (reportGroup == null) {
						reportGroup = new ECReportGroup();
						reportGroup.setGroupName("null");
						grouping.put("null", reportGroup);
					}

				}
				addTagToGroup(reportGroup, tag, output);
			}
		}
		result.addAll(grouping.values());
		return result;
	}

	private void addTagToGroup(ECReportGroup group, ECTag tag, ECReportOutputSpec output) {
		if (!output.isIncludeEPC() && !output.isIncludeTag() && !output.isIncludeRawHex()
				&& !output.isIncludeRawDecimal()) {
			group.setGroupList(null);
		} else {
			// add the tag to the groupList
			ECReportGroupList groupList = group.getGroupList();

			if (groupList == null) {
				groupList = new ECReportGroupList();
				group.setGroupList(groupList);
			}

			List<ECReportGroupListMember> members = groupList.getMember();

			ECReportGroupListMember member = new ECReportGroupListMember();

			if (output.isIncludeEPC()) {
				EPC epc = new EPC();
				epc.setValue(tag.toEPCURI());
				member.setEpc(epc);
			}

			if (output.isIncludeTag()) {
				EPC epc = new EPC();
				epc.setValue(tag.toTagURI());
				member.setTag(epc);
			}

			if (output.isIncludeRawHex()) {
				EPC epc = new EPC();
				epc.setValue(tag.toRawHexURI());
				member.setRawHex(epc);
			}

			if (output.isIncludeRawDecimal()) {
				EPC epc = new EPC();
				epc.setValue(tag.toRawDecimalURI());
				member.setRawDecimal(epc);
			}

			members.add(member);
		}

		if (!output.isIncludeCount()) {
			group.setGroupCount(null);
		} else {
			// add the tag to the groupCount
			ECReportGroupCount groupCount = group.getGroupCount();

			if (groupCount == null) {
				groupCount = new ECReportGroupCount();
				group.setGroupCount(groupCount);
			}

			groupCount.setCount(groupCount.getCount() + 1);
		}
	}
}
