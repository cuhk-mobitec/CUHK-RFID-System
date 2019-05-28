package cuhk.ale.editor.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class FileEditDialog extends Dialog {

	String fileSaved = null;

	private Text textArea = null;

	private Button button = null;

	private Button button1 = null;

	private Button button2 = null;

	private boolean hasChanged = false;

	private boolean isClosing = false;

	private static final String title = "CUHK ALE ECSPEC Editor";

	private static final String NEW_LINE = System.getProperty("line.separator");

	Shell sShell = null;

	private String[] filterExtensions;

	private String filterPath;

	/**
	 * @param parent
	 */
	public FileEditDialog(Shell parent) {
		super(parent);
	}

	/**
	 * @param parent
	 * @param style
	 */
	public FileEditDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Makes the dialog visible.
	 * 
	 * @return
	 */
	public String open(final String title, String prompt) {
		Shell parent = getParent();
		sShell = new Shell(parent, SWT.TITLE | SWT.BORDER | SWT.MULTI | SWT.RESIZE
				| SWT.APPLICATION_MODAL);

		org.eclipse.swt.layout.GridLayout gridLayout2 = new GridLayout();
		org.eclipse.swt.layout.GridData gridData3 = new org.eclipse.swt.layout.GridData();
		org.eclipse.swt.layout.GridData gridData4 = new org.eclipse.swt.layout.GridData();
		org.eclipse.swt.layout.GridData gridData5 = new org.eclipse.swt.layout.GridData();
		org.eclipse.swt.layout.GridData gridData6 = new org.eclipse.swt.layout.GridData();
		textArea = new Text(sShell, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL
				| SWT.BORDER);
		button = new Button(sShell, SWT.NONE);
		button1 = new Button(sShell, SWT.NONE);
		button2 = new Button(sShell, SWT.NONE);
		sShell.setText(title);
		sShell.setLayout(gridLayout2);
		gridLayout2.numColumns = 3;
		gridLayout2.makeColumnsEqualWidth = false;
		gridData3.horizontalSpan = 3;
		gridData3.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData3.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.grabExcessVerticalSpace = true;
		textArea.setLayoutData(gridData3);
		button.setText("Load File");
		button.setLayoutData(gridData4);
		button1.setText("Save File");
		button1.setLayoutData(gridData5);
		button2.setText("Close");
		button2.setLayoutData(gridData6);
		gridData4.horizontalAlignment = org.eclipse.swt.layout.GridData.END;
		gridData4.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		gridData4.grabExcessHorizontalSpace = true;
		gridData5.horizontalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		gridData5.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		gridData6.grabExcessHorizontalSpace = true;
		sShell.setSize(new org.eclipse.swt.graphics.Point(393, 279));
		textArea.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				if (!hasChanged) {
					sShell.setText(title + " *");
					hasChanged = true;
				}
			}
		});
		button2
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						doExit();
					}
				});
		button1
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						saveFile();
					}
				});
		button
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						loadFile();
					}
				});
		sShell.addShellListener(new org.eclipse.swt.events.ShellAdapter() {
			public void shellClosed(org.eclipse.swt.events.ShellEvent e) {
				if (!isClosing) {
					e.doit = doExit();
				}
			}
		});

		// shell.setText(title);
		//
		// shell.setLayout(new GridLayout(2, true));
		//
		// Label label = new Label(shell, SWT.NULL);
		// label.setText(prompt);
		//
		// final Text text = new Text(shell, SWT.SINGLE | SWT.BORDER);
		//
		// final Button buttonOK = new Button(shell, SWT.PUSH);
		// buttonOK.setText("Ok");
		// buttonOK.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		// Button buttonCancel = new Button(shell, SWT.PUSH);
		// buttonCancel.setText("Cancel");
		//
		// text.addListener(SWT.Modify, new Listener() {
		// public void handleEvent(Event event) {
		// try {
		// value = text.getText();
		// buttonOK.setEnabled(true);
		// } catch (Exception e) {
		// buttonOK.setEnabled(false);
		// }
		// }
		// });
		//
		// buttonOK.addListener(SWT.Selection, new Listener() {
		// public void handleEvent(Event event) {
		// shell.dispose();
		// }
		// });
		//
		// buttonCancel.addListener(SWT.Selection, new Listener() {
		// public void handleEvent(Event event) {
		// value = null;
		// shell.dispose();
		// }
		// });
		// shell.addListener(SWT.Traverse, new Listener() {
		// public void handleEvent(Event event) {
		// if(event.detail == SWT.TRAVERSE_ESCAPE)
		// event.doit = false;
		// }
		// });
		//
		// text.setText("");
		sShell.open();

		Display display = parent.getDisplay();
		while (!sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		return fileSaved;
	}

	private boolean doExit() {
		if (hasChanged) {
			MessageBox mb = new MessageBox(sShell, SWT.ICON_QUESTION | SWT.YES
					| SWT.NO | SWT.CANCEL);
			mb.setText("Save Changes?");
			mb.setMessage("File has been changed. Save before exit?");
			int state = mb.open();
			if (state == SWT.YES) {
				saveFile();
			} else if (state == SWT.CANCEL) {
				return false;
			}
		}
		isClosing = true;
		sShell.close();
		sShell.dispose();
		return true;
	}

	private void loadFile() {
		FileDialog dialog = new FileDialog(sShell, SWT.OPEN);
		dialog.setFilterExtensions(filterExtensions);
		dialog.setFilterPath(filterPath);
		String result = dialog.open();
		if (result != null) {
			File f = new File(result);
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				StringBuffer buff = new StringBuffer();
				String line = br.readLine();
				while (line != null) {
					buff.append(line + NEW_LINE);
					line = br.readLine();
				}
				textArea.setText(buff.toString());
				br.close();
				sShell.setText(title);
				hasChanged = false;
				fileSaved = result;
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void saveFile() {
		FileDialog dialog = new FileDialog(sShell, SWT.SAVE);
		String result = dialog.open();
		if (result != null) {
			File f = new File(result);
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
				String text = textArea.getText();
				bw.write(text);
				bw.close();
				sShell.setText(title);
				hasChanged = false;
				fileSaved = result;

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void setFilterExtensions(String[] filterExtensions) {
		this.filterExtensions = filterExtensions;		
	}

	public void setFilterPath(String filterPath) {
		this.filterPath = filterPath; 
		
	}

}
