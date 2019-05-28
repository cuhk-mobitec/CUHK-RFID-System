
package epcglobal.ale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * 
 *              An ECSpec describes an event cycle and one or more reports that are to
 *              be generated from it. It contains a list of logical readers whose reader
 *              cycles are to be included in the event cycle, a specification of read
 *              cycle timing, a specification of how the boundaries of event cycles are
 *              to be determined, and list of specifications each of which describes a
 *              report to be generated from this event cycle.
 *             
 * 
 * <p>Java class for ECSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ECSpec">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:epcglobal:xsd:1}Document">
 *       &lt;sequence>
 *         &lt;element name="logicalReaders" type="{urn:epcglobal:ale:xsd:1}ECLogicalReaders"/>
 *         &lt;element name="boundarySpec" type="{urn:epcglobal:ale:xsd:1}ECBoundarySpec"/>
 *         &lt;element name="reportSpecs" type="{urn:epcglobal:ale:xsd:1}ECReportSpecs"/>
 *         &lt;element name="extension" type="{urn:epcglobal:ale:xsd:1}ECSpecExtension" minOccurs="0"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *       &lt;attribute name="includeSpecInReports" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ECSpec", propOrder = {
    "logicalReaders",
    "boundarySpec",
    "reportSpecs",
    "extension",
    "any"
})
public class ECSpec
    extends Document
    implements Serializable
{

    @XmlElement(required = true)
    protected ECLogicalReaders logicalReaders;
    @XmlElement(required = true)
    protected ECBoundarySpec boundarySpec;
    @XmlElement(required = true)
    protected ECReportSpecs reportSpecs;
    protected ECSpecExtension extension;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute
    protected Boolean includeSpecInReports;

    /**
     * Gets the value of the logicalReaders property.
     * 
     * @return
     *     possible object is
     *     {@link ECLogicalReaders }
     *     
     */
    public ECLogicalReaders getLogicalReaders() {
        return logicalReaders;
    }

    /**
     * Sets the value of the logicalReaders property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECLogicalReaders }
     *     
     */
    public void setLogicalReaders(ECLogicalReaders value) {
        this.logicalReaders = value;
    }

    public boolean isSetLogicalReaders() {
        return (this.logicalReaders!= null);
    }

    /**
     * Gets the value of the boundarySpec property.
     * 
     * @return
     *     possible object is
     *     {@link ECBoundarySpec }
     *     
     */
    public ECBoundarySpec getBoundarySpec() {
        return boundarySpec;
    }

    /**
     * Sets the value of the boundarySpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECBoundarySpec }
     *     
     */
    public void setBoundarySpec(ECBoundarySpec value) {
        this.boundarySpec = value;
    }

    public boolean isSetBoundarySpec() {
        return (this.boundarySpec!= null);
    }

    /**
     * Gets the value of the reportSpecs property.
     * 
     * @return
     *     possible object is
     *     {@link ECReportSpecs }
     *     
     */
    public ECReportSpecs getReportSpecs() {
        return reportSpecs;
    }

    /**
     * Sets the value of the reportSpecs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECReportSpecs }
     *     
     */
    public void setReportSpecs(ECReportSpecs value) {
        this.reportSpecs = value;
    }

    public boolean isSetReportSpecs() {
        return (this.reportSpecs!= null);
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link ECSpecExtension }
     *     
     */
    public ECSpecExtension getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECSpecExtension }
     *     
     */
    public void setExtension(ECSpecExtension value) {
        this.extension = value;
    }

    public boolean isSetExtension() {
        return (this.extension!= null);
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Element }
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

    public boolean isSetAny() {
        return ((this.any!= null)&&(!this.any.isEmpty()));
    }

    public void unsetAny() {
        this.any = null;
    }

    /**
     * Gets the value of the includeSpecInReports property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIncludeSpecInReports() {
        if (includeSpecInReports == null) {
            return false;
        } else {
            return includeSpecInReports;
        }
    }

    /**
     * Sets the value of the includeSpecInReports property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeSpecInReports(boolean value) {
        this.includeSpecInReports = value;
    }

    public boolean isSetIncludeSpecInReports() {
        return (this.includeSpecInReports!= null);
    }

    public void unsetIncludeSpecInReports() {
        this.includeSpecInReports = null;
    }

}
