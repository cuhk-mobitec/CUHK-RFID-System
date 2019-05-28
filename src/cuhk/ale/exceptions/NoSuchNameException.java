// $Id: NoSuchNameException.java,v 1.1 2006/06/16 16:49:19 alfred Exp $

package cuhk.ale.exceptions;

public class NoSuchNameException extends ALEException {

	private static final long serialVersionUID = 4135225750012046632L;

	public NoSuchNameException() {
		super();
	}

	public NoSuchNameException(String message) {
		super(message);
	}
}
