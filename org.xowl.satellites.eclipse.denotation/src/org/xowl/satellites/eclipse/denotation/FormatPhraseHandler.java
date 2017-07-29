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
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.xowl.satellites.eclipse.denotation.actions.FormatJsonFile;
import org.xowl.satellites.eclipse.denotation.editors.DenotationEditor;
import org.xowl.satellites.eclipse.denotation.editors.PhraseEditor;

/**
 * Implements the handler for formatting a phrase
 *
 * @author Laurent Wouters
 */
public class FormatPhraseHandler extends AbstractHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
        if (selection.size() == 1) {
            Object selected = selection.getFirstElement();
            if (selected == null)
                return null;
            if (selected instanceof IFile) {
                formatPhraseFile((IFile) selected);
                return null;
            }
        }

        IEditorPart editor = HandlerUtil.getActiveEditor(event);
        if (editor instanceof DenotationEditor) {
            DenotationEditor denotationEditor = (DenotationEditor) editor;
            Object object = denotationEditor.getSelectedPage();
            if (object instanceof PhraseEditor) {
                FileEditorInput input = ((FileEditorInput) ((PhraseEditor) object).getEditorInput());
                formatPhraseFile(input.getFile());
                return null;
            }

        }
        return null;
    }

    /**
     * Format the content of the specified phrase file
     *
     * @param file The file
     */
    private void formatPhraseFile(IFile file) {
        if (file == null)
            return;
        if (!file.getName().endsWith(Constants.FILE_PHRASE))
            return;
        Runnable action = new FormatJsonFile(file);
        Display.getDefault().asyncExec(action);
    }
}
