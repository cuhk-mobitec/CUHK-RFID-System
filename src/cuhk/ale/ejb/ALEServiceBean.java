/**
 * ALEServiceBean.java
 * $Id: ALEServiceBean.java,v 1.1 2006/06/16 16:49:19 alfred Exp $
 *  
 */
package cuhk.ale.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import cuhk.ale.ECSpecEventCallback;
import cuhk.ale.ECSpecValidator;
import cuhk.ale.ejb.interfaces.ALEServiceUtil;
import cuhk.ale.ejb.interfaces.ECSpecInstanceLocal;
import cuhk.ale.ejb.interfaces.ECSpecInstanceLocalHome;
import cuhk.ale.ejb.interfaces.ECSpecInstancePK;
import cuhk.ale.ejb.interfaces.ECSpecInstanceUtil;
import epcglobal.ale.*;
import cuhk.ale.exceptions.*;;

/**
 * @version $Id: ALEServiceBean.java,v 1.1 2006/06/16 16:49:19 alfred Exp $
 * 
 * @ejb.bean name="ALEService" view-type = "both" display-name="Name for ALEService"
 *           description="Description for ALEService" type="Stateless" jndi-name = "ejb/ALEService"
 * 
 * 
 */
public abstract class ALEServiceBean implements SessionBean {

	static final Logger logger = Logger.getLogger(ALEServiceBean.class.getName());

	public ALEServiceBean() {
		super();
	}

