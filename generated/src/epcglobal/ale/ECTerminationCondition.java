
package epcglobal.ale;

import javax.xml.bind.annotation.XmlEnum;


/**
 * <p>Java class for ECTerminationCondition.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ECTerminationCondition">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NCName">
 *     &lt;enumeration value="TRIGGER"/>
 *     &lt;enumeration value="DURATION"/>
 *     &lt;enumeration value="STABLE_SET"/>
 *     &lt;enumeration value="UNREQUEST"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum ECTerminationCondition {

    DURATION,
    STABLE_SET,
    TRIGGER,
    UNREQUEST;

    public String value() {
        return name();
    }

    public static ECTerminationCondition fromValue(String v) {
        return valueOf(v);
    }

}
