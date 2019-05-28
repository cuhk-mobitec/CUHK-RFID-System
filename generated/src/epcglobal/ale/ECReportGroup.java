
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
 * <p>Java class for ECReportGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ECReportGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="groupList" type="{urn:epcglobal:ale:xsd:1}ECReportGroupList" minOccurs="0"/>
 *         &lt;element name="groupCount" type="{urn:epcglobal:ale:xsd:1}ECReportGroupCount" minOccurs="0"/>
 *         &lt;element name="extension" type="{urn:epcglobal:ale:xsd:1}ECReportGroupExtension" minOccurs="0"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *       &lt;attribute name="groupName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ECReportGroup", propOrder = {
    "groupList",
    "groupCount",
    "extension",
    "any"
})
public class ECReportGroup
    implements Serializable
{

    protected ECReportGroupList groupList;
    protected ECReportGroupCount groupCount;
    protected ECReportGroupExtension extension;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute
    protected String groupName;

    /**
     * Gets the value of the groupList property.
     * 
     * @return
     *     possible object is
     *     {@link ECReportGroupList }
     *     
     */
    public ECReportGroupList getGroupList() {
        return groupList;
    }

    /**
     * Sets the value of the groupList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECReportGroupList }
     *     
     */
    public void setGroupList(ECReportGroupList value) {
        this.groupList = value;
    }

    public boolean isSetGroupList() {
        return (this.groupList!= null);
    }

    /**
     * Gets the value of the groupCount property.
     * 
     * @return
     *     possible object is
     *     {@link ECReportGroupCount }
     *     
     */
    public ECReportGroupCount getGroupCount() {
        return groupCount;
    }

    /**
     * Sets the value of the groupCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECReportGroupCount }
     *     
     */
    public void setGroupCount(ECReportGroupCount value) {
        this.groupCount = value;
    }

    public boolean isSetGroupCount() {
        return (this.groupCount!= null);
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link ECReportGroupExtension }
     *     
     */
    public ECReportGroupExtension getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECReportGroupExtension }
     *     
     */
    public void setExtension(ECReportGroupExtension value) {
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
     * Gets the value of the groupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the value of the groupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupName(String value) {
        this.groupName = value;
    }

    public boolean isSetGroupName() {
        return (this.groupName!= null);
    }

}
