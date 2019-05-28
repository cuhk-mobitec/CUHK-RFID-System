// $Id: EventCycle.java,v 1.1 2006/06/16 16:49:18 alfred Exp $

package cuhk.ale;

import java.io.Serializable;
import java.util.Date;
import epcglobal.ale.*;


// this class defines the start and end of an EventCycle
 
public class EventCycle implements Serializable {

	private static final long serialVersionUID = 1158943980426663351L;
	private String specName;
	private ECSpec spec;
	private Date start;
	private Date end;
	private Date previousStart;
	private Date previousEnd;
	private ECTerminationCondition terminationCondition;
	
	public EventCycle(String specName, ECSpec spec, Date start, Date end, Date previousStart, Date previousEnd, ECTerminationCondition terminationCondition) {
		super();
		this.specName = specName;
		this.spec = spec;
		this.start = start;
		this.end = end;
		this.previousStart = previousStart;
		this.previousEnd = previousEnd;
		this.terminationCondition = terminationCondition;
	}

	public EventCycle(String specName, ECSpec spec, Long startTime, Long endTime, Long previousStartTime, Long previousEndTime, ECTerminationCondition terminationCondition) {
		this(specName, spec, new Date(startTime), new Date(endTime), new Date(previousStartTime), new Date (previousEndTime), terminationCondition);
	}

	public Date getEnd() {
		return end;
	}

	public Date getPreviousEnd() {
		return previousEnd;
	}

	public Date getPreviousStart() {
		return previousStart;
	}

	public ECSpec getSpec() {
		return spec;
	}

	public String getSpecName() {
		return specName;
	}

	public Date getStart() {
		return start;
	}

	public ECTerminationCondition getTerminationCondition() {
		return terminationCondition;
	}
}
