/**
 * ECSpecInstanceBean.java
 * $Id: ECSpecInstanceBean.java,v 1.3 2006/09/18 14:31:13 kyyu Exp $
 *  
 * Task List:
 * - timer transaction type.
 * - timer skew. 
 * - how to run this ejb in higher priority .
 */

package cuhk.ale.ejb;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.NoSuchObjectLocalException;
import javax.ejb.RemoveException;
import javax.ejb.TimedObject;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.jboss.ejb.txtimer.TimerImpl;

import cuhk.ale.ECSpecEventCallback;
import cuhk.ale.ECSpecState;
import cuhk.ale.ECTag;
import cuhk.ale.EventCycle;
import cuhk.ale.NotifyRequest;
import cuhk.ale.ejb.interfaces.ECSpecInstancePK;
import cuhk.ale.ejb.interfaces.ReportGenerator;
import cuhk.ale.ejb.interfaces.ReportGeneratorHome;
import cuhk.ale.ejb.interfaces.ReportGeneratorUtil;
import cuhk.ale.exceptions.ECSpecInvalidStateException;
import cuhk.ale.exceptions.ImplementationException;
import epcglobal.ale.ECReports;
import epcglobal.ale.ECSpec;
import epcglobal.ale.ECTerminationCondition;

/**
 * @version $Id: ECSpecInstanceBean.java,v 1.3 2006/09/18 14:31:13 kyyu Exp $
 * @author kyyu
 * 
 * @ejb.value-object match="light"
 * @ejb.dao impl-class = "cuhk.ale.dao.ECSpecInstanceDAOImpl"
 * @ejb.bean name="ECSpecInstance" transaction-type = "Container" display-name="Name for
 *           ECSpecInstance" description="Description for ECSpecInstance"
 *           jndi-name="ejb/ECSpecInstance" type="BMP" view-type="local" local-jndi-name =
 *           "ejb/ECSpecInstanceLocal"
 * 
 * @ejb.transaction type = "Required"
 * 
 * @ejb.resource-ref res-ref-name = "mySQLDS" res-type = "javax.sql.DataSource" res-auth =
 *                   "Container"
 * 
 * @jboss.resource-ref jndi-name = "java:/mySQLDS" res-ref-name = "mySQLDS"
 *
 * @jboss.method-attributes pattern = "get*" read-only = "true"
 *
 */
public abstract class ECSpecInstanceBean implements TimedObject, EntityBean {

	private EntityContext ctx;

	static final Logger logger = Logger.getLogger(ECSpecInstanceBean.class.getName());

	public enum TimerType {
		DURATION, SSI, REPEAT
	};

	static final long WARMUPTIME = 5L;

	public void setEntityContext(javax.ejb.EntityContext ctx) {
		this.ctx = ctx;
	}

	/**
	 * 
	 */
	public ECSpecInstanceBean() {
		super();
	}

	/**
	 * 
	 * @return
	 * @ejb.persistent-field
	 * @ejb.value-object match = "light"
	 * @ejb.pk-field
	 * @ejb.interface-method view-type="local"
	 */
	public abstract String getSpecName();

	/**
	 * @param value
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setSpecName(String value);

	/**
	 * 
	 * @return
	 * @ejb.persistent-field
	 * @ejb.value-object match = "light"
	 * @ejb.interface-method view-type="local"
	 */
	public abstract long getStartTime();

	/**
	 * @param l
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setStartTime(long l);

	/**
	 * 
	 * @return
	 * @ejb.persistent-field
	 * @ejb.value-object match = "light"
	 * @ejb.interface-method view-type="local"
	 */
	public abstract long getPreviousStartTime();

	/**
	 * @param l
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setPreviousStartTime(long l);

	/**
	 * 
	 * @return
	 * @ejb.persistent-field
	 * @ejb.value-object match = "light"
	 * @ejb.interface-method view-type="local"
	 */
	public abstract long getPreviousEndTime();

	/**
	 * @param l
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setPreviousEndTime(long l);

	/**
	 * 
	 * @return
	 * @ejb.persistent-field
	 * @ejb.value-object match = "light"
	 * @ejb.interface-method view-type="local"
	 */
	public abstract int getStateVersion();

	/**
	 * @param value
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setStateVersion(int value);

	/**
	 * @return
	 * @param pk
	 * @throws FinderException
	 * @ejb.interface-method view-type="local"
	 */
	public abstract ECSpecInstancePK ejbFindByPrimaryKey(ECSpecInstancePK pk) throws FinderException;

