
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
 *              A ECBoundarySpec specifies how the beginning and end of event cycles
 *              are to be determined.  The startTrigger and repeatPeriod elements
 *              are mutually exclusive.  One may, however, specify a ECBoundarySpec
 *              with neither event cycle start condition (i.e., startTrigger nor
 *              repeatPeriod) present.  At least one event cycle stopping condition
 *              (stopTrigger, duration, and/or stableSetInterval) must be present.
 *             
 * 
 * <p>Java class for ECBoundarySpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ECBoundarySpec">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startTrigger" type="{urn:epcglobal:ale:xsd:1}ECTrigger" minOccurs="0"/>
 *         &lt;element name="repeatPeriod" type="{urn:epcglobal:ale:xsd:1}ECTime" minOccurs="0"/>
 *         &lt;element name="stopTrigger" type="{urn:epcglobal:ale:xsd:1}ECTrigger" minOccurs="0"/>
 *         &lt;element name="duration" type="{urn:epcglobal:ale:xsd:1}ECTime" minOccurs="0"/>
 *         &lt;element name="stableSetInterval" type="{urn:epcglobal:ale:xsd:1}ECTime" minOccurs="0"/>
 *         &lt;element name="extension" type="{urn:epcglobal:ale:xsd:1}ECBoundarySpecExtension" minOccurs="0"/>
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
@XmlType(name = "ECBoundarySpec", propOrder = {
    "startTrigger",
    "repeatPeriod",
    "stopTrigger",
    "duration",
    "stableSetInterval",
    "extension",
    "any"
})
public class ECBoundarySpec
    implements Serializable
{

    protected ECTrigger startTrigger;
    protected ECTime repeatPeriod;
    protected ECTrigger stopTrigger;
    protected ECTime duration;
    protected ECTime stableSetInterval;
    protected ECBoundarySpecExtension extension;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the startTrigger property.
     * 
     * @return
     *     possible object is
     *     {@link ECTrigger }
     *     
     */
    public ECTrigger getStartTrigger() {
        return startTrigger;
    }

    /**
     * Sets the value of the startTrigger property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECTrigger }
     *     
     */
    public void setStartTrigger(ECTrigger value) {
        this.startTrigger = value;
    }

    public boolean isSetStartTrigger() {
        return (this.startTrigger!= null);
    }

    /**
     * Gets the value of the repeatPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link ECTime }
     *     
     */
    public ECTime getRepeatPeriod() {
        return repeatPeriod;
    }

    /**
     * Sets the value of the repeatPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECTime }
     *     
     */
    public void setRepeatPeriod(ECTime value) {
        this.repeatPeriod = value;
    }

    public boolean isSetRepeatPeriod() {
        return (this.repeatPeriod!= null);
    }

    /**
     * Gets the value of the stopTrigger property.
     * 
     * @return
     *     possible object is
     *     {@link ECTrigger }
     *     
     */
    public ECTrigger getStopTrigger() {
        return stopTrigger;
    }

    /**
     * Sets the value of the stopTrigger property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECTrigger }
     *     
     */
    public void setStopTrigger(ECTrigger value) {
        this.stopTrigger = value;
    }

    public boolean isSetStopTrigger() {
        return (this.stopTrigger!= null);
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link ECTime }
     *     
     */
    public ECTime getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECTime }
     *     
     */
    public void setDuration(ECTime value) {
        this.duration = value;
    }

    public boolean isSetDuration() {
        return (this.duration!= null);
    }

    /**
     * Gets the value of the stableSetInterval property.
     * 
     * @return
     *     possible object is
     *     {@link ECTime }
     *     
     */
    public ECTime getStableSetInterval() {
        return stableSetInterval;
    }

    /**
     * Sets the value of the stableSetInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECTime }
     *     
     */
    public void setStableSetInterval(ECTime value) {
        this.stableSetInterval = value;
    }

    public boolean isSetStableSetInterval() {
        return (this.stableSetInterval!= null);
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link ECBoundarySpecExtension }
     *     
     */
    public ECBoundarySpecExtension getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECBoundarySpecExtension }
     *     
     */
    public void setExtension(ECBoundarySpecExtension value) {
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
