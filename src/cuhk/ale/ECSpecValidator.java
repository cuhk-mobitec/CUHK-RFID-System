/**
 * $Id: ECSpecValidator.java,v 1.3 2006/09/20 16:36:31 kyyu Exp $
 */
package cuhk.ale;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import cuhk.ale.ejb.interfaces.ReaderManagerUtil;
import cuhk.ale.exceptions.ECSpecValidationException;
import cuhk.ale.exceptions.ParseURIException;
import epcglobal.ale.ECBoundarySpec;
import epcglobal.ale.ECReportOutputSpec;
import epcglobal.ale.ECReportSpec;
import epcglobal.ale.ECSpec;

/**
 * @version $Id: ECSpecValidator.java,v 1.3 2006/09/20 16:36:31 kyyu Exp $
 * @author kyyu
 * 
 */
public class ECSpecValidator {
	
	static Logger logger = Logger.getLogger(ECSpecValidator.class.getName( ));

	static public void validate(ECSpec ecspec) throws ECSpecValidationException {

			// line 645 check null values
			if (ecspec.getBoundarySpec() == null) {
				throw new ECSpecValidationException("BoundarySpec is null.");
			}
			if (ecspec.getLogicalReaders() == null || !ecspec.getLogicalReaders().isSetLogicalReader()) {
				throw new ECSpecValidationException("Readers is null or empty.");
			}
			if (ecspec.getReportSpecs() == null || !ecspec.getReportSpecs().isSetReportSpec()) {
				throw new ECSpecValidationException("ReportSpec is null or empty.");
			}
	
			ECBoundarySpec boundaries = ecspec.getBoundarySpec();
			List<ECReportSpec> reportSpecs = ecspec.getReportSpecs().getReportSpec();
	
			long lDuration = (boundaries.getDuration()) == null ? 0 : boundaries.getDuration().getValue();
			long lStableSetInterval = (boundaries.getStableSetInterval()) == null ? 0 : boundaries.getStableSetInterval()
					.getValue();
			long lRepeatPeriod = (boundaries.getRepeatPeriod()) == null ? 0 : boundaries.getRepeatPeriod().getValue();
			boolean isSetStartTrigger = (boundaries.getStartTrigger()) == null ? false : (boundaries.getStartTrigger()
					.getValue() != null);
			boolean isSetStopTrigger = (boundaries.getStopTrigger()) == null ? false : (boundaries.getStopTrigger()
					.getValue() != null);
	
			// line 949
			// readers in all logical reader
			
			try {
				List<String> logicalReaders = ReaderManagerUtil.getLocalHome().create().retrieveLogicalReaderNames();
				for( String readerid : ecspec.getLogicalReaders().getLogicalReader()) {
					if (!logicalReaders.contains(readerid)) {
						throw new ECSpecValidationException("Logical reader: " + readerid + " is not known to middleware server");
					}
				}				
			} catch (NamingException e) {
				logger.warn("Cannot validate by reader name because not connected to applicatoin server.");
			} catch (CreateException e) {
				logger.warn("Cannot validate by reader name because applicatoin server refuse to create ejb");
			} catch (ECSpecValidationException e ){
				throw e;				
			} catch (Exception e) {
				logger.warn("Error when connecting to application server");
			}
	
	
			// line 951
			// StartTrigger, StopTrigger conform to URI in RFC2396
			if (isSetStartTrigger) {
				try {
					new URI(boundaries.getStartTrigger().getValue());
				} catch (URISyntaxException e) {
					throw new ECSpecValidationException("StartTrigger is not conform to RFC2396.");
				}
			}
	
			if (isSetStopTrigger) {
				try {
					new URI(boundaries.getStopTrigger().getValue());
				} catch (URISyntaxException e) {
					throw new ECSpecValidationException("StopTrigger is not conform to RFC2396.");
				}
			}
	
			// line 954
			// duration, SSI, repeat is not negative
			if (lDuration < 0) {
				throw new ECSpecValidationException("Duration is negative.");
			}
			if (lStableSetInterval < 0) {
				throw new ECSpecValidationException("StableSetInterval is negative.");
			}
			if (lRepeatPeriod < 0) {
				throw new ECSpecValidationException("RepeatPeriod is negative.");
			}
	
			// line 956
			// NOT: startTrigger is not empty and repeatPeriod is non-zero
	
			if (isSetStartTrigger && lRepeatPeriod != 0) {
				throw new ECSpecValidationException("StartTrigger is not empty and repeatPeriod is non-zero.");
			}
	
			// line 958
			// NOT: neither stopTrigger nor duration nor SSI nor any vendor
			// extension specified.
			if (!isSetStopTrigger && lDuration == 0 && lStableSetInterval == 0) {
				throw new ECSpecValidationException("No stopping condition is specified.");
			}
	
			// line 961
			// ECReportSpec should not be empty
			if (reportSpecs.isEmpty()) {
				throw new ECSpecValidationException("ECReportSepc is empty");
			}
	
			// line 962
			// no two ECReportspec have identical values for name
			HashSet<String> reportNames = new HashSet<String>();
			for (ECReportSpec reportSpec : reportSpecs) {
				if (reportNames.contains(reportSpec.getReportName())) {
					throw new ECSpecValidationException("Two ECReportspec have identical values.");
				}
				reportNames.add(reportSpec.getReportName());
			}
	
			// line 964
			// ECFilterSpec conform to EPC URI pattern syntax
			//
			// line 966
			// ECGroupSpec conform to EPC URI grouping pattern syntax as in
			// section 8.2.9
			for (ECReportSpec reportSpec : reportSpecs) {
				if (reportSpec.getFilterSpec() != null && reportSpec.getFilterSpec().getExcludePatterns() != null) {
					for (String excludePattern : reportSpec.getFilterSpec().getExcludePatterns().getExcludePattern()) {
						try {
							new PatternURI(excludePattern);
							// FIXME: remove when checking is done in patternURI
							new URI(excludePattern); 
						} catch (ParseURIException e) {
							throw new ECSpecValidationException(e.getMessage());
						} catch (URISyntaxException e) {
							throw new ECSpecValidationException("ExcludePattern is not conform to RFC2396.");						}
					}
				}
	
				if (reportSpec.getFilterSpec() != null && reportSpec.getFilterSpec().getIncludePatterns() != null) {
					for (String includePattern : reportSpec.getFilterSpec().getIncludePatterns().getIncludePattern()) {
						try {
							new PatternURI(includePattern);
							// FIXME: remove when checking is done in patternURI							
							new URI(includePattern);
						} catch (ParseURIException e) {
							throw new ECSpecValidationException(e.getMessage());
						} catch (URISyntaxException e) {
							throw new ECSpecValidationException("IncludePattern is not conform to RFC2396.");
						}
					}
				}
				if (reportSpec.getGroupSpec() != null) {
					for (String groupPattern : reportSpec.getGroupSpec().getPattern())
						try {
							new PatternURI(groupPattern);
							// FIXME: remove when checking is done in patternURI							
							new URI(groupPattern);
						} catch (ParseURIException e) {
							throw new ECSpecValidationException(e.getMessage());
						} catch (URISyntaxException e) {
							throw new ECSpecValidationException("GroupPattern is not conform to RFC2396.");
						}
				}
			}
	
			// line 968
			// FIXME ECGroupSpecs are non-disjoint as in section 8.2.9
			// non-disjoing: for any i, j
			// type_i = type_j and all field_i_k and field_j_k are non disjoint.
			// 
	
			// line 970
			// In all ECReportSpec, ECReportOutputSpec must have output type
			// specified.
			for (ECReportSpec reportSpec : reportSpecs) {
				ECReportOutputSpec reportOutputSpec = reportSpec.getOutput();
				if (!(reportOutputSpec.isIncludeCount() || reportOutputSpec.isIncludeEPC()
						|| reportOutputSpec.isIncludeRawDecimal() || reportOutputSpec.isIncludeRawHex() || reportOutputSpec
						.isIncludeTag())) {
					throw new ECSpecValidationException("ECReportOutputSpec must have output type specified.");
				}
	
			}
	}
}