	/**
	 * @param arg0
	 * @ejb.interface-method view-type="local"
	 * @ejb.transaction type="RequiresNew"
	 * 
	 * Use NotSupported or RequiresNew, according to documentation.
	 */
	public void ejbTimeout(Timer timer) {

		try {
			ECSpecInstanceBean.logger.debug("ejbTimeout " + timer.getInfo().toString() + " "
					+ timer.getNextTimeout() + " REMAINING " + timer.getTimeRemaining());
			TimerType type = (TimerType) timer.getInfo();
			timer.cancel();

			ECSpec ecspec = getECSpec();
			String specName = getSpecName();

			switch (type) {
			case DURATION:
				// LINE 692 case 1
				ECSpecInstanceBean.logger.info(specName + " DURATION reached");
				doActiveToRequestState(ECTerminationCondition.DURATION);
				break;
			case SSI:
				if (getState() == ECSpecState.ACTIVE) {

					long lastSSI = System.currentTimeMillis();
					// FIXME GET LAST SSI , NOT LAST START
					long i = CalculateSSI(getStartTime(), lastSSI, ecspec.getLogicalReaders().getLogicalReader());

					if (i >= retrieveSSI(ecspec)) { // SSI EMPTY
						// LINE 693 case 2
						ECSpecInstanceBean.logger.info(specName + " SSI reached");
						doActiveToRequestState(ECTerminationCondition.STABLE_SET);
					} else {
						// there are some tags
						if (getState() == ECSpecState.ACTIVE) {
							createTimer(TimerType.SSI, retrieveSSI(ecspec)
									- i);
						}
					}
				}

				break;
			case REPEAT:
				if (getState() == ECSpecState.REQUESTED) {
					// LINE 684 - case 2.2
					ECSpecInstanceBean.logger.info(specName + " REPEAT_PERIOD reached");
					doRequestToActiveState();
				}
				break;
			default:
				break;
			}
		} catch (NoSuchObjectLocalException e) {
			ECSpecInstanceBean.logger.debug(e);
			TimerService service = ctx.getTimerService();

			// TODO this code is for debug only
			{
				Collection<TimerImpl> list = service.getTimers();
				for (TimerImpl tmr : list) {
					ECSpecInstanceBean.logger.debug(tmr.getTimerId() + " " + tmr.getInfoInternal());
				}
			}
		}
	}

	private long retrieveDuration(ECSpec ecspec) {
		if (ecspec != null && ecspec.getBoundarySpec() != null && ecspec.getBoundarySpec().getDuration()!= null )
		return ecspec.getBoundarySpec().getDuration().getValue();
		else
			return 0;
	}
	
	private long retrieveSSI(ECSpec ecspec) {
		if (ecspec != null && ecspec.getBoundarySpec() != null && ecspec.getBoundarySpec().getStableSetInterval()!= null )
			return ecspec.getBoundarySpec().getStableSetInterval().getValue();
		else
			return 0;
	}
	
	private long retrieveRepeatPeriod(ECSpec ecspec) {
		if (ecspec != null && ecspec.getBoundarySpec() != null && ecspec.getBoundarySpec().getRepeatPeriod()!= null )
			return ecspec.getBoundarySpec().getRepeatPeriod().getValue();
		else 
			return 0;
	}


	/**
	 * Create method
	 * 
	 * @ejb.create-method view-type = "local"
	 */
	public cuhk.ale.ejb.interfaces.ECSpecInstancePK ejbCreate(String specName, ECSpec spec)
			throws javax.ejb.CreateException {

		setSpecName(specName);
		setECSpec(spec);
		setState(ECSpecState.UNREQUESTED);
		setStartTime(0L);

		// FIXME what is the initial value?
		setPreviousStartTime(0L);
		setPreviousEndTime(0L);

		// simply return null, child will handle the Primary key issue.
		return null;
	}

	/**
	 */
	public void ejbRemove() throws RemoveException, EJBException, RemoteException {

	}

	/**
	 * 
	 * @ejb.persistent-field
	 * @ejb.value-object match = "light"
	 * @ejb.interface-method view-type="local"
	 */
	public abstract ECSpec getECSpec();

	/**
	 * 
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setECSpec(ECSpec value);

	/**
	 * 
	 * @ejb.persistent-field
	 * @ejb.value-object match = "light"
	 * @ejb.interface-method view-type="local"
	 */
	public abstract int getState();

	/**
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setState(int value);

	/**
	 * @ejb.interface-method view-type="local"
	 */
	public abstract cuhk.ale.valueobjects.ECSpecInstanceValue getECSpecInstanceValue();

