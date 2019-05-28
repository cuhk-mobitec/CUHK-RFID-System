
package epcglobal.ale.soap;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImplementationException complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImplementationException">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:epcglobal:ale:wsdl:1}ALEException">
 *       &lt;sequence>
 *         &lt;element name="severity" type="{urn:epcglobal:ale:wsdl:1}ImplementationExceptionSeverity"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImplementationException", propOrder = {
    "severity"
})
public class ImplementationException
    extends ALEException
    implements Serializable
{

    @XmlElement(required = true)
    protected ImplementationExceptionSeverity severity;

    /**
     * Gets the value of the severity property.
     * 
     * @return
     *     possible object is
     *     {@link ImplementationExceptionSeverity }
     *     
     */
    public ImplementationExceptionSeverity getSeverity() {
        return severity;
    }

    /**
     * Sets the value of the severity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImplementationExceptionSeverity }
     *     
     */
    public void setSeverity(ImplementationExceptionSeverity value) {
        this.severity = value;
    }

    public boolean isSetSeverity() {
        return (this.severity!= null);
    }

}
