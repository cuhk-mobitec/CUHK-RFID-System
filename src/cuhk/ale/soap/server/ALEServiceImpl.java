package cuhk.ale.soap.server;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import cuhk.ale.ejb.interfaces.ALEServiceLocal;
import cuhk.ale.ejb.interfaces.ALEServiceLocalHome;
import cuhk.ale.ejb.interfaces.ALEServiceUtil;
import cuhk.ale.exceptions.DuplicateNameException;
import cuhk.ale.exceptions.DuplicateSubscriptionException;
import cuhk.ale.exceptions.ECSpecValidationException;
import cuhk.ale.exceptions.ImplementationException;
import cuhk.ale.exceptions.InvalidURIException;
import cuhk.ale.exceptions.NoSuchNameException;
import cuhk.ale.exceptions.NoSuchSubscriberException;
import cuhk.ale.exceptions.SecurityException;
import cuhk.ale.soap.util.ExceptionObjectFactory;
import epcglobal.ale.ECReports;
import epcglobal.ale.ECSpec;
import epcglobal.ale.soap.ArrayOfString;
import epcglobal.ale.soap.Define;
import epcglobal.ale.soap.DuplicateNameExceptionResponse;
import epcglobal.ale.soap.DuplicateSubscriptionExceptionResponse;
import epcglobal.ale.soap.ECSpecValidationExceptionResponse;
import epcglobal.ale.soap.EmptyParms;
import epcglobal.ale.soap.GetECSpec;
import epcglobal.ale.soap.GetSubscribers;
import epcglobal.ale.soap.Immediate;
import epcglobal.ale.soap.ImplementationExceptionResponse;
import epcglobal.ale.soap.InvalidURIExceptionResponse;
import epcglobal.ale.soap.NoSuchNameExceptionResponse;
import epcglobal.ale.soap.NoSuchSubscriberExceptionResponse;
import epcglobal.ale.soap.ObjectFactory;
import epcglobal.ale.soap.Poll;
import epcglobal.ale.soap.SecurityExceptionResponse;
import epcglobal.ale.soap.Subscribe;
import epcglobal.ale.soap.Undefine;
import epcglobal.ale.soap.Unsubscribe;
import epcglobal.ale.soap.VoidHolder;

@javax.jws.WebService(endpointInterface = "epcglobal.ale.soap.ALEServicePortType")
public class ALEServiceImpl {

	static private final Logger logger = Logger.getLogger(ALEServiceImpl.class.getName());

