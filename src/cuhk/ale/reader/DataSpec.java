package cuhk.ale.reader;

import java.io.Serializable;

public class DataSpec implements Serializable {
	
	private static final long serialVersionUID = 1L;
	final static int BYTE_ADDRESSING = 0; //the simplest addressing scheme, every byte has its address. 
	final static int PAGE_ADDRESSING = 1; //use page number to address data memory, always read and write from the start of a page.
	
	private String tagID;
	private int formatID;
	private int address;
	private int length;
	private byte databytes[];
	
	public DataSpec(String tagID, int formatID, int address, int length) {
		super();
		this.tagID = tagID;
		this.formatID = formatID;
		this.address = address;
		this.length = length;
		this.databytes = null;
	}

	public DataSpec() {
		super();
	}

	public DataSpec(String tagID, int formatID, int address, int length, byte[] databytes) {
		super();
		this.tagID = tagID;
		this.formatID = formatID;
		this.address = address;
		this.length = length;
		this.databytes = databytes;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public byte[] getDatabytes() {
		return databytes;
	}

	public void setDatabytes(byte[] databytes) {
		this.databytes = databytes;
	}

	public int getFormatID() {
		return formatID;
	}

	public void setFormatID(int formatID) {
		this.formatID = formatID;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getTagID() {
		return tagID;
	}

	public void setTagID(String tagID) {
		this.tagID = tagID;
	}
	
	
}