	/**
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setECSpecInstanceValue(cuhk.ale.valueobjects.ECSpecInstanceValue value);

	/**
	 * @ejb.interface-method view-type="local" comment out ejb.transaction type = "NotSupported"
	 */
	public void createTimer(TimerType type, long duration) {
		// System.out.println("TEST start: " + new Date());
		TimerService service = ctx.getTimerService();

		// In case the system is extremely overload,
		// the timer will be in EXPIRE status when calling ejbTimeout.
		// This is very undesirable but we still have to handle this.
		// Thus we set the timer interval
		// (2nd parameter - which will repeat the timer
		// once the first date is reached.)

		// service.createTimer(duration, 3000, type);

		// FIXME disable repeatation.
		service.createTimer(duration, type);

		ECSpecInstanceBean.logger.info("createTimer " + type + " " + duration);
		ECSpecInstanceBean.logger.info("- - - - timers count : " + service.getTimers().size());

		Collection<Timer> list = service.getTimers();

		for (Timer tmr : list) {
			ECSpecInstanceBean.logger.debug(tmr.getInfo() + ", next timeout " + tmr.getNextTimeout());

		}

		// System.out.println("TEST set: " + new Date());
	}

	/**
	 * @ejb.interface-method view-type="local" comment out ejb.transaction type = "NotSupported"
	 */
	public void cancelTimer(TimerType type) {
		ECSpecInstanceBean.logger.debug("cancelTimer " + type);
		try {
			TimerService service = ctx.getTimerService();
			Collection<Timer> list = service.getTimers();
			for (Timer tmr : list) {
				// ECSpecInstanceBean.logger.debug(tmr.getInfo() + " " + tmr.getNextTimeout());
				if (tmr.getInfo() == type) {
					ECSpecInstanceBean.logger.debug("cancelled timer " + type);
					tmr.cancel();
				}
			}
		} catch (Exception e) {
			ECSpecInstanceBean.logger.error("Exception after cancelling timer : " + e.toString());
		}

		return;
	}

