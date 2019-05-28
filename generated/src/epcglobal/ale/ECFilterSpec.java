
package epcglobal.ale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * 
 *              A ECFilterSpec specifies what EPCs are to be included in the final
 *              report. The ECFilterSpec implements a flexible filtering scheme based on
 *              pattern lists for inclusion and exclusion.
 *             
 * 
 * <p>Java class for ECFilterSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ECFilterSpec">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="includePatterns" type="{urn:epcglobal:ale:xsd:1}ECIncludePatterns" minOccurs="0"/>
 *         &lt;element name="excludePatterns" type="{urn:epcglobal:ale:xsd:1}ECExcludePatterns" minOccurs="0"/>
 *         &lt;element name="extension" type="{urn:epcglobal:ale:xsd:1}ECFilterSpecExtension" minOccurs="0"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ECFilterSpec", propOrder = {
    "includePatterns",
    "excludePatterns",
    "extension",
    "any"
})
public class ECFilterSpec
    implements Serializable
{

    protected ECIncludePatterns includePatterns;
    protected ECExcludePatterns excludePatterns;
    protected ECFilterSpecExtension extension;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the includePatterns property.
     * 
     * @return
     *     possible object is
     *     {@link ECIncludePatterns }
     *     
     */
    public ECIncludePatterns getIncludePatterns() {
        return includePatterns;
    }

    /**
     * Sets the value of the includePatterns property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECIncludePatterns }
     *     
     */
    public void setIncludePatterns(ECIncludePatterns value) {
        this.includePatterns = value;
    }

    public boolean isSetIncludePatterns() {
        return (this.includePatterns!= null);
    }

    /**
     * Gets the value of the excludePatterns property.
     * 
     * @return
     *     possible object is
     *     {@link ECExcludePatterns }
     *     
     */
    public ECExcludePatterns getExcludePatterns() {
        return excludePatterns;
    }

    /**
     * Sets the value of the excludePatterns property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECExcludePatterns }
     *     
     */
    public void setExcludePatterns(ECExcludePatterns value) {
        this.excludePatterns = value;
    }

    public boolean isSetExcludePatterns() {
        return (this.excludePatterns!= null);
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link ECFilterSpecExtension }
     *     
     */
    public ECFilterSpecExtension getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECFilterSpecExtension }
     *     
     */
    public void setExtension(ECFilterSpecExtension value) {
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

}
