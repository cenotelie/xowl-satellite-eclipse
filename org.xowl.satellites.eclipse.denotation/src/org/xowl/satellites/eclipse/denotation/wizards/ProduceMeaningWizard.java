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

package org.xowl.satellites.eclipse.denotation.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.xowl.satellites.eclipse.denotation.actions.ProduceMeaning;

/**
 * A wizard to produce the full meaning for a captured denotation
 * 
 * @author Laurent Wouters
 */
public class ProduceMeaningWizard extends Wizard implements INewWizard {
	/**
	 * The first wizard page
	 */
	private ProduceMeaningWizardPage page1;
	/**
	 * The diagram to capture
	 */
	private final IFile fileInput;

	/**
	 * Constructor for SampleNewWizard.
	 * 
	 * @param fileInput
	 *            The input file
	 */
	public ProduceMeaningWizard(IFile fileInput) {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("Produce Meaning - Wizard");
		this.fileInput = fileInput;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	@Override
	public void addPages() {
		page1 = new ProduceMeaningWizardPage(fileInput);
		addPage(page1);
	}

	@Override
	public boolean performFinish() {
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IFile filePhrase = root.getFile(new Path(page1.getFilePhrase()));
			IFile fileDenotation = root.getFile(new Path(page1.getFileDenotation()));
			IFile fileMeaning = root.getFile(new Path(page1.getFileMeaning()));
			ProduceMeaning action = new ProduceMeaning(filePhrase, fileDenotation, fileMeaning, page1.getGraph());
			Display.getDefault().asyncExec(action);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return true;
	}
}