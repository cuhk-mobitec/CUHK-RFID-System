
package epcglobal.ale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ECExcludePatterns complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ECExcludePatterns">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="excludePattern" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ECExcludePatterns", propOrder = {
    "excludePattern"
})
public class ECExcludePatterns
    implements Serializable
{

    @XmlElement(required = true)
    protected List<String> excludePattern;

    /**
     * Gets the value of the excludePattern property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the excludePattern property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExcludePattern().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getExcludePattern() {
        if (excludePattern == null) {
            excludePattern = new ArrayList<String>();
        }
        return this.excludePattern;
    }

    public boolean isSetExcludePattern() {
        return ((this.excludePattern!= null)&&(!this.excludePattern.isEmpty()));
    }

    public void unsetExcludePattern() {
        this.excludePattern = null;
    }

}
