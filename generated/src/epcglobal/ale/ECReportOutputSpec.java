
package epcglobal.ale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * 
 *              ECReportOutputSpec specifies how the final set of EPCs is to be reported
 *              with respect to type.
 *             
 * 
 * <p>Java class for ECReportOutputSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ECReportOutputSpec">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="extension" type="{urn:epcglobal:ale:xsd:1}ECReportOutputSpecExtension" minOccurs="0"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *       &lt;attribute name="includeCount" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="includeEPC" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="includeRawDecimal" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="includeRawHex" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="includeTag" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ECReportOutputSpec", propOrder = {
    "extension",
    "any"
})
public class ECReportOutputSpec
    implements Serializable
{

    protected ECReportOutputSpecExtension extension;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute
    protected Boolean includeCount;
    @XmlAttribute
    protected Boolean includeEPC;
    @XmlAttribute
    protected Boolean includeRawDecimal;
    @XmlAttribute
    protected Boolean includeRawHex;
    @XmlAttribute
    protected Boolean includeTag;

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link ECReportOutputSpecExtension }
     *     
     */
    public ECReportOutputSpecExtension getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECReportOutputSpecExtension }
     *     
     */
    public void setExtension(ECReportOutputSpecExtension value) {
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
     * Gets the value of the includeCount property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIncludeCount() {
        if (includeCount == null) {
            return false;
        } else {
            return includeCount;
        }
    }

    /**
     * Sets the value of the includeCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeCount(boolean value) {
        this.includeCount = value;
    }

    public boolean isSetIncludeCount() {
        return (this.includeCount!= null);
    }

    public void unsetIncludeCount() {
        this.includeCount = null;
    }

    /**
     * Gets the value of the includeEPC property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIncludeEPC() {
        if (includeEPC == null) {
            return false;
        } else {
            return includeEPC;
        }
    }

    /**
     * Sets the value of the includeEPC property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeEPC(boolean value) {
        this.includeEPC = value;
    }

    public boolean isSetIncludeEPC() {
        return (this.includeEPC!= null);
    }

    public void unsetIncludeEPC() {
        this.includeEPC = null;
    }

    /**
     * Gets the value of the includeRawDecimal property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIncludeRawDecimal() {
        if (includeRawDecimal == null) {
            return false;
        } else {
            return includeRawDecimal;
        }
    }

    /**
     * Sets the value of the includeRawDecimal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeRawDecimal(boolean value) {
        this.includeRawDecimal = value;
    }

    public boolean isSetIncludeRawDecimal() {
        return (this.includeRawDecimal!= null);
    }

    public void unsetIncludeRawDecimal() {
        this.includeRawDecimal = null;
    }

    /**
     * Gets the value of the includeRawHex property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIncludeRawHex() {
        if (includeRawHex == null) {
            return false;
        } else {
            return includeRawHex;
        }
    }

    /**
     * Sets the value of the includeRawHex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeRawHex(boolean value) {
        this.includeRawHex = value;
    }

    public boolean isSetIncludeRawHex() {
        return (this.includeRawHex!= null);
    }

    public void unsetIncludeRawHex() {
        this.includeRawHex = null;
    }

    /**
     * Gets the value of the includeTag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIncludeTag() {
        if (includeTag == null) {
            return false;
        } else {
            return includeTag;
        }
    }

    /**
     * Sets the value of the includeTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeTag(boolean value) {
        this.includeTag = value;
    }

    public boolean isSetIncludeTag() {
        return (this.includeTag!= null);
    }

    public void unsetIncludeTag() {
        this.includeTag = null;
    }

}