	/**
	 */
	public Timer getTimer(TimerType type) {
		ECSpecInstanceBean.logger.debug("getTimer " + type);
		TimerService service = ctx.getTimerService();

		Collection<Timer> list = service.getTimers();
		for (Timer tmr : list) {
			if (tmr.getInfo() == type) {
				return tmr;
			}
		}
		return null;
	}

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	public void subscribe(String notificationUrl) {
		ECSpecInstanceBean.logger.debug("subscribe [" + notificationUrl + "]");

		addNotificationURL(getSpecName(), notificationUrl);

		int state = getState();
		if (state == ECSpecState.UNREQUESTED) {
			doUnrequestToRequestState();
		} else {
			// nothing
		}

	}

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	public void unsubscribe(String notificationUrl) {
		ECSpecInstanceBean.logger.debug("unsubscribe [" + notificationUrl + "]");

		// List notificationUrls = getNotificationURLs();
		// notificationUrls.remove(notificationUrl);
		// setNotificationURLs(notificationUrls);
		deleteNotificationURL(getSpecName(), notificationUrl);
		if (getNotificationURLs(getSpecName()).isEmpty()) {
			switch (getState()) {
			case ECSpecState.ACTIVE:
				// LINE 706 case 4
				doActiveToRequestState(ECTerminationCondition.UNREQUEST);
				break;
			case ECSpecState.REQUESTED:
				doRequestToUnrequestState();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * @dao.call
	 * @ejb.interface-method view-type = "local"
	 */
	public abstract java.util.List<String> getNotificationURLs(String specName);

	/**
	 * @dao.call
	 * @ejb.interface-method view-type = "local"
	 */
	protected abstract void addNotificationURL(String specName, String url);

	/**
	 * @dao.call
	 * @ejb.interface-method view-type = "local"
	 */
	protected abstract void deleteNotificationURL(String specName, String url);

	// /**
	// * We should never do this, right ?
	// */
	// private void doActiveToUnrequestState() {
	// int state = getState();
	// if (state == ECSpecState.ACTIVE) {
	// setState(ECSpecState.REQUESTED);
	// doRequestToUnrequestState();
	// } else {
	// throw new ECSpecInvalidStateException();
	// }
	// }

	private void doActiveToRequestState(ECTerminationCondition terminationCondition) {
		ECSpecInstanceBean.logger.info(" ACTIVE  ->  REQUEST State [START]");

		ECSpec ecspec = getECSpec();
		String specName = getSpecName();

		Long startTime = getStartTime();
		Long endTime = System.currentTimeMillis();
		Long previousStartTime = getPreviousStartTime();
		Long previousEndTime = getPreviousEndTime();

		int state = getState();

		if (state == ECSpecState.ACTIVE) {
			setState(ECSpecState.REQUESTED);
			setPreviousStartTime(startTime);
			setPreviousEndTime(endTime);

			try {

				// FIXME until report generator is implemented
				// ECReports reports = new ECReports(new ArrayList());

				ReportGeneratorHome reportGeneratorHome = ReportGeneratorUtil.getHome();
				ReportGenerator reportGenerator = reportGeneratorHome.create();
				EventCycle eventCycle = new EventCycle(specName, ecspec, startTime, endTime,
						previousStartTime, previousEndTime, terminationCondition);
				ECReports reports = reportGenerator.generateReports(eventCycle);
				logger.info("CHECK reportGenerator.generateReports");
				notifySubscribers(reports);

			} catch (NamingException e) {
				// FIXME , FLOW ERROR
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (CreateException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// FIXME CATCHING THIS EXCEPTION IS NOT A GOOD IDEA
				e.printStackTrace();
			}

			cancelTimer(TimerType.DURATION);
			cancelTimer(TimerType.SSI);

			// Here we have already gone to request state.

			if (getNotificationURLs(specName).isEmpty()) {
				// to unrequest directly!
				doRequestToUnrequestState();
			} else {
				if (ecspec.getBoundarySpec().getStartTrigger() == null
						&& retrieveRepeatPeriod(ecspec) == 0) {
					// LINE 689 case 3.2
					doRequestToActiveState();
				} else if (ecspec.getBoundarySpec().getStartTrigger() == null) {
					Timer repeatTimer = getTimer(TimerType.REPEAT);
					if (repeatTimer == null) {
						// LINE 684 case 2.2
						doRequestToActiveState();
					}
				}
			}
		} // end getState()= ACTIVE
		ECSpecInstanceBean.logger.info(" ACTIVE  ->  REQUEST State [END]");
	}


	private void doRequestToUnrequestState() {
		ECSpecInstanceBean.logger.info(" REQUEST  ->  UNREQUEST State ");
		int state = getState();
		ECSpec ecspec = getECSpec();
		if (state == ECSpecState.REQUESTED) {
			setState(ECSpecState.UNREQUESTED);
			deactivateReaders(ecspec.getLogicalReaders().getLogicalReader());
		} else {
			throw new ECSpecInvalidStateException("Request to Unrequest failed.");
		}
	}

	private void doRequestToActiveState() {
		ECSpecInstanceBean.logger.info(" REQUEST  ->  ACTIVE State [START]");
		ECSpec ecspec = getECSpec();
		int state = getState();
		if (state == ECSpecState.REQUESTED) {
			setState(ECSpecState.ACTIVE);
			setStartTime(System.currentTimeMillis());
			// FIXME: ACTIVATE READERS.
			activateReaders(ecspec.getLogicalReaders().getLogicalReader());
			// FIXME: CREATE TIMERS
			if (retrieveDuration(ecspec) > 0) {
				createTimer(TimerType.DURATION, retrieveDuration(ecspec));
			}
			if (retrieveSSI(ecspec) > 0) {
				createTimer(TimerType.SSI, retrieveSSI(ecspec));
			}
			if (retrieveRepeatPeriod(ecspec) > 0) {
				createTimer(TimerType.REPEAT, retrieveRepeatPeriod(ecspec));
			}
		} else {
			throw new ECSpecInvalidStateException("Request to Active failed.");
		}
		ECSpecInstanceBean.logger.info(" REQUEST  ->  ACTIVE State [END]");

	}


	private void doUnrequestToRequestState() {
		ECSpecInstanceBean.logger.info(" UNREQUEST  ->  REQUEST State ");
		int state = getState();
		ECSpec ecspec = getECSpec();
		if (state == ECSpecState.UNREQUESTED) {
			setState(ECSpecState.REQUESTED);
			if (ecspec.getBoundarySpec().getStartTrigger() == null) {
				// LINE 683 case 2.1
				doRequestToActiveState();
			}
		} else {
			throw new ECSpecInvalidStateException("Unrequest to Request failed.");
		}

	}

	// /**
	// * We should never do this, right ?
	// */
	// private void doUnrequestToActiveState() {
	// ECSpecInstanceBean.logger.info(" UNREQUEST -> ACTIVE ( from REQUEST ) State ");
	// int state = getState();
	// if (state == ECSpecState.UNREQUESTED) {
	// setState(ECSpecState.REQUESTED);
	// doRequestToActiveState();
	// } else {
	// throw new ECSpecInvalidStateException();
	// }
	// }

	private void activateReaders(List readers) {
		// FIXME READER ADAPTER
	}

	private void deactivateReaders(List readers) {
		// FIXME READER ADAPTER
	}

	// private void generateReports(int eventcycle) {
	// }

	private void notifySubscribers(ECReports reports) {
		List<String> notificationURLs = getNotificationURLs(getSpecName());
		List<URI> urlsToSend = new ArrayList();
		ECSpecInstanceBean.logger.debug("notifySubscribers");
		String specName = getSpecName();

		for (String url : notificationURLs) {
			if (url.startsWith("__JMS__")) {
				ECSpecInstanceBean.logger.debug("JMS to be notified : " + url);
				try {
					ECSpecEventCallback.sendMessage((Serializable) reports, "specName", getSpecName());
				} catch (ImplementationException e) {
					// FIXME
					e.printStackTrace();
				}
			} else if (reports.getReports().isSetReport()) {
				ECSpecInstanceBean.logger.info(specName + " - will notify : " + url);
				try {
					urlsToSend.add(new URI(url));
				} catch (URISyntaxException e) {
					// FIXME
					e.printStackTrace();
				}
			}
		}

		if (!urlsToSend.isEmpty()) {
			NotifyRequest nr = new NotifyRequest(reports, urlsToSend);
			try {
				nr.sendMessage();
			} catch (NamingException e) {
				// FIXME
				e.printStackTrace();
			} catch (JMSException e) {
				// FIXME
				e.printStackTrace();
			}
//			try {
//				QueueConnection queueConnection = NotifierUtil.getQueueConnection();
//				QueueSession session = queueConnection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
//				QueueSender sender = session.createSender(NotifierUtil.getQueue());
//				NotifyRequest nr = new NotifyRequest(reports, urlsToSend);
//				ObjectMessage message = session.createObjectMessage((Serializable) reports);
//				sender.send(message);
//				session.commit();
//				session.close();
//				queueConnection.close();
//
//			} catch (NamingException e) {
//				// FIXME
//				e.printStackTrace();
//			} catch (JMSException e) {
//				// FIXME
//				e.printStackTrace();
//			}
//			logger.info(specName + " has reports: \n" + reports.getReports());
		}

	}

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	public void startTriggerReceived() {
		ECSpecInstanceBean.logger.debug("startTriggerReceived");
		int state = getState();
		if (state == ECSpecState.REQUESTED) {
			// LINE 680 case 1.1
			doRequestToActiveState();
		}
	}

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	public void stopTriggerReceived() {
		ECSpecInstanceBean.logger.debug("stopTriggerReceived");
		int state = getState();
		if (state == ECSpecState.ACTIVE) {
			// LINE 705 case 3
			doActiveToRequestState(ECTerminationCondition.TRIGGER);
		}
	}

	/**
	 * Home method
	 * 
	 * @ejb.home-method view-type = "local"
	 * @dao.call
	 */
	public abstract java.util.List ejbHomeGetSpecNames();

	/**
	 * @dao.call
	 */
	public abstract List<ECTag> getTagRead(List readers, long lastStart, long lastSSI);

	private long CalculateSSI(long lastStart, long lastSSI, List readers) {

		List<ECTag> tags = getTagRead(readers, lastStart, lastSSI);

		List<ECTag> tags_before = getTagRead(readers, lastStart - WARMUPTIME, lastStart);

		// sorting is not needed....
		Collections.sort(tags, new Comparator<ECTag>() {
			public int compare(ECTag a, ECTag b) {
				long orderA = a.getTimestamp();
				long orderB = b.getTimestamp();
				return (int) (orderA - orderB);
			}
		});

		Map<String, ECTag> tag_pool = new HashMap<String, ECTag>();

		for (ECTag tag : tags_before) {
			tag_pool.put(tag.getTagId(), tag);
		}

		long lastTagTime = lastStart;
		for (ECTag tag : tags) {
			if (!tag_pool.containsKey(tag.getTagId())) {
				tag_pool.put(tag.getTagId(), tag);
				lastTagTime = (lastTagTime > tag.getTimestamp()) ? lastTagTime : tag.getTimestamp();
			}
		}

		return lastTagTime - lastStart;

	}

}
