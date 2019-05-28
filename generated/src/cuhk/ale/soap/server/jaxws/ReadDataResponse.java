
package cuhk.ale.soap.server.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "readDataResponse", namespace = "urn:cuhk:ale:reader:wsdl:1")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "readDataResponse", namespace = "urn:cuhk:ale:reader:wsdl:1")
public class ReadDataResponse {

    @XmlElement(name = "return", namespace = "")
    private byte[] _return;

    /**
     * 
     * @return
     *     returns byte[]
     */
    public byte[] get_return() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void set_return(byte[] _return) {
        this._return = _return;
    }

}
