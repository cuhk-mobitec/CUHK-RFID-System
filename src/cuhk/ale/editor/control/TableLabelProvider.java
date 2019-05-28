package cuhk.ale.editor.control;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import cuhk.ale.editor.model.EcNode;

public class TableLabelProvider implements ITableLabelProvider {
	static Logger logger = Logger.getLogger(TableLabelProvider.class);

	public String getColumnText(Object element, int column_index) {
		
		logger.info("getColumnText");
		if (column_index == 0) {
			return "" + ((EcNode) element).getType();
		}

		if (column_index == 1) {
			return "" + ((EcNode) element).getName();
		}

		return "";
	}

	public Image getColumnImage(Object element, int column_index) {

		if (column_index != 0) {
			return null;
		}

		// if (((File) element).isDirectory()) {
		// return Util.getImageRegistry().get("folder");
		// } else {
		return Util.getImageRegistry().get("file");
		// }
	}

	public void addListener(ILabelProviderListener ilabelproviderlistener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object obj, String s) {
		return false;
	}

	public void removeListener(ILabelProviderListener ilabelproviderlistener) {
	}

}
