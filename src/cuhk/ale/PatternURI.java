// $Id: PatternURI.java,v 1.2 2006/09/18 14:02:24 alfred Exp $

package cuhk.ale;

import java.io.Serializable;
import cuhk.ale.exceptions.ParseURIException;
import cuhk.ale.exceptions.InvalidURIException;

public class PatternURI implements Serializable {

	//private static final long serialVersionUID = -3839406217330864716L;
	private String encoding;
	private String component;
	
	// TODO: need to rework to do better checking
	public PatternURI(String pattern) throws ParseURIException {
		super();
		if (!pattern.startsWith("urn:epc:pat:")) {
			throw new ParseURIException("Invalid EPC Pattern URI: doesn't start with 'urn:epc:pat:'");
		}
		
		String[] strs = pattern.split(":");
		if (strs.length != 5) {
			throw new ParseURIException("Invalid EPC Pattern URI: must have an encoding and components");
		}
		
		encoding = strs[3];
		component = strs[4];		
	}
	
	public PatternURI(String component, String encoding ) {
		this.encoding = encoding;
		this.component = component;
	}
	

	public String toString() {
		return "urn:epc:pat:" + encoding + ":" + component;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public String[] retrieveComponents() {
		return component.split("\\.");
	}

	public String formGroupName(ECTag tag) {

		String[] i = retrieveComponents();
		String[] epc = tag.getComponents();

		String result = "urn:epc:pat:" + encoding + ":";
		
		for (int k=0; k<i.length; k++) {

			if (i[k].matches("^" + Grammar.STAR_COMPONENT + "$")) {
				result += "*";
			}
			else if (i[k].matches("^" + Grammar.RANGE_COMPONENT + "$")) {
				result += i[k];
			}
			else {
				result += epc[k];
			}
			result += ".";
		}
		return result.substring(0,result.length()-1);
	}


	public String getComponent() {
		return component;
	}


	public void setComponent(String component) {
		this.component = component;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}


}
