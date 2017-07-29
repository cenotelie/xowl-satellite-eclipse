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
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.xowl.satellites.eclipse.denotation.actions.ShareMeaning;

/**
 * A wizard to share meaning
 *
 * @author Laurent Wouters
 */
public class ShareMeaningWizard extends Wizard implements INewWizard {
    /**
     * The first wizard page
     */
    private ShareMeaningWizardPage page1;
    /**
     * The file with the meaning to share
     */
    private final IFile fileInput;

    /**
     * Constructor for this wizard.
     *
     * @param fileInput The file with the meaning to share
     */
    public ShareMeaningWizard(IFile fileInput) {
        super();
        setNeedsProgressMonitor(true);
        setWindowTitle("Share Meaning - Wizard");
        this.fileInput = fileInput;
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
    }

    @Override
    public void addPages() {
        page1 = new ShareMeaningWizardPage(fileInput);
        addPage(page1);
    }

    @Override
    public boolean performFinish() {
        try {
            ShareMeaning action = new ShareMeaning(fileInput, page1.getArtifactName(), page1.getArtifactBase(),
                    page1.getArtifactVersion(), page1.getArtifactArchetype());
            Display.getDefault().asyncExec(action);
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}