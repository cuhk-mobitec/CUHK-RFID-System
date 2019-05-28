// $Id: Grammar.java,v 1.1 2006/06/16 16:49:18 alfred Exp $

package cuhk.ale;

public class Grammar {

	// Common Grammar Elements
	public static final String ZERO_COMPONENT			= "0";
	public static final String NON_ZERO_DIGIT			= "[1-9]";
	public static final String DIGIT 					= "[0-9]";
	public static final String UPPER_ALPHA				= "[A-Z]";
	public static final String LOWER_ALPHA				= "[a-z]";
	public static final String OTHER_CHAR				= "[!'()*+,-.:;=_]";
	public static final String NON_ZERO_COMPONENT		= NON_ZERO_DIGIT + DIGIT + "*";
	public static final String NUMERIC_COMPONENT 		= ZERO_COMPONENT + "|" + NON_ZERO_COMPONENT;
	public static final String PADDED_NUMERIC_COMPONENT = DIGIT + "+";
	public static final String UPPER_HEX_CHAR			= DIGIT + "|" + "[A-F]";
	public static final String HEX_COMPONENT			= "[" + UPPER_HEX_CHAR + "]+";
	public static final String HEX_CHAR					= UPPER_HEX_CHAR + "|" + "[a-f]";
	public static final String ESCAPE					= "%[" + HEX_CHAR + "][" + HEX_CHAR + "]";
	public static final String GS3A3CHAR				= DIGIT + "|" + UPPER_ALPHA + "|" + LOWER_ALPHA + "|" + OTHER_CHAR + "|" + ESCAPE;
	public static final String GS3A3COMPONENT			= "[" + GS3A3CHAR + "]+";
	
	// EPC Pattern URI
	public static final String STAR_COMPONENT			= "[*]"; 
	public static final String RANGE_COMPONENT			= "\\[(" + NUMERIC_COMPONENT + ")-(" + NUMERIC_COMPONENT + ")\\]";  
	
	// DoD Construct URI
	public static final String CAGECODE_OR_DODAAC_CHAR	= DIGIT + "|" + "[A-Z]";
	public static final String CAGECODE					= "[" + CAGECODE_OR_DODAAC_CHAR +"]{5}";
	public static final String DODAAC 					= "[" + CAGECODE_OR_DODAAC_CHAR +"]{6}";
	public static final String CAGECODE_OR_DODAAC		=  CAGECODE + "|" + DODAAC;
	
	// Reader Emulator
	public static final String RANDOM_COMPONENT			= "\\[(" + NUMERIC_COMPONENT + ")-(" + NUMERIC_COMPONENT + "):(" + NUMERIC_COMPONENT + ")\\]";
}
