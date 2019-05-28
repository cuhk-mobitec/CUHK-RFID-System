/**
 * $Id: ECSpecOptimisticLockException.java,v 1.1 2006/06/16 16:49:19 alfred Exp $
 */
package cuhk.ale.exceptions;

import javax.ejb.EJBException;

/**
 * @version $Id: ECSpecOptimisticLockException.java,v 1.1 2006/06/16 16:49:19 alfred Exp $
 * @author kyyu
 */
public class ECSpecOptimisticLockException extends EJBException {

	/**
	 * 
	 */
	public ECSpecOptimisticLockException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ECSpecOptimisticLockException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ECSpecOptimisticLockException(String message, Exception cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ECSpecOptimisticLockException(Exception cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
