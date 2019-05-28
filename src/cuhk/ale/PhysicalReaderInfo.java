package cuhk.ale;

import java.io.Serializable;

// for use in submitALEReaderInfo in ReaderManager

public class PhysicalReaderInfo implements Serializable {

	private static final long serialVersionUID = -4326556148069489029L;
	private String manufacturer;
	private String model;
	private String IPAddress;
	
	public PhysicalReaderInfo() {
		super();
	}

	public String getIPAddress() {
		return IPAddress;
	}

	public void setIPAddress(String address) {
		IPAddress = address;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
}
