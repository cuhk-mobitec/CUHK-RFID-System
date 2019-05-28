package cuhk.ale.editor.view;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;

import cuhk.ale.editor.control.TreeContentProvider;
import cuhk.ale.editor.control.TreeLabelProvider;
import cuhk.ale.editor.control.Util;
import cuhk.ale.editor.model.AleSoapClient;
import cuhk.ale.editor.model.EcNode;
import cuhk.ale.editor.model.Messages;
import epcglobal.ale.ECReports;
import epcglobal.ale.ECSpec;
import epcglobal.ale.ObjectFactory;
import epcglobal.ale.soap.ECSpecValidationExceptionResponse;
import epcglobal.ale.soap.ImplementationExceptionResponse;
import epcglobal.ale.soap.NoSuchNameExceptionResponse;
import epcglobal.ale.soap.SecurityExceptionResponse;

public class Console extends ApplicationWindow {
	static Logger logger = Logger.getLogger(Console.class);

	protected EcNode selected_node = null;

	protected EcNode firstNode;

	private TreeViewer tv;

	// private TableViewer tbv;

	private MenuManager fileMainMenu;

	private MenuManager specMenu;

	public Console() {
		super(null);
		addMenuBar();
		// addToolBar(SWT.FLAT | SWT.WRAP);
		addStatusLine();

	}

	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager();
		fileMainMenu = new MenuManager(Messages.getString("Console.MenuMainText")); //$NON-NLS-1$
		fileMainMenu.add(_getVendorVersionAction);
		fileMainMenu.add(_getStandardVersionAction);
		fileMainMenu.add(_exitAction);
		menuManager.add(fileMainMenu);

		specMenu = new MenuManager(Messages.getString("Console.MenuApiText")); //$NON-NLS-1$
		specMenu.add(_defineAction);
		specMenu.add(_undefineAction);
		specMenu.add(new Separator());
		specMenu.add(_immediateAction);
		specMenu.add(new Separator());
		specMenu.add(_pollAction);
		specMenu.add(new Separator());
		specMenu.add(_subscribeAction);
		specMenu.add(_unsubscribeAction);
		specMenu.add(new Separator());
		specMenu.add(_getECSpecAction);
		// specMenu.add(_getECSpecNamesAction);
		// specMenu.add(_getSubscriberAction);
		menuManager.add(specMenu);

