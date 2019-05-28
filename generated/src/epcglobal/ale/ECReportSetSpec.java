
package epcglobal.ale;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *              ECReportSetSpec specifies which set of EPCs is to be considered for
 *              filtering and output.
 *             
 * 
 * <p>Java class for ECReportSetSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ECReportSetSpec">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="set" type="{urn:epcglobal:ale:xsd:1}ECReportSetEnum" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ECReportSetSpec")
public class ECReportSetSpec
    implements Serializable
{

    @XmlAttribute
    protected ECReportSetEnum set;

    /**
     * Gets the value of the set property.
     * 
     * @return
     *     possible object is
     *     {@link ECReportSetEnum }
     *     
     */
    public ECReportSetEnum getSet() {
        return set;
    }

    /**
     * Sets the value of the set property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECReportSetEnum }
     *     
     */
    public void setSet(ECReportSetEnum value) {
        this.set = value;
    }

    public boolean isSetSet() {
        return (this.set!= null);
    }

}
