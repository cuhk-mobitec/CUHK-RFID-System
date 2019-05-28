package cuhk.ale.reader;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface TagData extends Remote {

	// Asking the reader adaptor to read user data from the physical tag
	void read(String readerID, String tagID, DataSpec spec, String guid) throws RemoteException;
	
	// Asking the reader adaptor to write user data from the physical tag
	void write(String readerID, String tagID, DataSpec spec, String guid) throws RemoteException;
}


