
package epcglobal.ale.soap;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAXWS SI.
 * JAX-WS RI 2.0_01-b59-fcs
 * Generated source version: 2.0
 * 
 */
@WebFault(name = "DuplicateSubscriptionException", targetNamespace = "urn:epcglobal:ale:wsdl:1")
public class DuplicateSubscriptionExceptionResponse
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private DuplicateSubscriptionException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public DuplicateSubscriptionExceptionResponse(String message, DuplicateSubscriptionException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param message
     * @param cause
     */
    public DuplicateSubscriptionExceptionResponse(String message, DuplicateSubscriptionException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: epcglobal.ale.soap.DuplicateSubscriptionException
     */
    public DuplicateSubscriptionException getFaultInfo() {
        return faultInfo;
    }

}
