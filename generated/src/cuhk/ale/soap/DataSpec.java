
package cuhk.ale.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataSpec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataSpec">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="databytes" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="formatID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="length" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tagID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataSpec", propOrder = {
    "address",
    "databytes",
    "formatID",
    "length",
    "tagID"
})
public class DataSpec {

    protected int address;
    protected byte[] databytes;
    protected int formatID;
    protected int length;
    protected String tagID;

    /**
     * Gets the value of the address property.
     * 
     */
    public int getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     */
    public void setAddress(int value) {
        this.address = value;
    }

    /**
     * Gets the value of the databytes property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDatabytes() {
        return databytes;
    }

    /**
     * Sets the value of the databytes property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDatabytes(byte[] value) {
        this.databytes = ((byte[]) value);
    }

    /**
     * Gets the value of the formatID property.
     * 
     */
    public int getFormatID() {
        return formatID;
    }

    /**
     * Sets the value of the formatID property.
     * 
     */
    public void setFormatID(int value) {
        this.formatID = value;
    }

    /**
     * Gets the value of the length property.
     * 
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     * 
     */
    public void setLength(int value) {
        this.length = value;
    }

    /**
     * Gets the value of the tagID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagID() {
        return tagID;
    }

    /**
     * Sets the value of the tagID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagID(String value) {
        this.tagID = value;
    }

}
