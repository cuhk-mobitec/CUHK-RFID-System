
package epcglobal.ale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3._2001.xmlschema.Adapter1;
import org.w3c.dom.Element;


/**
 * 
 *              ECReports is the output from an event cycle. The "meat" of an ECReports
 *              instance is the lists of count report instances and list report
 *              instances, each corresponding to an ECReportSpec instance in the event
 *              cycle's ECSpec. In addition to the reports themselves, ECReports contains
 *              a number of "header" fields that provide useful information about the
 *              event cycle.
 *             
 * 
 * <p>Java class for ECReports complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ECReports">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:epcglobal:xsd:1}Document">
 *       &lt;sequence>
 *         &lt;element name="reports" type="{urn:epcglobal:ale:xsd:1}ECReportList"/>
 *         &lt;element name="extension" type="{urn:epcglobal:ale:xsd:1}ECReportsExtension" minOccurs="0"/>
 *         &lt;element name="ECSpec" type="{urn:epcglobal:ale:xsd:1}ECSpec" minOccurs="0"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *       &lt;attribute name="ALEID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="date" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="schemaURL" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="specName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="terminationCondition" use="required" type="{urn:epcglobal:ale:xsd:1}ECTerminationCondition" />
 *       &lt;attribute name="totalMilliseconds" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ECReports", propOrder = {
    "reports",
    "extension",
    "ecSpec",
    "any"
})
public class ECReports
    extends Document
    implements Serializable
{

    @XmlElement(required = true)
    protected ECReportList reports;
    protected ECReportsExtension extension;
    @XmlElement(name = "ECSpec")
    protected ECSpec ecSpec;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute(name = "ALEID", required = true)
    protected String aleid;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(Adapter1 .class)
    protected Date date;
    @XmlAttribute
    protected String schemaURL;
    @XmlAttribute(required = true)
    protected String specName;
    @XmlAttribute(required = true)
    protected ECTerminationCondition terminationCondition;
    @XmlAttribute(required = true)
    protected long totalMilliseconds;

    /**
     * Gets the value of the reports property.
     * 
     * @return
     *     possible object is
     *     {@link ECReportList }
     *     
     */
    public ECReportList getReports() {
        return reports;
    }

    /**
     * Sets the value of the reports property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECReportList }
     *     
     */
    public void setReports(ECReportList value) {
        this.reports = value;
    }

    public boolean isSetReports() {
        return (this.reports!= null);
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link ECReportsExtension }
     *     
     */
    public ECReportsExtension getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECReportsExtension }
     *     
     */
    public void setExtension(ECReportsExtension value) {
        this.extension = value;
    }

    public boolean isSetExtension() {
        return (this.extension!= null);
    }

    /**
     * Gets the value of the ecSpec property.
     * 
     * @return
     *     possible object is
     *     {@link ECSpec }
     *     
     */
    public ECSpec getECSpec() {
        return ecSpec;
    }

    /**
     * Sets the value of the ecSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECSpec }
     *     
     */
    public void setECSpec(ECSpec value) {
        this.ecSpec = value;
    }

    public boolean isSetECSpec() {
        return (this.ecSpec!= null);
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
     * Gets the value of the aleid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getALEID() {
        return aleid;
    }

    /**
     * Sets the value of the aleid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALEID(String value) {
        this.aleid = value;
    }

    public boolean isSetALEID() {
        return (this.aleid!= null);
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(Date value) {
        this.date = value;
    }

    public boolean isSetDate() {
        return (this.date!= null);
    }

    /**
     * Gets the value of the schemaURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchemaURL() {
        return schemaURL;
    }

    /**
     * Sets the value of the schemaURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchemaURL(String value) {
        this.schemaURL = value;
    }

    public boolean isSetSchemaURL() {
        return (this.schemaURL!= null);
    }

    /**
     * Gets the value of the specName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecName() {
        return specName;
    }

    /**
     * Sets the value of the specName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecName(String value) {
        this.specName = value;
    }

    public boolean isSetSpecName() {
        return (this.specName!= null);
    }

    /**
     * Gets the value of the terminationCondition property.
     * 
     * @return
     *     possible object is
     *     {@link ECTerminationCondition }
     *     
     */
    public ECTerminationCondition getTerminationCondition() {
        return terminationCondition;
    }

    /**
     * Sets the value of the terminationCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECTerminationCondition }
     *     
     */
    public void setTerminationCondition(ECTerminationCondition value) {
        this.terminationCondition = value;
    }

    public boolean isSetTerminationCondition() {
        return (this.terminationCondition!= null);
    }

    /**
     * Gets the value of the totalMilliseconds property.
     * 
     */
    public long getTotalMilliseconds() {
        return totalMilliseconds;
    }

    /**
     * Sets the value of the totalMilliseconds property.
     * 
     */
    public void setTotalMilliseconds(long value) {
        this.totalMilliseconds = value;
    }

    public boolean isSetTotalMilliseconds() {
        return true;
    }

}
