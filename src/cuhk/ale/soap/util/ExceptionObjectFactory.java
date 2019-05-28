package cuhk.ale.soap.util;

import cuhk.ale.exceptions.ALEException;
import epcglobal.ale.soap.DuplicateNameExceptionResponse;
import epcglobal.ale.soap.DuplicateSubscriptionException;
import epcglobal.ale.soap.DuplicateSubscriptionExceptionResponse;
import epcglobal.ale.soap.ECSpecValidationExceptionResponse;
import epcglobal.ale.soap.ImplementationExceptionResponse;
import epcglobal.ale.soap.ImplementationExceptionSeverity;
import epcglobal.ale.soap.InvalidURIExceptionResponse;
import epcglobal.ale.soap.NoSuchNameExceptionResponse;
import epcglobal.ale.soap.NoSuchSubscriberExceptionResponse;
import epcglobal.ale.soap.SecurityExceptionResponse;

public class ExceptionObjectFactory {

	public static ImplementationExceptionResponse createImplementationExceptionResponse(Exception e) {

		if (e instanceof cuhk.ale.exceptions.ImplementationException) {
			cuhk.ale.exceptions.ImplementationException e2 = (cuhk.ale.exceptions.ImplementationException) e;
			epcglobal.ale.soap.ImplementationException e_soap = new epcglobal.ale.soap.ImplementationException();
			e_soap.setReason(e2.getReason());
			e_soap.setSeverity(ImplementationExceptionSeverity.fromValue(e2.getSeverity().toString()));
			return new ImplementationExceptionResponse(e_soap.getReason(), e_soap);
		} else {
			epcglobal.ale.soap.ImplementationException e_soap = new epcglobal.ale.soap.ImplementationException();
			e_soap.setReason(e.getMessage());
			e_soap.setSeverity(ImplementationExceptionSeverity.fromValue("ERROR"));
			return new ImplementationExceptionResponse(e_soap.getReason(), e_soap);
		}
	}

	public static epcglobal.ale.soap.ALEException convertALEExceptionToSoap(ALEException e) {
		epcglobal.ale.soap.ALEException e_soap = new epcglobal.ale.soap.ALEException();
		e_soap.setReason(e.getReason());
		return e_soap;
	}

	public static DuplicateNameExceptionResponse createDuplicateNameExceptionResponse(ALEException e) {
		epcglobal.ale.soap.DuplicateNameException e_soap = new epcglobal.ale.soap.DuplicateNameException();
		e_soap.setReason(e.getReason());
		return new DuplicateNameExceptionResponse(e_soap.getReason(), e_soap);
	}

	public static DuplicateSubscriptionExceptionResponse createDuplicateSubscriptionExceptionResponse(ALEException e) {
		DuplicateSubscriptionException e_soap = new epcglobal.ale.soap.DuplicateSubscriptionException();
		e_soap.setReason(e.getReason());
		return new DuplicateSubscriptionExceptionResponse(e_soap.getReason(), e_soap);
	}


	public static ECSpecValidationExceptionResponse createECSpecValidationExceptionResponse(ALEException e) {
		epcglobal.ale.soap.ECSpecValidationException e_soap = new epcglobal.ale.soap.ECSpecValidationException();
		e_soap.setReason(e.getReason());
		return new ECSpecValidationExceptionResponse(e_soap.getReason(), e_soap);
	}

	public static InvalidURIExceptionResponse createInvalidURIExceptionResponse(ALEException e) {
		epcglobal.ale.soap.InvalidURIException e_soap = new epcglobal.ale.soap.InvalidURIException();
		e_soap.setReason(e.getReason());
		return new InvalidURIExceptionResponse(e_soap.getReason(), e_soap);
	}

	public static NoSuchNameExceptionResponse createNoSuchNameExceptionResponse(ALEException e) {
		epcglobal.ale.soap.NoSuchNameException e_soap = new epcglobal.ale.soap.NoSuchNameException();
		e_soap.setReason(e.getReason());
		return new NoSuchNameExceptionResponse(e_soap.getReason(), e_soap);
	}

	public static NoSuchSubscriberExceptionResponse createNoSuchSubscriberExceptionResponse(ALEException e) {
		epcglobal.ale.soap.NoSuchSubscriberException e_soap = new epcglobal.ale.soap.NoSuchSubscriberException();
		e_soap.setReason(e.getReason());
		return new NoSuchSubscriberExceptionResponse(e_soap.getReason(), e_soap);
	}

//	public static ParseURIExceptionResponse createParseURIExceptionResponse(ALEException e) {
//		epcglobal.ale.soap.ParseURIException e_soap = new epcglobal.ale.soap.ParseURIException();
//		e_soap.setReason(e.getReason());
//		return new ParseURIExceptionResponse(e_soap.getReason(), e_soap);
//	}

	public static SecurityExceptionResponse createSecurityExceptionResponse(ALEException e) {
		epcglobal.ale.soap.SecurityException e_soap = new epcglobal.ale.soap.SecurityException();
		e_soap.setReason(e.getReason());
		return new SecurityExceptionResponse(e_soap.getReason(), e_soap);
	}

//	public static XXXExceptionResponse createXXXExceptionResponse(ALEException e) {
//		epcglobal.ale.soap.XXXException e_soap = new epcglobal.ale.soap.XXXException();
//		e_soap.setReason(e.getReason());
//		return new XXXExceptionResponse(e_soap.getReason(), e_soap);
//	}


	
}