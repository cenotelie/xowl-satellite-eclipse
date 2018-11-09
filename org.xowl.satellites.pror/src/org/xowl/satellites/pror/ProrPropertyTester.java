package org.xowl.satellites.pror;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.gmt.modisco.infra.browser.uicore.internal.model.ModelElementItem;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.rmf.reqif10.ReqIF;

@SuppressWarnings("deprecation")
public class ProrPropertyTester extends PropertyTester {
	
	public static final String IS_REQIF = "isReqif";

	@SuppressWarnings("deprecation")
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (!(receiver instanceof IStructuredSelection)) return false;
		IStructuredSelection selection = (IStructuredSelection)receiver;
		if (selection.size() != 1) return false;
		Object object = selection.getFirstElement();
		if (!(object instanceof ModelElementItem)) return false;
		if (IS_REQIF.equals(property)) return (((ModelElementItem)object).getEObject() instanceof ReqIF);
		return false;
	}

}
