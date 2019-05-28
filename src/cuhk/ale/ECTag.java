// $Id: ECTag.java,v 1.1 2006/06/16 16:49:18 alfred Exp $

package cuhk.ale;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import cuhk.ale.exceptions.ParseURIException;

public class ECTag implements Serializable {

	private static final long serialVersionUID = -3338269064651519712L;
	private long timestamp;
	private String reader;
	private String encoding;
	private String components;

	// TODO: add mechanism for storing tag value

	public boolean equals(Object obj) {
		if ((obj != null) && (obj.getClass().equals(this.getClass())))
			return getTagId().equals(((ECTag) obj).getTagId());  
		return false;
	}

	public int hashCode() {
		return getTagId().hashCode();
	}
	
	public String toString() {
		return getTagId();
	}
	
	public String getReader() {
		return reader;
	}
	
	public void setReader(String reader) {
		this.reader = reader;
	}

	public String getEncoding() {
		return encoding;
	}
	
	public String[] getComponents() {
		return components.split("\\.");
	}	
	
	public String getTagId() {
		return "urn:epc:tag:" + encoding + ":" + components;
	}
	
	public void setTagId(String pattern) throws ParseURIException {

		// TODO: to use regular expression to test
		if (!pattern.startsWith("urn:epc:tag:")) {
			throw new ParseURIException("Invalid EPC Tag URI: doesn't start with 'urn:epc:tag:'");
		}
		
		String[] strs = pattern.split(":");
		if (strs.length != 5) {
			throw new ParseURIException("Invalid EPC Tag URI: must have an encoding and components");
		}
		
		encoding = strs[3];
		components = strs[4];
	}	
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean matchesPattern(PatternURI pattern) throws ParseURIException {
		
		if (encoding.equals(pattern.getEncoding())) {
			String[] c = getComponents();
			String[] p = pattern.retrieveComponents();
			
			if (c.length != p.length) {
				throw new ParseURIException("the # of components inside the tag and pattern URI do not match despite of the same encoding!");
			}
			
			for (int i=0; i<c.length; i++) {
				
				if (p[i].matches("^" + Grammar.NUMERIC_COMPONENT + "$")) {
					if (!c[i].equals(p[i])) {
						return false;
					}
				}
				else if (p[i].matches("^" + Grammar.PADDED_NUMERIC_COMPONENT +"$")) {
					if (!c[i].equals(p[i])) {
						return false;
					}
				}
				else if (p[i].matches("^" + Grammar.CAGECODE_OR_DODAAC +"$")) {
					if (!c[i].equals(p[i])) {
						return false;
					}
				}
				else if (p[i].matches("^" + Grammar.RANGE_COMPONENT + "$")) {
					String[] strs = p[i].split("-");
					int lo = Integer.parseInt(strs[0].substring(1));
					int hi = Integer.parseInt(strs[1].substring(0,strs[1].length()-1));
					int ci = Integer.parseInt(c[i]);
				
					if (!(ci >= lo && ci <= hi)) {
						return false;
					}
				}
				else if (p[i].matches("^" + Grammar.STAR_COMPONENT + "$")) {
					// any c[i] can match
				}
				else {
					throw new ParseURIException("unrecognized pattern component at index:" + i);
				}
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean matchesGroup(PatternURI pattern) throws ParseURIException {
		
		if (encoding.equals(pattern.getEncoding())) {
			String[] c = getComponents();
			String[] p = pattern.retrieveComponents();
			
			if (c.length != p.length) {
				throw new ParseURIException("the # of components inside the tag and pattern URI do not match despite of the same encoding!");
			}
			
			for (int i=0; i<c.length; i++) {
				
				if (p[i].matches("^X$")) {
					// any c[i] can match
				}
				else if (p[i].matches("^" + Grammar.NUMERIC_COMPONENT + "$")) {
					if (!c[i].equals(p[i])) {
						return false;
					}
				}
				else if (p[i].matches("^" + Grammar.PADDED_NUMERIC_COMPONENT +"$")) {
					if (!c[i].equals(p[i])) {
						return false;
					}
				}
				else if (p[i].matches("^" + Grammar.CAGECODE_OR_DODAAC +"$")) {
					if (!c[i].equals(p[i])) {
						return false;
					}
				}
				else if (p[i].matches("^" + Grammar.RANGE_COMPONENT + "$")) {
					String[] strs = p[i].split("-");
					int lo = Integer.parseInt(strs[0].substring(1));
					int hi = Integer.parseInt(strs[1].substring(0,strs[1].length()-1));
					int ci = Integer.parseInt(c[i]);
				
					if (!(ci >= lo && ci <= hi)) {
						return false;
					}
				}
				else if (p[i].matches("^" + Grammar.STAR_COMPONENT + "$")) {
					// any c[i] can match
				}
				else {
					throw new ParseURIException("unrecognized pattern component at index:" + i);
				}
			}
			return true;
		}
		else {
			return false;
		}
	}

	public String toEPCURI() {
		// TODO: convert to urn:epc:id format
		return new String("urn:epc:id:...");
	}

	public String toTagURI() {
		return getTagId();
	}
	
	public String toRawHexURI() {
		// TODO: convert to urn:epc:raw hex format
		return new String("urn:epc:raw:...");		
	}

	public String toRawDecimalURI() {
		// TODO: convert to urn:epc:raw format
		return new String("urn:epc:raw:...");		
	}	
}	

