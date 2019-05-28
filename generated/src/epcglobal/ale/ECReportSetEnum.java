
package epcglobal.ale;

import javax.xml.bind.annotation.XmlEnum;


/**
 * <p>Java class for ECReportSetEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ECReportSetEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NCName">
 *     &lt;enumeration value="CURRENT"/>
 *     &lt;enumeration value="ADDITIONS"/>
 *     &lt;enumeration value="DELETIONS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum ECReportSetEnum {

    ADDITIONS,
    CURRENT,
    DELETIONS;

    public String value() {
        return name();
    }

    public static ECReportSetEnum fromValue(String v) {
        return valueOf(v);
    }

}
