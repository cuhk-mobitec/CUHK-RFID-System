
package cuhk.ale.soap.server.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "writeData", namespace = "urn:cuhk:ale:reader:wsdl:1")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "writeData", namespace = "urn:cuhk:ale:reader:wsdl:1")
public class WriteData {

    @XmlElement(name = "arg0", namespace = "")
    private cuhk.ale.reader.DataSpec arg0;

    /**
     * 
     * @return
     *     returns DataSpec
     */
    public cuhk.ale.reader.DataSpec getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(cuhk.ale.reader.DataSpec arg0) {
        this.arg0 = arg0;
    }

}
