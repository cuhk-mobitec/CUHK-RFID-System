package cuhk.ale.client;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.text.Document;
import cuhk.ale.Grammar;
import cuhk.ale.PhysicalReaderInfo;
import cuhk.ale.ejb.interfaces.ReaderManager;
import cuhk.ale.ejb.interfaces.ReaderManagerHome;
import cuhk.ale.ejb.interfaces.ReaderManagerUtil;

public class ReaderEmulator extends JApplet implements ActionListener {

	private static final long serialVersionUID = 181324381794896911L;
	private JTextField serverAddressField,readerIDField,readCycleField;
	private JTextArea tagsArea,statusArea;
	private JButton startButton,stopButton,modifyButton,clearButton;
	private Thread timerThread;
	private volatile boolean noStopRequested;

	private String serverAddress;
	private String readerID;
	private long readCycle;
	private String[] inputTags; // tags input by user, including fixed, and tags to be generated
	private String[][] tagCurrent;
	private int[][] tagLo;
	private int[][] tagHi;
	private int[][] tagLife; // current tag life count, -1 for unlimited
	private int[][] tagMaxLife;
	
	public void init() {
		getContentPane().setLayout(new BorderLayout());

		serverAddressField = new JTextField(getParameter("serverAddress"),20);
		readerIDField = new JTextField(getParameter("readerID"),10);
		readCycleField = new JTextField(getParameter("readCycle"),4);
		tagsArea = new JTextArea(getParameter("tags"));
		tagsArea.setLineWrap(true);
		tagsArea.setWrapStyleWord(true);
		JScrollPane tagsPane = new JScrollPane(tagsArea);
		tagsPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		tagsPane.setPreferredSize(new Dimension(200, 100));

		// Create some labels for the fields.
		JLabel serverAddressFieldLabel = new JLabel("Server Address: ");
		serverAddressFieldLabel.setLabelFor(serverAddressField);		
		JLabel readerIDFieldLabel = new JLabel("Reader ID: ");
		readerIDFieldLabel.setLabelFor(readerIDField);
		JLabel readCycleFieldLabel = new JLabel("Read Cycle(ms): ");
		readCycleFieldLabel.setLabelFor(readCycleField);
		JLabel tagsAreaLabel = new JLabel("Tags:");
		tagsAreaLabel.setLabelFor(tagsArea);

		// Lay out the text controls and the labels.
		JPanel controlPane = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		controlPane.setLayout(gridbag);

		JLabel[] labels = {serverAddressFieldLabel,readerIDFieldLabel,readCycleFieldLabel,tagsAreaLabel};
		JComponent[] textFields = {serverAddressField,readerIDField,readCycleField,tagsPane};
		addLabelTextRows(labels,textFields,gridbag,controlPane);

		startButton = new JButton("Start");
		startButton.setActionCommand("start");
		startButton.addActionListener(this);

		stopButton = new JButton("Stop");
		stopButton.setActionCommand("stop");
		stopButton.setEnabled(false);
		stopButton.addActionListener(this);		

		modifyButton = new JButton("Modify");
		modifyButton.setActionCommand("modify");
		modifyButton.setEnabled(false);
		modifyButton.addActionListener(this);		
		
		clearButton = new JButton("Clear");
		clearButton.setActionCommand("clear");
		clearButton.addActionListener(this);		

		controlPane.add(startButton);
		controlPane.add(stopButton);
		controlPane.add(modifyButton);
		controlPane.add(clearButton);

		c.gridwidth = GridBagConstraints.REMAINDER; // last
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1.0;

		controlPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("CUHK Reader Emulator"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// Create a text area.
		statusArea = new JTextArea();
		statusArea.setFont(new Font("Serif", Font.ITALIC, 12));
		statusArea.setLineWrap(true);
		statusArea.setWrapStyleWord(true);
		JScrollPane statusPane = new JScrollPane(statusArea);
		statusPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		statusPane.setPreferredSize(new Dimension(250, 250));
		statusPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(BorderFactory
						.createTitledBorder("Status"), BorderFactory
						.createEmptyBorder(5, 5, 5, 5)), statusPane
						.getBorder()));

