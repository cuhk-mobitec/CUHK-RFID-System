package cuhk.ale.editor.control;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import cuhk.ale.editor.model.EcNode;

public class TreeContentProvider implements ITreeContentProvider {
	static Logger logger = Logger.getLogger(TreeContentProvider.class);

	public Object[] getChildren(Object element) {
		// Object[] kids = ((File) element).listFiles();
		
		// logger.info("getChildren");

		Object[] kids = ((EcNode) element).getSibling();
		
		return kids == null ? new Object[0] : kids;
	}

	public Object[] getElements(Object element) {
		return getChildren(element);
	}

	public boolean hasChildren(Object element) {
		// logger.info(getChildren(element).length );
		return getChildren(element).length > 0;
	}

	public Object getParent(Object element) {
		// logger.info("getParent");

		return ((EcNode) element).getParent();
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object old_input, Object new_input) {
	}
}
