package org.xowl.satellites.pror;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.services.IServiceLocator;

public class ProrPushMenu extends ContributionItem {
	
	public static final String PUSH_REQUIREMENTS_COMMAND = "org.xowl.satellites.pror.commands.requirements.push";

	@Override
	public void fill(Menu menu, int index) {
		MenuItem item = new MenuItem(menu, SWT.CHECK, index);
		item.setText("Requirements Baseline");
		item.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ICommandService commandService = (ICommandService)((IServiceLocator)PlatformUI.getWorkbench()).getService(ICommandService.class);
				IHandlerService handlerService = (IHandlerService)((IServiceLocator)PlatformUI.getWorkbench()).getService(IHandlerService.class);
				Command command = commandService.getCommand(PUSH_REQUIREMENTS_COMMAND);
				ParameterizedCommand pcommand = new ParameterizedCommand(command, null);
				handlerService.activateHandler(PUSH_REQUIREMENTS_COMMAND, new PushRequirementsHandler());
				try {
					handlerService.executeCommand(pcommand, null);
				} catch (ExecutionException | NotDefinedException | NotEnabledException | NotHandledException ex) {
					ex.printStackTrace();
				}
			}
			
		});
	}

}
