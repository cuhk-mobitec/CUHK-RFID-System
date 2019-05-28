// $Id: ALEException.java,v 1.1 2006/06/16 16:49:19 alfred Exp $

package cuhk.ale.exceptions;


public class ALEException extends Exception {

	private String reason;

	public ALEException() {
		super();
	}
	
	public ALEException(String message) {
		super(message);		
		reason = message;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
}
