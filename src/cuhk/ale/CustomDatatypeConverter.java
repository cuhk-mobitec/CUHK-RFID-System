package cuhk.ale;

import java.util.*;
import javax.xml.bind.*;

public class CustomDatatypeConverter {
	
	public static Date parseDateTime(String s) {
		return DatatypeConverter.parseDateTime(s).getTime();
	}

	public static String printDateTime(Date dt) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(dt);
		return DatatypeConverter.printDateTime(cal);
	}
}