		return menuManager;
	}

	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		// toolBarManager.add(_createNewSpecAction);
		// toolBarManager.add(_remove);
		return toolBarManager;
	}

	protected void initializeBounds() {
		getShell().setSize(600, 500);
		// getShell().setImage(Util.getImageRegistry().get("cutag"));
	}

	protected Control createContents(Composite parent) {

		SashForm sash_form = new SashForm(parent, SWT.HORIZONTAL | SWT.NULL);
		getShell().setText(Messages.getString("Console.Title")); //$NON-NLS-1$

		tv = new TreeViewer(sash_form);
		tv.setContentProvider(new TreeContentProvider());
		tv.setLabelProvider(new TreeLabelProvider());
		EcNode rootNode = new EcNode("Root", null, "Root");
		firstNode = new EcNode(Messages.getString("Console.FirstNodeName"), null, "Explorer"); //$NON-NLS-1$
		rootNode.addSibling(firstNode);
		tv.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
		tv.setInput(rootNode);

		try {
			for (String specName : AleSoapClient.runGetECSpecNames().getString()) {
				logger.info("getECSpecName: " + specName);
				EcNode ecSpecNode = new EcNode(specName, null, "EcSpec");
				firstNode.addSibling(ecSpecNode);
				for (String notificationUrl : AleSoapClient.runGetSubscribers(specName).getString()) {
					EcNode ecSubNode = new EcNode(notificationUrl, null, "Subscriber");
					ecSpecNode.addSibling(ecSubNode);
				}
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		tv.refresh();
		tv.expandAll();

		// tbv = new TableViewer(sash_form, SWT.BORDER | SWT.FULL_SELECTION
		// | SWT.MULTI);
		//
		// tbv.setContentProvider(new TableContentProvider());
		// tbv.setLabelProvider(new TableLabelProvider());
		//
		// TableColumn column = new TableColumn(tbv.getTable(), SWT.LEFT);
		// column.setText("Type");
		// column.setWidth(200);
		//
		// column = new TableColumn(tbv.getTable(), SWT.LEFT);
		// column.setText("Content");
		// column.setWidth(300);
		//
		// tbv.getTable().setHeaderVisible(true);

		tv.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();

				if (selected_node == null || selection == null || selection.isEmpty()) {
					selected_node = firstNode;
				} else {
					selected_node = (EcNode) selection.getFirstElement();
				}

				String type = selected_node.getType();
				if (type.equals("EcSpec")) {
					specMenu.getMenu().getParentItem().setEnabled(true);
					fileMainMenu.getMenu().getParentItem().setEnabled(true);
				} else if (type.equals("Subscriber")) {
					specMenu.getMenu().getParentItem().setEnabled(true);
					fileMainMenu.getMenu().getParentItem().setEnabled(true);
				} else {
					specMenu.getMenu().getParentItem().setEnabled(true);
					fileMainMenu.getMenu().getParentItem().setEnabled(true);
				}
				// tbv.setInput(selected_node);
				setStatus(selected_node.getStatus());
			}
		});

		// tbv.addSelectionChangedListener(new ISelectionChangedListener() {
		// public void selectionChanged(SelectionChangedEvent event) {
		// IStructuredSelection selection = (IStructuredSelection) event
		// .getSelection();
		//
		// // setStatus("Number of items selected is " + selection.size());
		// }
		// });

		logger.info("createContents");

		// int weights[] = { 3, 7 };
		// sash_form.setWeights(weights);
		return sash_form;

	}

	public static void main(String[] args) {
		// BasicConfigurator.configure();
		PropertyConfigurator.configure(Messages.getString("Console.LogFileLocation")); //$NON-NLS-1$

		Console w = new Console();

		w.setBlockOnOpen(true);
		w.open();
		Display.getCurrent().dispose();
	}

	// private CreateNewSpecAction _createNewSpecAction = new
	// CreateNewSpecAction();
	// private class CreateNewSpecAction extends Action {
	// public CreateNewSpecAction() {
	// setText("C&reate new EcSpec@Alt+C");
	// setToolTipText("Create Ecspec by opening the editor");
	// }
	// public void run() {
	// String filename = openFileEditDialog();
	// int i = filename.lastIndexOf(System.getProperty("file.separator"));
	// int j = filename.lastIndexOf(".");
	// if (j < i)
	// j = filename.length();
	// String shortfilename = filename.substring(i + 1, j);
	//
	// EcNode ecNode = new EcNode(shortfilename, null, "EcSpec");
	// ecNode.setContent(filename);
	// firstNode.addSibling(ecNode);
	// tv.refresh();
	// // tbv.refresh();
	//
	// logger.info("create new is clicked");
	// }
	// }
	//
	// private RemoveSpecAction _removeSpecAction = new RemoveSpecAction();
	//
	// private class RemoveSpecAction extends Action {
	//
	// public RemoveSpecAction() {
	// setText("R&emove EcSpec@Alt+R");
	// setToolTipText("");
	// }
	//
	// public void run() {
	// if (selected_node.getType().equals("EcSpec")) {
	// selected_node.getParent().removeSibling(selected_node);
	// tv.remove(selected_node);
	// tv.refresh();
	// logger.info("remove ecspec");
	//
	// }
	// }
	// }

	private ExitAction _exitAction = new ExitAction(this);

	private class ExitAction extends Action {
		ApplicationWindow _window;

		public ExitAction(ApplicationWindow window) {
			_window = window;
			setText(Messages.getString("Console.ExitText")); //$NON-NLS-1$
			setToolTipText(Messages.getString("Console.ExitToolTip")); //$NON-NLS-1$
			// setImageDescriptor(greenImageDesc);
		}

		public void run() {
			_window.close();
		}
	}

	private DefineAction _defineAction = new DefineAction();

	private class DefineAction extends Action {
		public DefineAction() {
			setText(Messages.getString("Console.DefineText")); //$NON-NLS-1$
			setToolTipText(Messages.getString("Console.DefineToolTip")); //$NON-NLS-1$
		}

		public void run() {
			String specName;
			String specXmlFile;
			try {
				specName = openInputDialog(
						Messages.getString("Console.InputECSpecTitle"), //$NON-NLS-1$
						Messages.getString("Console.InputECSpecText"), Messages.getString("Console.InputECSpecDefault")); //$NON-NLS-1$ //$NON-NLS-2$
				specXmlFile = openFileDialog(Messages.getString("Console.InputECSpecFileTitle")); //$NON-NLS-1$
			} catch (Exception e) {
				return;
			}

			try {
				AleSoapClient.runDefine(AleSoapClient.getECSpecFromFile(specXmlFile), specName);
				EcNode ecNode = new EcNode(specName, null, "EcSpec");
				ecNode.setStatus("Last action for this node: define");
				firstNode.addSibling(ecNode);
				tv.refresh();

				// selected_node.setStatus("Status: " + "defined");
			} catch (Exception e) {
				openWarningDialog(e);
			}
		}
	}

	private UndefineAction _undefineAction = new UndefineAction();

	private class UndefineAction extends Action {

		public UndefineAction() {
			setText(Messages.getString("Console.UndefineText")); //$NON-NLS-1$
			setToolTipText(Messages.getString("Console.UndefineTooltip")); //$NON-NLS-1$
		}

		public void run() {

			if (selected_node.getType().equals("EcSpec")) {
				try {
					AleSoapClient.runUndefine(selected_node.getName());
					EcNode theNode = selected_node;
					theNode.getParent().removeSibling(theNode);
					tv.remove(selected_node);
					tv.refresh();
					logger.info("undefine");
				} catch (Exception e) {
					openWarningDialog(e);
				}
			}
		}
	}

	private PollAction _pollAction = new PollAction();

	private class PollAction extends Action {

		public PollAction() {
			setText(Messages.getString("Console.PollText")); //$NON-NLS-1$
			setToolTipText(Messages.getString("Console.PollTooltip")); //$NON-NLS-1$
		}

		public void run() {
			if (selected_node.getType().equals("EcSpec")) {
				try {
					ECReports reports = AleSoapClient.runPoll(selected_node.getName());
					selected_node.setStatus("Last action for this node: define");

					JAXBContext jc = JAXBContext.newInstance("epcglobal.ale");
					Marshaller marshaller = jc.createMarshaller();

					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					marshaller.marshal(new ObjectFactory().createECReports(reports), bos);
					showXml(
							Messages.getString("Console.ReportContentTitle") + selected_node.getName(), bos.toString()); //$NON-NLS-1$

				} catch (Exception e) {
					openWarningDialog(e);
				}
			}

			logger.info("poll");
		}
	}

	private SubscribeAction _subscribeAction = new SubscribeAction();

	private class SubscribeAction extends Action {

		public SubscribeAction() {
			setText(Messages.getString("Console.SubscribeText")); //$NON-NLS-1$
			setToolTipText(Messages.getString("Console.SubscribeTooltip")); //$NON-NLS-1$
		}

		public void run() {
			if (selected_node.getType().equals("EcSpec")) {
				String subscriberurl;
				try {
					subscriberurl = openInputDialog(Messages
							.getString("Console.ECSpecSubscriptionTitle"), //$NON-NLS-1$
							Messages.getString("Console.ECSpecSubscriptionText"), //$NON-NLS-1$
							Messages.getString("Console.ECSpecSubscriptionDefault")); //$NON-NLS-1$
				} catch (Exception e) {
					return;
				}

				try {
					AleSoapClient.runSubscribe(selected_node.getName(), subscriberurl);
				} catch (Exception e) {
					openWarningDialog(e);
				}

				EcNode ecNode = new EcNode(subscriberurl, null, "Subscriber");
				selected_node.addSibling(ecNode);
				ecNode.setStatus("Last action: subscriber");
				tv.refresh();
				logger.info("subscribe");
			}
		}
	}

	private UnsubscribeAction _unsubscribeAction = new UnsubscribeAction();

	private class UnsubscribeAction extends Action {

		public UnsubscribeAction() {
			setText(Messages.getString("Console.UnsubscribeText")); //$NON-NLS-1$
			setToolTipText(Messages.getString("Console.UnsubscribeTooltip")); //$NON-NLS-1$
		}

		public void run() {
			if (selected_node.getType().equals("Subscriber")) {
				try {
					AleSoapClient.runUnsubscribe(selected_node.getParent().getName(), selected_node
							.getName());
				} catch (Exception e) {
					openWarningDialog(e);
				}
				EcNode theNode = selected_node;
				logger.info("remove sibling from parent");
				theNode.getParent().removeSibling(theNode);
				logger.info("remove node from tree");
				tv.remove(selected_node);
				logger.info("refresh");
				tv.refresh();
				logger.info("unsubscribe");
			}
		}
	}

	private ImmediateAction _immediateAction = new ImmediateAction();

	private class ImmediateAction extends Action {

		public ImmediateAction() {
			setText(Messages.getString("Console.ImmediateText")); //$NON-NLS-1$
			setToolTipText(Messages.getString("Console.ImmediateTooltip")); //$NON-NLS-1$
		}

		public void run() {
			String specXmlFile;
			try {
				specXmlFile = openFileDialog(Messages.getString("Console.InputECSpecFileTitle"));
			} catch (Exception e) {
				return;
			} //$NON-NLS-1$
			try {
				ECReports reports = AleSoapClient.runImmediate(AleSoapClient
						.getECSpecFromFile(specXmlFile));
				JAXBContext jc = JAXBContext.newInstance("epcglobal.ale");
				Marshaller marshaller = jc.createMarshaller();

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				marshaller.marshal(new ObjectFactory().createECReports(reports), bos);
				showXml(
						Messages.getString("Console.ReportContentTitle") + selected_node.getName(), bos.toString()); //$NON-NLS-1$

			} catch (Exception e) {
				openWarningDialog(e);
			}

			selected_node.setStatus("Last action: immediate");
			logger.info("");
		}
	}

	private GetVendorVersionAction _getVendorVersionAction = new GetVendorVersionAction();

	private class GetVendorVersionAction extends Action {
		public GetVendorVersionAction() {
			setText(Messages.getString("Console.GetVendorVersionText")); //$NON-NLS-1$
			setToolTipText(Messages.getString("Console.GetVendorVersionTooltip")); //$NON-NLS-1$
		}

		public void run() {
			try {
				openOutputDialog(Messages.getString("Console.VendorVersionTitle"), AleSoapClient //$NON-NLS-1$
						.runGetVendorVersion());
			} catch (Exception e) {
				openWarningDialog(e);
			}
		}
	}

	private GetStandardVersionAction _getStandardVersionAction = new GetStandardVersionAction();

	private class GetStandardVersionAction extends Action {
		public GetStandardVersionAction() {
			setText(Messages.getString("Console.GetStandardVersionText")); //$NON-NLS-1$
			setToolTipText(Messages.getString("Console.GetStandardVersionTooltip")); //$NON-NLS-1$
		}

		public void run() {
			try {
				openOutputDialog(Messages.getString("Console.StandardVersionTitle"), AleSoapClient //$NON-NLS-1$
						.runGetStandardVersion());
			} catch (Exception e) {
				openWarningDialog(e);
			}
		}
	}

	private GetECSpecAction _getECSpecAction = new GetECSpecAction();

	private class GetECSpecAction extends Action {
		public GetECSpecAction() {
			setText(Messages.getString("Console.GetECSpecText")); //$NON-NLS-1$
			setToolTipText(Messages.getString("Console.GetECSpecTooltip")); //$NON-NLS-1$
		}

		public void run() {
			if (selected_node.getType().equals("EcSpec")) {
				try {
					ECSpec spec = AleSoapClient.runGetECSpec(selected_node.getName());
					JAXBContext jc = JAXBContext.newInstance("epcglobal.ale");
					Marshaller marshaller = jc.createMarshaller();

					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					marshaller.marshal(new ObjectFactory().createECSpec(spec), bos);
					showXml(
							Messages.getString("Console.XMLContentTitle") + selected_node.getName(), bos.toString()); //$NON-NLS-1$
					// openInputDialog(selected_node.getName(), "Content in
					// XML",
					// bos.toString());

				} catch (Exception e) {
					openWarningDialog(e);
				}
			}
		}
	}

	protected String openFileEditDialog() {
		FileEditDialog dialog = new FileEditDialog(getShell(), SWT.OPEN);
		dialog.setText("Editor");
		String[] filterExtensions = { "*.xml", "*.*" };
		dialog.setFilterExtensions(filterExtensions);
		dialog.setFilterPath("/");
		return dialog.open(Messages.getString("Console.EditorTitle"), ""); //$NON-NLS-1$
	}

	protected String openFileDialog(String title) throws Exception {
		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
		dialog.setText(title);
		String[] filterExtensions = { "*.xml", "*.*" };
		dialog.setFilterExtensions(filterExtensions);
		String filename = dialog.open();
		if (filename == null) {
			throw new Exception(Messages.getString("Console.Action_other_than_a_valid_filename")); //$NON-NLS-1$
		}
		return filename;
	}

	protected String openInputDialog(String title, String prompt, String initValue)
			throws Exception {
		InputDialog input = new InputDialog(getShell(), title, prompt, initValue, null);
		if (input.open() == Window.OK) {
			logger.info(title + " = " + input.getValue());
			return input.getValue();
		}
		throw new Exception(Messages.getString("Console.Action_other_than_OK")); //$NON-NLS-1$
	}

	protected void openOutputDialog(String title, String message) {
		MessageDialog.openInformation(getShell(), title, message);
	}

	protected void openWarningDialog(Exception e) {
		logger.warn(e.getMessage(), e);
		MessageDialog.openWarning(getShell(), "Exception Detected", e.getMessage());
	}

	protected void showXml(String title, String xml) {

		ByteOutputStream bos = new ByteOutputStream();

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xml)));

			OutputFormat format = new OutputFormat(document);
			format.setLineWidth(65);
			format.setIndenting(true);
			format.setIndent(2);
			format.setLineSeparator("\n");
			XMLSerializer serializer = new XMLSerializer(bos, format);
			serializer.serialize(document);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Display display = Display.getCurrent();
		Shell shell = new Shell(getShell());
		shell.setLayout(new FillLayout());
		Font font = new Font(shell.getDisplay(),
				Messages.getString("Console.XmlFont"), 10, SWT.NONE); //$NON-NLS-1$
		Text text = new Text(shell, SWT.H_SCROLL | SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.WRAP
				| SWT.NONE);
		text.setText(bos.toString());
		text.setFont(font);
		shell.setSize(550, 400);
		shell.setText(title);
		shell.open();
		// while (!shell.isDisposed()) {
		// if (!display.readAndDispatch())
		// display.sleep();
		// }
		// display.dispose();
	}

}