	public VoidHolder define(Define parms) throws DuplicateNameExceptionResponse, ECSpecValidationExceptionResponse,
			ImplementationExceptionResponse, SecurityExceptionResponse {
		logger.info("define is triggered.");
		ALEServiceLocalHome aleServiceHome;
		try {
			aleServiceHome = (ALEServiceLocalHome) ALEServiceUtil.getLocalHome();
			ALEServiceLocal aleService = aleServiceHome.create();
			aleService.define(parms.getSpecName(), parms.getSpec());
		} catch (NamingException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (CreateException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (DuplicateNameException e) {
			logger.error(e);			
			throw ExceptionObjectFactory.createDuplicateNameExceptionResponse(e);
		} catch (ECSpecValidationException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createECSpecValidationExceptionResponse(e);
		} catch (SecurityException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createSecurityExceptionResponse(e);
		} catch (ImplementationException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} finally {
		}
		return new VoidHolder();

	}

	public VoidHolder undefine(Undefine parms) throws ImplementationExceptionResponse, NoSuchNameExceptionResponse,
			SecurityExceptionResponse {
		logger.info("undefine is triggered.");
		try {
			ALEServiceLocalHome aleServiceHome = (ALEServiceLocalHome) ALEServiceUtil.getLocalHome();
			cuhk.ale.ejb.interfaces.ALEServiceLocal aleService = aleServiceHome.create();
			aleService.undefine(parms.getSpecName());
		} catch (NamingException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (CreateException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (NoSuchNameException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createNoSuchNameExceptionResponse(e);
		} catch (SecurityException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createSecurityExceptionResponse(e);
		} catch (ImplementationException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} finally {
		}

		return new VoidHolder();
	}

	public ECSpec getECSpec(GetECSpec parms) throws ImplementationExceptionResponse, SecurityExceptionResponse {
		logger.info("getECSpec is triggered.");
		try {
			ALEServiceLocalHome aleServiceHome = (ALEServiceLocalHome) ALEServiceUtil.getLocalHome();
			cuhk.ale.ejb.interfaces.ALEServiceLocal aleService = aleServiceHome.create();
			return aleService.getECSpec(parms.getSpecName());
		} catch (NamingException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (CreateException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (NoSuchNameException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (SecurityException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createSecurityExceptionResponse(e);
		} catch (ImplementationException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} finally {
		}
	}

	public ArrayOfString getECSpecNames(EmptyParms parms) throws ImplementationExceptionResponse,
			SecurityExceptionResponse {
		logger.info("getECSpecNames is triggered.");
		try {
			ALEServiceLocalHome aleServiceHome = (ALEServiceLocalHome) ALEServiceUtil.getLocalHome();
			cuhk.ale.ejb.interfaces.ALEServiceLocal aleService = aleServiceHome.create();
			List<String> specNames = aleService.getECSpecNames();
			ArrayOfString retval = new ObjectFactory().createArrayOfString();
			for (String specName : specNames) {
				retval.getString().add(specName);
			}
			return retval;
		} catch (NamingException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (CreateException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (SecurityException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createSecurityExceptionResponse(e);
		} catch (ImplementationException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} finally {
		}

	}

	public VoidHolder subscribe(Subscribe parms) throws DuplicateSubscriptionExceptionResponse,
	ImplementationExceptionResponse, InvalidURIExceptionResponse, NoSuchNameExceptionResponse,
			SecurityExceptionResponse {
		logger.info("subscribe is triggered.");
		try {
			ALEServiceLocalHome aleServiceHome = (ALEServiceLocalHome) ALEServiceUtil.getLocalHome();
			cuhk.ale.ejb.interfaces.ALEServiceLocal aleService = aleServiceHome.create();
			aleService.subscribe(parms.getSpecName(), parms.getNotificationURI());
		} catch (NamingException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (CreateException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (NoSuchNameException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createNoSuchNameExceptionResponse(e);
		} catch (InvalidURIException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createInvalidURIExceptionResponse(e);
		} catch (DuplicateSubscriptionException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createDuplicateSubscriptionExceptionResponse(e);
		} catch (SecurityException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createSecurityExceptionResponse(e);
		} catch (ImplementationException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} finally {
		}
		return new VoidHolder();
	}

	public VoidHolder unsubscribe(Unsubscribe parms) throws ImplementationExceptionResponse,
			InvalidURIExceptionResponse, NoSuchNameExceptionResponse, NoSuchSubscriberExceptionResponse,
			SecurityExceptionResponse {
		logger.info("unsubscribe is triggered.");
		try {
			ALEServiceLocalHome aleServiceHome = (ALEServiceLocalHome) ALEServiceUtil.getLocalHome();
			cuhk.ale.ejb.interfaces.ALEServiceLocal aleService = aleServiceHome.create();
			aleService.unsubscribe(parms.getSpecName(), parms.getNotificationURI());
		} catch (NamingException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (CreateException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (NoSuchNameException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createNoSuchNameExceptionResponse(e);
		} catch (NoSuchSubscriberException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createNoSuchSubscriberExceptionResponse(e);
		} catch (InvalidURIException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createInvalidURIExceptionResponse(e);
		} catch (SecurityException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createSecurityExceptionResponse(e);
		} catch (ImplementationException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} finally {
		}
		return new VoidHolder();
	}

	public ECReports poll(Poll parms) throws ImplementationExceptionResponse, NoSuchNameExceptionResponse,
			SecurityExceptionResponse {
		logger.info("poll is triggered.");
		try {
			ALEServiceLocalHome aleServiceHome = (ALEServiceLocalHome) ALEServiceUtil.getLocalHome();
			cuhk.ale.ejb.interfaces.ALEServiceLocal aleService = aleServiceHome.create();
			return aleService.poll(parms.getSpecName());
		} catch (NamingException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (CreateException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (NoSuchNameException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createNoSuchNameExceptionResponse(e);
		} catch (SecurityException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createSecurityExceptionResponse(e);
		} catch (ImplementationException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} finally {
		}
	}

	public ECReports immediate(Immediate parms) throws ECSpecValidationExceptionResponse,
			ImplementationExceptionResponse, SecurityExceptionResponse {
		logger.info("immediate is triggered.");
		try {
			ALEServiceLocalHome aleServiceHome = (ALEServiceLocalHome) ALEServiceUtil.getLocalHome();
			cuhk.ale.ejb.interfaces.ALEServiceLocal aleService = aleServiceHome.create();
			return aleService.immediate(parms.getSpec());
		} catch (NamingException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (CreateException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (ECSpecValidationException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createECSpecValidationExceptionResponse(e);
		} catch (SecurityException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createSecurityExceptionResponse(e);
		} catch (ImplementationException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} finally {
		}
	}

	public ArrayOfString getSubscribers(GetSubscribers parms) throws ImplementationExceptionResponse,
			NoSuchNameExceptionResponse, SecurityExceptionResponse {
		logger.info("getSubscribers is triggered.");
		try {
			ALEServiceLocalHome aleServiceHome = (ALEServiceLocalHome) ALEServiceUtil.getLocalHome();
			cuhk.ale.ejb.interfaces.ALEServiceLocal aleService = aleServiceHome.create();
			List<String> subscribers = aleService.getSubscribers(parms.getSpecName());
			ArrayOfString retval = new ObjectFactory().createArrayOfString();
			for (String subscriber : subscribers) {
				retval.getString().add(subscriber);
			}
			return retval;

		} catch (NamingException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (CreateException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (NoSuchNameException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createNoSuchNameExceptionResponse(e);
		} catch (SecurityException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createSecurityExceptionResponse(e);
		} catch (ImplementationException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} finally {
		}
	}

	public String getStandardVersion(EmptyParms parms) throws ImplementationExceptionResponse {
		logger.info("getStandardVersion is triggered.");

		try {
			ALEServiceLocalHome aleServiceHome = (ALEServiceLocalHome) ALEServiceUtil.getLocalHome();
			cuhk.ale.ejb.interfaces.ALEServiceLocal aleService = aleServiceHome.create();
			return aleService.getStandardVersion();
		} catch (NamingException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (CreateException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} finally {
		}

	}

	public String getVendorVersion(EmptyParms parms) throws ImplementationExceptionResponse {
		logger.info("getVendorVersion is triggered.");
		try {
			ALEServiceLocalHome aleServiceHome = (ALEServiceLocalHome) ALEServiceUtil.getLocalHome();
			cuhk.ale.ejb.interfaces.ALEServiceLocal aleService = aleServiceHome.create();
			return aleService.getVendorVersion();
		} catch (NamingException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} catch (CreateException e) {
			logger.error(e);
			throw ExceptionObjectFactory.createImplementationExceptionResponse(e);
		} finally {
		}
	}
}
