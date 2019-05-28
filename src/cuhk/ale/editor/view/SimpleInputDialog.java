package cuhk.ale.editor.view;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SimpleInputDialog extends Dialog{
	
	  String value;

	  /**
	   * @param parent
	   */
	  public SimpleInputDialog(Shell parent) {
	    super(parent);
	  }

	  /**
	   * @param parent
	   * @param style
	   */
	  public SimpleInputDialog(Shell parent, int style) {
	    super(parent, style);
	  }

	  /**
	   * Makes the dialog visible.
	   * 
	   * @return
	   */
	  public String open(String title, String prompt) {
	    Shell parent = getParent();
	    final Shell shell =
	      new Shell(parent, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
	    shell.setText(title);
	    shell.setSize(600,90);

	    shell.setLayout(new GridLayout(3, false));

	    Label label = new Label(shell, SWT.NULL);
	    label.setText(prompt);

	    final Text text = new Text(shell, SWT.SINGLE | SWT.BORDER );
	    
		org.eclipse.swt.layout.GridData gridData3 = new org.eclipse.swt.layout.GridData();
		gridData3.horizontalSpan = 2;
		gridData3.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData3.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.grabExcessVerticalSpace = true;
		text.setLayoutData(gridData3);
	    
	    
	    // text.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false)); 		

	    final Button buttonOK = new Button(shell, SWT.PUSH);
	    buttonOK.setText("Ok");
	    buttonOK.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
	    Button buttonCancel = new Button(shell, SWT.PUSH);
	    buttonCancel.setText("Cancel");

	    text.addListener(SWT.Modify, new Listener() {
	      public void handleEvent(Event event) {
	        try {
	          value = text.getText();
	          buttonOK.setEnabled(true);
	        } catch (Exception e) {
	          buttonOK.setEnabled(false);
	        }
	      }
	    });

	    buttonOK.addListener(SWT.Selection, new Listener() {
	      public void handleEvent(Event event) {
	        shell.dispose();
	      }
	    });

	    buttonCancel.addListener(SWT.Selection, new Listener() {
	      public void handleEvent(Event event) {
	        value = null;
	        shell.dispose();
	      }
	    });
	    shell.addListener(SWT.Traverse, new Listener() {
	      public void handleEvent(Event event) {
	        if(event.detail == SWT.TRAVERSE_ESCAPE)
	          event.doit = false;
	      }
	    });

	    text.setText("");
	    shell.open();

	    Display display = parent.getDisplay();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }

	    return value;
	  }

}