		// Put everything together.
		JPanel leftPane = new JPanel(new BorderLayout());
		leftPane.add(controlPane, BorderLayout.PAGE_START);
		leftPane.add(statusPane, BorderLayout.CENTER);

		getContentPane().add(leftPane);
		readerIDField.grabFocus();
	}

	private void addLabelTextRows(JLabel[] labels, JComponent[] textFields,
			GridBagLayout gridbag, Container container) {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		int numLabels = labels.length;

		for (int i = 0; i < numLabels; i++) {
			c.gridwidth = GridBagConstraints.RELATIVE; // next-to-last
			c.fill = GridBagConstraints.NONE; // reset to default
			c.weightx = 0.0; // reset to default
			container.add(labels[i], c);

			c.gridwidth = GridBagConstraints.REMAINDER; // end row
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			container.add(textFields[i], c);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if ("start".equals(e.getActionCommand())) {
			try {
				doStart();
				statusArea.append("start\n");
			}
			catch (Exception ex) {
				statusArea.append(ex.getMessage()+"\n");				
			}			
		} else if ("clear".equals(e.getActionCommand())) {
			statusArea.setText(null);
		} else if ("stop".equals(e.getActionCommand())) {
			try {
				doStop();
				statusArea.append("stop\n");
			}
			catch (Exception ex) {				
			}			
		} else if ("modify".equals(e.getActionCommand())) {
			try {
				doStop();
				doStart();
			}
			catch (Exception ex) {
				statusArea.append(ex.getMessage()+"\n");				
			}			
		}
	}
	
	private void doStart() throws Exception {
		validateInputs();
		serverAddress = serverAddressField.getText();
		readerID = readerIDField.getText();
		readCycle = Integer.parseInt(readCycleField.getText());
		if (!tagsArea.getText().equals("")) {
			inputTags = tagsArea.getText().split(";");
		}
		else {
			inputTags = new String[0];
		}
		startThread();
		startButton.setEnabled(false);
		stopButton.setEnabled(true);
		modifyButton.setEnabled(true);
	}
	
	private void doStop() throws Exception {
		stopThread();
		stopButton.setEnabled(false);
		modifyButton.setEnabled(false);
		startButton.setEnabled(true);
	}
	
	private void validateInputs() throws Exception {
		
		if (serverAddressField.getText().equals("")) {
			throw new Exception("Server Address cannot be empty!");
		}
		
		if (readerIDField.getText().equals("")) {
			throw new Exception("Reader ID cannot be empty!");
		}
		
		if (!readCycleField.getText().matches("^" + Grammar.PADDED_NUMERIC_COMPONENT + "$")) {
			throw new Exception("Read Cycle must be numeric!");
		}		

		if (!tagsArea.getText().equals("")) {
			for (String inputTag : tagsArea.getText().split(";")) {
				// TODO: to use regular expression to validate input
				if (!inputTag.startsWith("urn:epc:tag:")) {
					throw new Exception("Invalid EPC Tag URI: doesn't start with 'urn:epc:tag:'");
				}			
			}
		}
	}

	private List<String> generateTags() {
		List<String> tags = new Vector<String>();
			
		for (int i=0; i<inputTags.length; i++) {
			String tag = generateRandomTag(i);
			if (!tags.contains(tag)) {
				tags.add(tag);
			}
		}
		return tags;
	}
	
	private void initTagInfos() {
		tagCurrent = new String[inputTags.length][];
		tagLo = new int[inputTags.length][];
		tagHi = new int[inputTags.length][];
		tagLife = new int[inputTags.length][];
		tagMaxLife = new int[inputTags.length][];
	
		for (int i=0; i<inputTags.length; i++) {
			String inputTag = inputTags[i];
			
			String[] components = inputTag.substring("urn:epc:tag:".length()).split("\\.");
			tagCurrent[i] = new String[components.length];
			tagLo[i] = new int[components.length];
			tagHi[i] = new int[components.length];
			tagLife[i] = new int[components.length];
			tagMaxLife[i] = new int[components.length];
			
			for (int j=0; j<components.length; j++) {
				//System.out.println(components[j]);
				if (components[j].matches("^" + Grammar.RANDOM_COMPONENT + "$")) {
					//System.out.println("match la");
					String[] strs = components[j].split("-");
					int lo = Integer.parseInt(strs[0].substring(1));
					strs = strs[1].split(":");
					int hi = Integer.parseInt(strs[0]);
					int life = Integer.parseInt(strs[1].substring(0,strs[1].length()-1));
					int c = (int) Math.round(Math.random() * (hi-lo) + lo);
					tagCurrent[i][j] = Integer.toString(c);
					tagLo[i][j] = lo;
					tagHi[i][j] = hi;
					tagLife[i][j] = life;
					tagMaxLife[i][j] = life;
				}
				else {
					tagCurrent[i][j] = components[j];
					tagLo[i][j] = -1;
					tagHi[i][j] = -1;
					tagLife[i][j] = -1;
					tagMaxLife[i][j] = -1;
				}
			}
		}
	}
	
	// assuming the input must of the form "urn:epc:tag:xxx:xxx.xxx....." 
	private String generateRandomTag(int i) {
		String input = inputTags[i];
		String prefix = input.substring(0,"urn:epc:tag:".length());
		String[] components = input.substring("urn:epc:tag:".length()).split("\\.");
		String output = prefix;
		
		for (int j=0; j<components.length; j++) {
			if (tagLife[i][j]==0) {
				int c = (int) Math.round(Math.random() * (tagHi[i][j]-tagLo[i][j]) + tagLo[i][j]);
				tagCurrent[i][j] = Integer.toString(c);	
				tagLife[i][j]=tagMaxLife[i][j]-1;
			}
			else if (tagLife[i][j]>0) {
				tagLife[i][j]--;
			}
			
			output += tagCurrent[i][j] + ".";
		}
		return output.substring(0,output.length()-1);
	}
	
	private void startThread() {
		noStopRequested = true;

		Runnable r = new Runnable() {
			public void run() {
				runWork();
			}
		};
		timerThread = new Thread(r, "submitServer");
		timerThread.start();
	}

	private void runWork() { // note that this is private
	
		try {
			String url = "jnp://" + serverAddress + ":1099";

			Hashtable<String,String> env = new Hashtable<String,String> ();
			env.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
			env.put("java.naming.provider.url",url);
			env.put("java.naming.factory.url.pkgs","org.jboss.naming:org.jnp.interfaces");
			
			ReaderManagerHome readerManagerHome = (ReaderManagerHome) ReaderManagerUtil.getHome(env);
			ReaderManager readerManager = readerManagerHome.create();

			PhysicalReaderInfo physicalReaderInfo = new PhysicalReaderInfo();
			physicalReaderInfo.setIPAddress("0.0.0.0");
			physicalReaderInfo.setManufacturer("CUHK");
			physicalReaderInfo.setModel("EMULATOR");
			readerManager.submitALEPhysicalReaderInfo(readerID,physicalReaderInfo);			
			
			initTagInfos();
			
			while (noStopRequested) {
				List<String> tags = generateTags();
				readerManager.submitALETags(readerID,tags);				
				statusArea.append("[" + new Date() + "] submit tags " + tags + " " + "\n");
				Document d = statusArea.getDocument();
				statusArea.select(d.getLength(), d.getLength()); 
				Thread.sleep(readCycle);
			}
		} catch (InterruptedException x) {
			// reassert interrupt
			Thread.currentThread().interrupt();
		}
		catch (Exception e) {
			statusArea.append(e.toString()+"\n");
			System.err.println("ReaderAdaptor exception: " + e.getMessage());
			e.printStackTrace();
		}		
	}

	private void stopThread() {
		noStopRequested = false;
		timerThread.interrupt();
	}
}
