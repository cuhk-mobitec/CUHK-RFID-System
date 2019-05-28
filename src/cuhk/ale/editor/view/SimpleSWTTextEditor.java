package cuhk.ale.editor.view;

/*******************************************************************************
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

public class SimpleSWTTextEditor {

	org.eclipse.swt.widgets.Shell sShell = null; //  @jve:decl-index=0:visual-constraint="19,7"

	private Text textArea = null;

	private Button button = null;

	private Button button1 = null;

	private Button button2 = null;

	private boolean hasChanged = false;

	private boolean isClosing = false;

	private static final String title = "CUHK ALE ECSPEC Editor";

	private static final String NEW_LINE = System.getProperty("line.separator");

	public static void main(String[] args) {
		/* Before this is run, be sure to set up the following in the launch configuration 
		 * (Arguments->VM Arguments) for the correct SWT library path. 
		 * The following is a windows example:
		 * -Djava.library.path="installation_directory\plugins\org.eclipse.swt.win32_3.0.0\os\win32\x86"
		 */
		org.eclipse.swt.widgets.Display display = org.eclipse.swt.widgets.Display
				.getDefault();
		SimpleSWTTextEditor thisClass = new SimpleSWTTextEditor();
		thisClass.createSShell();
		thisClass.sShell.open();

		while (!thisClass.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	/**
	 * This method initializes sShell
	 */
	protected void createSShell() {
		sShell = new org.eclipse.swt.widgets.Shell();
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
	}

	private void loadFile() {
		FileDialog dialog = new FileDialog(sShell, SWT.OPEN);
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
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
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
}
