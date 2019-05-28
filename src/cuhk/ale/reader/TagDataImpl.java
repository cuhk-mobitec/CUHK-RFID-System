package cuhk.ale.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import cuhk.ale.ejb.interfaces.ReaderManager;
import cuhk.ale.ejb.interfaces.ReaderManagerHome;
import cuhk.ale.ejb.interfaces.ReaderManagerUtil;

public class TagDataImpl extends UnicastRemoteObject implements TagData {
	private static final long serialVersionUID = 1L;

	final static int SUCCESS = 0;

	final static int NOTFOUND = -1;

	final static int READERROR = -2;

	private final static String TAGDATAPATH = "C:/TAGDATA/";

	TagDataImpl() throws RemoteException, NamingException, CreateException {

		
		super();
		System.out.println("Adaptor Start");

		/*
		 * // sample codes for using the ReaderManager java.util.Vector tags =
		 * new java.util.Vector(); tags.add(new
		 * String("urn:epc:tag:gid-96:21.300.402")); PhysicalReaderInfo
		 * physicalReaderInfo = new PhysicalReaderInfo();
		 * physicalReaderInfo.setIPAddress("0.0.0.0");
		 * physicalReaderInfo.setManufacturer("CUHK");
		 * physicalReaderInfo.setModel("CUHKRF");
		 * readerManager.submitALEPhysicalReaderInfo("READERX",physicalReaderInfo);
		 * readerManager.submitALETags("READERX",tags);
		 */
	}

	private ReaderManager getAleIntf(String host) throws RemoteException {
		String url = "jnp://" + host + ":1099";

		try {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put("java.naming.factory.initial",
				"org.jnp.interfaces.NamingContextFactory");
		env.put("java.naming.provider.url", url);
		env.put("java.naming.factory.url.pkgs",
				"org.jboss.naming:org.jnp.interfaces");

		ReaderManagerHome readerManagerHome = (ReaderManagerHome) ReaderManagerUtil.getHome(env);
		return readerManagerHome.create();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (CreateException e) {
			e.printStackTrace();
		} finally {
		}
		
		return null;
	}

	public void read(String readerID, String tagID, DataSpec spec, String guid)
			throws RemoteException {
		System.out.println("Got a read.");
		
		ReaderManager readerManager = null;		
		try {
			readerManager = getAleIntf(RemoteServer.getClientHost());
		} catch (ServerNotActiveException e1) {
			e1.printStackTrace();
		}

		try {
			// do something here to tell the physical reader to read
			// ....
			String filename = tagID.replaceAll("\\W", "_");
			RandomAccessFile raf = new RandomAccessFile(TAGDATAPATH + filename,
					"r");
			int i = (spec.getLength());
			long filesize = new File(TAGDATAPATH + filename).length();
			i = (int) (i>filesize ? filesize : i); 
			byte[] b = new byte[i]; 
			raf.read(b, spec.getAddress(), i);
			Thread.sleep(200);
			readerManager.submitReadData(readerID, SUCCESS, b, guid);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			readerManager.submitReadData(readerID, NOTFOUND, null, guid);
		} catch (IOException e) {
			e.printStackTrace();
			readerManager.submitReadData(readerID, READERROR, null, guid);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void write(String readerID, String tagID, DataSpec spec, String guid)
			throws RemoteException {
		System.out.println("Got a write.");
		ReaderManager readerManager = null;		
		try {
			readerManager = getAleIntf(RemoteServer.getClientHost());
		} catch (ServerNotActiveException e1) {
			e1.printStackTrace();
		}

		try {
			// do something here to tell the physical reader to write
			// ....
			String filename = tagID.replaceAll("\\W", "_");
			RandomAccessFile raf = new RandomAccessFile(TAGDATAPATH + filename,
					"rws");
			raf.write(spec.getDatabytes(), spec.getAddress(), spec.getLength());
			Thread.sleep(500);
			readerManager.submitWriteData(readerID, SUCCESS, guid);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			readerManager.submitWriteData(readerID, NOTFOUND, guid);
		} catch (IOException e) {
			e.printStackTrace();
			readerManager.submitWriteData(readerID, READERROR, guid);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		// start the rmi registry programatically (cmd: start rmiregistry)
		try {
			java.rmi.registry.LocateRegistry.createRegistry(2001);
			System.out.println("RMI registry ready.");
		} catch (Exception e) {
			System.out.println("Exception when starting RMI registry:");
			e.printStackTrace();
		}

		// set the security manager
		try {
			// create a local instance of the object
			TagDataImpl tagDataServer = new TagDataImpl();

			// put the local instance in the registry
			Naming.rebind("//127.0.0.1:2001/TAGDATA-SERVER", tagDataServer);

			System.out.println("Server waiting.....");
		} catch (java.net.MalformedURLException me) {
			me.printStackTrace();
		}

		catch (RemoteException re) {
			re.printStackTrace();
		} catch (CreateException ce) {
			ce.printStackTrace();
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
	}
}
