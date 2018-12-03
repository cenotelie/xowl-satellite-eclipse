package org.xowl.satellites.pror;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.internal.resources.File;
import org.eclipse.jface.viewers.TreeSelection;

@SuppressWarnings("restriction")
public class ProrPropertyTester extends PropertyTester {
	
	public static final String IS_REQIF = "isReqif";

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (!(receiver instanceof TreeSelection)) return false;
		TreeSelection selection = (TreeSelection)receiver;
		if (selection.size() != 1) return false;
		Object object = selection.getFirstElement();
		if (!(object instanceof File)) return false;
		File file = (File)object;
		//if (IS_REQIF.equals(property)) return file.getName().endsWith(".reqif");
		if (IS_REQIF.equals(property)) return "reqif".equalsIgnoreCase(file.getFileExtension());
		return false;
	}

}
