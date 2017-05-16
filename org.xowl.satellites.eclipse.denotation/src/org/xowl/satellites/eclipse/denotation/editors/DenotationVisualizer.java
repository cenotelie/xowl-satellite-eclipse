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

package org.xowl.satellites.eclipse.denotation.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.xowl.satellites.eclipse.denotation.Constants;
import org.xowl.satellites.eclipse.editors.DenotationEditor;
import org.xowl.satellites.eclipse.editors.TriGEditor;

/**
 * Implements an editor for the visualization of a denotation
 *
 * @author Laurent Wouters
 */
public class DenotationVisualizer extends MultiPageEditorPart {
	@Override
	protected void createPages() {
		IEditorInput input = getEditorInput();
		IFile file = ((FileEditorInput) input).getFile();
		int index = 0;

		String nakedName = file.getName().substring(0, file.getName().length() - file.getFileExtension().length() - 1);
		IFile fileRepresentation = file.getParent().getFile(new Path(nakedName + Constants.FILE_REPRESENTATION));
		IFile fileSvg = file.getParent().getFile(new Path(nakedName + ".svg"));
		IFile filePhrase = file.getParent().getFile(new Path(nakedName + Constants.FILE_PHRASE));
		IFile fileMeaning = file.getParent().getFile(new Path(nakedName + Constants.FILE_MEANING));
		setPartName(nakedName);

		if (fileRepresentation != null && fileRepresentation.exists()) {
		} else if (fileSvg != null && fileSvg.exists()) {
		}

		if (filePhrase != null && filePhrase.exists()) {
			try {
				addPage(index, new TextEditor(), new FileEditorInput(filePhrase));
				setPageText(index, "Phrase");
				index++;
			} catch (PartInitException exception) {
				exception.printStackTrace();
			}
		}

		try {
			addPage(index, new DenotationEditor(), input);
			setPageText(index, "Denotation");
			index++;
		} catch (PartInitException exception) {
			exception.printStackTrace();
		}

		if (fileMeaning != null && fileMeaning.exists()) {
			try {
				addPage(index, new TriGEditor(), new FileEditorInput(fileMeaning));
				setPageText(index, "Meaning");
				index++;
			} catch (PartInitException exception) {
				exception.printStackTrace();
			}
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
}
