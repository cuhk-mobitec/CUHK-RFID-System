// $Id: ImplementationException.java,v 1.1 2006/06/16 16:49:19 alfred Exp $

package cuhk.ale.exceptions;

public class ImplementationException extends ALEException {

	private static final long serialVersionUID = 1286128931899311650L;
	
	private ImplementationExceptionSeverity severity;

	public ImplementationException(ImplementationExceptionSeverity severity) {
		super();
		this.severity = severity;
	}

	public ImplementationException(String message, ImplementationExceptionSeverity severity) {
		super(message);
		this.severity = severity;
	}
	
	public ImplementationExceptionSeverity getSeverity() {
		return severity;
	}
}
