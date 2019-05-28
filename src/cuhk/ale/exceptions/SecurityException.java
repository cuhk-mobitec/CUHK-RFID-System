// $Id: SecurityException.java,v 1.1 2006/06/16 16:49:19 alfred Exp $

package cuhk.ale.exceptions;

public class SecurityException extends ALEException {

	private static final long serialVersionUID = -4674854680738351817L;

	public SecurityException() {
		super();
	}

	public SecurityException(String message) {
		super(message);
	}
}
