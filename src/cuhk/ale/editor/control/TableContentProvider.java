package cuhk.ale.editor.control;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import cuhk.ale.editor.model.EcNode;

public class TableContentProvider implements IStructuredContentProvider {
	
	static Logger logger = Logger.getLogger(TableContentProvider.class);
	
	public Object[] getElements(Object element) {
		Object[] kids = null;
		kids = ((EcNode) element).getSibling();
		return kids == null ? new Object[0] : kids;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object old_object, Object new_object) {
	}
}