package cuhk.ale.editor.control;

import java.net.*;

import org.eclipse.jface.resource.*;

public class Util {
	private static ImageRegistry image_registry;

	public static URL newURL(String url_name) {
		try {
			return new URL(url_name);
		} catch (MalformedURLException e) {
			throw new RuntimeException("Malformed URL " + url_name, e);
		}
	}

	public static ImageRegistry getImageRegistry() {
		if (image_registry == null) {
			image_registry = new ImageRegistry();
			image_registry.put("folder", ImageDescriptor
					.createFromURL(newURL("file:icons/folder.gif")));
			image_registry.put("file", ImageDescriptor
					.createFromURL(newURL("file:icons/file.gif")));
			image_registry.put("cutag", ImageDescriptor
					.createFromURL(newURL("file:icons/cu_tag.gif")));
		}
		return image_registry;
	}
}