	/**
	 * 
	 * @param specName
	 * @param spec
	 * @throws NamingException
	 * @ejb.interface-method view-type = "both"
	 */
	public void define(String specName, epcglobal.ale.ECSpec spec) throws cuhk.ale.exceptions.DuplicateNameException,
	cuhk.ale.exceptions.ECSpecValidationException, cuhk.ale.exceptions.SecurityException, cuhk.ale.exceptions.ImplementationException {

		ALEServiceBean.logger.info(specName + " ALE_define ");

		ECSpecValidator.validate(spec);

		try {
			ECSpecInstanceLocalHome ecspecInstanceLocalHome = ECSpecInstanceUtil.getLocalHome();
			ecspecInstanceLocalHome.create(specName, spec);
		} catch (EJBException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (CreateException e) {
			throw new DuplicateNameException(e.getMessage());
		} catch (NamingException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		}
	}

	/**
	 * 
	 * @param specName
	 * @ejb.interface-method view-type = "both"
	 */
	public void undefine(String specName) throws cuhk.ale.exceptions.NoSuchNameException,
			cuhk.ale.exceptions.SecurityException, cuhk.ale.exceptions.ImplementationException {

		ALEServiceBean.logger.info(specName + " ALE_undefine ");
		try {
			ECSpecInstanceLocalHome ecspecInstanceLocalHome = ECSpecInstanceUtil.getLocalHome();
			ECSpecInstanceLocal ecspecInstanceLocal = ecspecInstanceLocalHome
					.findByPrimaryKey(new ECSpecInstancePK(specName));
			ecspecInstanceLocal.remove();

		} catch (NamingException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (FinderException e) {
			throw new NoSuchNameException(e.getMessage());
		} catch (EJBException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (RemoveException e) {
			throw new NoSuchNameException(e.getMessage());
		}
	}

	/**
	 * 
	 * @return
	 * @param specName
	 * @ejb.interface-method view-type = "both"
	 */
	public epcglobal.ale.ECSpec getECSpec(String specName) throws cuhk.ale.exceptions.NoSuchNameException,
			cuhk.ale.exceptions.SecurityException, cuhk.ale.exceptions.ImplementationException {

		ALEServiceBean.logger.info(specName + " ALE_getECSpec ");
		try {
			ECSpecInstanceLocalHome ecspecInstanceLocalHome = ECSpecInstanceUtil.getLocalHome();
			ECSpecInstanceLocal ecspecInstanceLocal = ecspecInstanceLocalHome
					.findByPrimaryKey(new ECSpecInstancePK(specName));
			return ecspecInstanceLocal.getECSpec();
		} catch (EJBException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (NamingException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (FinderException e) {
			throw new NoSuchNameException(e.getMessage());
		}

	}

	/**
	 * 
	 * 
	 * @return
	 * @ejb.interface-method view-type = "both"
	 */
	public List getECSpecNames() throws cuhk.ale.exceptions.SecurityException,
	cuhk.ale.exceptions.ImplementationException {

		ALEServiceBean.logger.info("ALE_getECSpecNames");
		try {
			ECSpecInstanceLocalHome ecspecInstanceLocalHome = ECSpecInstanceUtil.getLocalHome();
			return ecspecInstanceLocalHome.getSpecNames();
		} catch (EJBException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (NamingException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		}
	}

	/**
	 * 
	 * 
	 * @param specName
	 * @param notificationURI
	 * @ejb.interface-method view-type = "both"
	 */
	public void subscribe(String specName, String notificationURI)
			throws cuhk.ale.exceptions.NoSuchNameException, cuhk.ale.exceptions.InvalidURIException,
			cuhk.ale.exceptions.DuplicateSubscriptionException, cuhk.ale.exceptions.SecurityException,
			cuhk.ale.exceptions.ImplementationException {

		ALEServiceBean.logger.info(specName + " ALE_subscribe " + ", " + notificationURI);
		try {

			ECSpecInstanceLocalHome ecspecInstanceLocalHome = ECSpecInstanceUtil.getLocalHome();
			ECSpecInstanceLocal ecspecInstanceLocal = ecspecInstanceLocalHome
					.findByPrimaryKey(new ECSpecInstancePK(specName));
			ecspecInstanceLocal.subscribe(notificationURI);

		} catch (EJBException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (NamingException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (FinderException e) {
			throw new NoSuchNameException(e.getMessage());
		}

	}

	/**
	 * 
	 * 
	 * @param specName
	 * @param notificationURI
	 * @ejb.interface-method view-type = "both"
	 */
	public void unsubscribe(String specName, String notificationURI)
			throws cuhk.ale.exceptions.NoSuchNameException, cuhk.ale.exceptions.NoSuchSubscriberException,
			cuhk.ale.exceptions.InvalidURIException, cuhk.ale.exceptions.SecurityException,
			cuhk.ale.exceptions.ImplementationException {

		ALEServiceBean.logger.info(specName + " ALE_unsubscribe " + ", " + notificationURI);
		try {

			ECSpecInstanceLocalHome ecspecInstanceLocalHome = ECSpecInstanceUtil.getLocalHome();
			ECSpecInstanceLocal ecspecInstanceLocal = ecspecInstanceLocalHome
					.findByPrimaryKey(new ECSpecInstancePK(specName));
			ecspecInstanceLocal.unsubscribe(notificationURI);

		} catch (EJBException e) {
			// FIXME throw Invalid URI exception !
			// FIXME NoSuchSubscriberException ?!
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (NamingException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (FinderException e) {
			throw new NoSuchNameException(e.getMessage());
		}
	}

	/**
	 * 
	 * 
	 * @return
	 * @param specName
	 * @ejb.interface-method view-type = "both"
	 * @ejb.transaction type = "Supports"
	 * 
	 * Transaction required will fail
	 */
	public epcglobal.ale.ECReports poll(String specName) throws cuhk.ale.exceptions.NoSuchNameException,
			cuhk.ale.exceptions.SecurityException, cuhk.ale.exceptions.ImplementationException {

		ALEServiceBean.logger.info(specName + " ALE_poll ");

		try {

			ECSpecInstanceLocalHome ecspecInstanceLocalHome = ECSpecInstanceUtil.getLocalHome();
			ECSpecInstanceLocal ecspecInstanceLocal = ecspecInstanceLocalHome
					.findByPrimaryKey(new ECSpecInstancePK(specName));
			String urlUuid = "__JMS__" + ALEServiceUtil.generateGUID(this);
			ecspecInstanceLocal.subscribe(urlUuid);
			ECReports reports = (ECReports) ECSpecEventCallback.recvMessage("specName", specName);
			ecspecInstanceLocal.unsubscribe(urlUuid);

			return reports;

		} catch (EJBException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (NamingException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (FinderException e) {
			throw new NoSuchNameException(e.getMessage());
		}
	}

	/**
	 * 
	 * @return
	 * @param spec
	 * @ejb.interface-method view-type = "both"
	 * @ejb.transaction type = "Supports"
	 */
	public epcglobal.ale.ECReports immediate(epcglobal.ale.ECSpec spec) throws cuhk.ale.exceptions.ECSpecValidationException,
			cuhk.ale.exceptions.SecurityException, cuhk.ale.exceptions.ImplementationException {

		ALEServiceBean.logger.info("ALE_immediate");

		String specName = "__IMMED__" + ALEServiceUtil.generateGUID(this);

		try {
			define(specName, spec);
			ECReports reports = poll(specName);
			undefine(specName);
			return reports;
		} catch (DuplicateNameException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (NoSuchNameException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		}
	}

	/**
	 * 
	 * @return
	 * @param spec
	 * @ejb.interface-method view-type = "both"
	 * @ejb.transaction type = "Supports"
	 */
	public List getSubscribers(String specName) throws cuhk.ale.exceptions.NoSuchNameException,
			cuhk.ale.exceptions.SecurityException, cuhk.ale.exceptions.ImplementationException {
		ALEServiceBean.logger.info(specName + " ALE_getSubscribers ");
		try {
			ECSpecInstanceLocal ecspecInstanceLocal = ECSpecInstanceUtil.getLocalHome().findByPrimaryKey(
					new ECSpecInstancePK(specName));
			List notificationURLs = ecspecInstanceLocal.getNotificationURLs(specName);
			return notificationURLs;
		} catch (NamingException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (FinderException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} finally {
		}
	}

	/**
	 * 
	 * 
	 * @return
	 * @ejb.interface-method view-type = "both"
	 */
	public String getStandardVersion() {
		return "1.0";
	}

	/**
	 * 
	 * 
	 * @return
	 * @ejb.interface-method view-type = "both"
	 */
	public String getVendorVersion() {
		// empty string indicates the implementation doesn't have vendor
		// extensions
		return "";
	}

	/**
	 * @param specName
	 * @throws ImplementationException
	 * @ejb.interface-method view-type = "both"
	 */
	public void startTrigger(String specName) throws cuhk.ale.exceptions.ImplementationException {
		ALEServiceBean.logger.info(specName + " ALE_rcv_startTrigger ");
		try {
			ECSpecInstanceLocalHome ecspecInstanceLocalHome = ECSpecInstanceUtil.getLocalHome();
			ECSpecInstanceLocal ecspecInstanceLocal = ecspecInstanceLocalHome
					.findByPrimaryKey(new ECSpecInstancePK(specName));
			ecspecInstanceLocal.startTriggerReceived();

		} catch (FinderException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (NamingException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} finally {
		}

	}

	/**
	 * @param specName
	 * @throws ImplementationException
	 * @ejb.interface-method view-type = "both"
	 */
	public void stopTrigger(String specName) throws cuhk.ale.exceptions.ImplementationException {
		ALEServiceBean.logger.info(specName + " ALE_rcv_stopTrigger ");
		try {
			ECSpecInstanceLocalHome ecspecInstanceLocalHome = ECSpecInstanceUtil.getLocalHome();
			ECSpecInstanceLocal ecspecInstanceLocal = ecspecInstanceLocalHome
					.findByPrimaryKey(new ECSpecInstancePK(specName));
			ecspecInstanceLocal.stopTriggerReceived();

		} catch (FinderException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} catch (NamingException e) {
			throw new ImplementationException(e.getMessage(), ImplementationExceptionSeverity.ERROR);
		} finally {
		}
	}

}
