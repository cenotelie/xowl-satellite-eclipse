package org.xowl.satellites.papyrus;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class ShowClassNameHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbench workbench = PlatformUI.getWorkbench();
		Shell shell = workbench.getActiveWorkbenchWindow().getShell();
		ISelection selection = workbench.getActiveWorkbenchWindow().getSelectionService().getSelection();
		MessageDialog.openInformation(shell, "Class Name", selection.getClass().getName());
		return null;
	}

}
