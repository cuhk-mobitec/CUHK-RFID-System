package cuhk.ale.reader;

import java.io.Serializable;

public class DataResult implements Serializable {
	

	private static final long serialVersionUID = 1L;	
	private String readerID;
	private int status;
	private byte[] data;
	private String guid;

	
	public byte[] getData() {
		return data;
	}
	public DataResult(String readerID, int status, byte[] data, String guid) {
		super();
		this.readerID = readerID;
		this.status = status;
		this.guid = guid;
		this.data = data;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getReaderID() {
		return readerID;
	}
	public void setReaderID(String readerID) {
		this.readerID = readerID;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
