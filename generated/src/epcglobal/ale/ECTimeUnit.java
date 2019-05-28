
package epcglobal.ale;

import javax.xml.bind.annotation.XmlEnum;


/**
 * <p>Java class for ECTimeUnit.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ECTimeUnit">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NCName">
 *     &lt;enumeration value="MS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum ECTimeUnit {

    MS;

    public String value() {
        return name();
    }

    public static ECTimeUnit fromValue(String v) {
        return valueOf(v);
    }

}
