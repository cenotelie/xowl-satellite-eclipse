/*******************************************************************************
 * Copyright (c) 2017 Association Cénotélie (cenotelie.fr)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package org.xowl.satellites.eclipse.denotation;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.xowl.satellites.eclipse.denotation.wizards.DiagramCaptureInitWizard;

/**
 * Implements the handler for the command to capture the denotation of a
 * selected diagram
 *
 * @author Laurent Wouters
 */
public class CommandCaptureDiagramHandler extends AbstractHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
        if (selection.size() != 1)
            return null;
        Object selected = selection.getFirstElement();
        if (selected == null)
            return null;
        Diagram diagram = null;
        if (selected instanceof Diagram) {
            diagram = (Diagram) selected;
        } else if (selected instanceof EditPart) {
            Object model = ((EditPart) selected).getModel();
            if (model instanceof Diagram) {
                diagram = (Diagram) model;
            }
        }
        if (diagram == null)
            return null;

        IWizard wizard = new DiagramCaptureInitWizard(diagram);
        WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
        dialog.setTitle(wizard.getWindowTitle());
        dialog.open();
        return null;
    }
}
