// $Id: InvalidURIException.java,v 1.1 2006/06/16 16:49:19 alfred Exp $

package cuhk.ale.exceptions;

public class InvalidURIException extends ALEException {

	private static final long serialVersionUID = -4408875921956556074L;

	public InvalidURIException() {
		super();
	}

	public InvalidURIException(String message) {
		super(message);
	}
}
