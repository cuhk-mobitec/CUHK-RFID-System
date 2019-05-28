
package cuhk.ale.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cuhk.ale.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ReadDataResponse_QNAME = new QName("urn:cuhk:ale:reader:wsdl:1", "readDataResponse");
    private final static QName _ReadData_QNAME = new QName("urn:cuhk:ale:reader:wsdl:1", "readData");
    private final static QName _WriteData_QNAME = new QName("urn:cuhk:ale:reader:wsdl:1", "writeData");
    private final static QName _WriteDataResponse_QNAME = new QName("urn:cuhk:ale:reader:wsdl:1", "writeDataResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cuhk.ale.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReadData }
     * 
     */
    public ReadData createReadData() {
        return new ReadData();
    }

    /**
     * Create an instance of {@link WriteDataResponse }
     * 
     */
    public WriteDataResponse createWriteDataResponse() {
        return new WriteDataResponse();
    }

    /**
     * Create an instance of {@link WriteData }
     * 
     */
    public WriteData createWriteData() {
        return new WriteData();
    }

    /**
     * Create an instance of {@link ReadDataResponse }
     * 
     */
    public ReadDataResponse createReadDataResponse() {
        return new ReadDataResponse();
    }

    /**
     * Create an instance of {@link DataSpec }
     * 
     */
    public DataSpec createDataSpec() {
        return new DataSpec();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:cuhk:ale:reader:wsdl:1", name = "readDataResponse")
    public JAXBElement<ReadDataResponse> createReadDataResponse(ReadDataResponse value) {
        return new JAXBElement<ReadDataResponse>(_ReadDataResponse_QNAME, ReadDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:cuhk:ale:reader:wsdl:1", name = "readData")
    public JAXBElement<ReadData> createReadData(ReadData value) {
        return new JAXBElement<ReadData>(_ReadData_QNAME, ReadData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WriteData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:cuhk:ale:reader:wsdl:1", name = "writeData")
    public JAXBElement<WriteData> createWriteData(WriteData value) {
        return new JAXBElement<WriteData>(_WriteData_QNAME, WriteData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WriteDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:cuhk:ale:reader:wsdl:1", name = "writeDataResponse")
    public JAXBElement<WriteDataResponse> createWriteDataResponse(WriteDataResponse value) {
        return new JAXBElement<WriteDataResponse>(_WriteDataResponse_QNAME, WriteDataResponse.class, null, value);
    }

}
