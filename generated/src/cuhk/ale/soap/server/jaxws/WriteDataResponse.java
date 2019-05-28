
package cuhk.ale.soap.server.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "writeDataResponse", namespace = "urn:cuhk:ale:reader:wsdl:1")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "writeDataResponse", namespace = "urn:cuhk:ale:reader:wsdl:1")
public class WriteDataResponse {

    @XmlElement(name = "return", namespace = "")
    private int _return;

    /**
     * 
     * @return
     *     returns int
     */
    public int get_return() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void set_return(int _return) {
        this._return = _return;
    }

}
