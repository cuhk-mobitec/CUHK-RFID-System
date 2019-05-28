package cuhk.ale.editor.control;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import cuhk.ale.editor.model.EcNode;

public class TreeLabelProvider extends LabelProvider {
	static Logger logger = Logger.getLogger(TreeLabelProvider.class);

	public String getText(Object element) {
		return ((EcNode) element).getName();
	}

	public Image getImage(Object element) {
		
		if(((EcNode)element).getType().equals("Explorer")) {
			return Util.getImageRegistry().get("folder");
		}
		return Util.getImageRegistry().get("file");
	}
}
