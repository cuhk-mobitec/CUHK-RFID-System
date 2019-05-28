// $Id: NoSuchSubscriberException.java,v 1.1 2006/06/16 16:49:19 alfred Exp $

package cuhk.ale.exceptions;

public class NoSuchSubscriberException extends ALEException {

	private static final long serialVersionUID = 8597472105852627704L;

	public NoSuchSubscriberException() {
		super();
	}

	public NoSuchSubscriberException(String message) {
		super(message);
	}
}
