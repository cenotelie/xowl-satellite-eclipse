package org.xowl.satellites.papyrus;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EObjectTreeElement;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;

public class PapyrusPropertyTester extends PropertyTester {
	
	public static final String IS_MODEL = "isModel";
	public static final String IS_PACKAGE = "isPackage";
	public static final String IS_CLASS = "isClass";

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (!(receiver instanceof IStructuredSelection)) return false;
		IStructuredSelection selection = (IStructuredSelection)receiver;
		if (selection.size() != 1) return false;
		Object object = selection.getFirstElement();
		if (!(object instanceof EObjectTreeElement)) return false;
		EObjectTreeElement tree = (EObjectTreeElement)object;
		EObject element = tree.getEObject();
		if (IS_MODEL.equals(property)) return (element instanceof Model);
		if (IS_PACKAGE.equals(property)) return (element instanceof Package);
		if (IS_CLASS.equals(property)) return (element instanceof Class);
		return false;
	}

}
