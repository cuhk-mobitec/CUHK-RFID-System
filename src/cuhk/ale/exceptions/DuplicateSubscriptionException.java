// $Id: DuplicateSubscriptionException.java,v 1.1 2006/06/16 16:49:19 alfred Exp $

package cuhk.ale.exceptions;

public class DuplicateSubscriptionException extends ALEException {

	private static final long serialVersionUID = 8013713627264737216L;

	public DuplicateSubscriptionException() {
		super();
	}

	public DuplicateSubscriptionException(String message) {
		super(message);
	}
}
