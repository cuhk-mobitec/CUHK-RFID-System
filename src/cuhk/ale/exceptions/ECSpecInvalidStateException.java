/**
 * $Id: ECSpecInvalidStateException.java,v 1.1 2006/06/16 16:49:19 alfred Exp $
 */
package cuhk.ale.exceptions;

import javax.ejb.EJBException;

/**
 * @version $Id: ECSpecInvalidStateException.java,v 1.1 2006/06/16 16:49:19 alfred Exp $
 * @author kyyu
 *
 */
public class ECSpecInvalidStateException extends EJBException {

	/**
	 * 
	 */
	public ECSpecInvalidStateException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ECSpecInvalidStateException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ECSpecInvalidStateException(Exception arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ECSpecInvalidStateException(String arg0, Exception arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
