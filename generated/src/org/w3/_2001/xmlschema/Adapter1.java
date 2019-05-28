
package org.w3._2001.xmlschema;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, Date>
{


    public Date unmarshal(String value) {
        return (cuhk.ale.CustomDatatypeConverter.parseDateTime(value));
    }

    public String marshal(Date value) {
        return (cuhk.ale.CustomDatatypeConverter.printDateTime(value));
    }

}
