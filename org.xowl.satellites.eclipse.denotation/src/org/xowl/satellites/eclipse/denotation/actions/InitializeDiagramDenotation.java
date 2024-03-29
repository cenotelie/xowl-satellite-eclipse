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

package org.xowl.satellites.eclipse.denotation.actions;

import fr.cenotelie.commons.utils.IOUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat;
import org.eclipse.gmf.runtime.diagram.ui.render.util.CopyToImageUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.xowl.infra.denotation.phrases.Phrase;
import org.xowl.satellites.eclipse.denotation.Constants;
import org.xowl.satellites.eclipse.denotation.parsers.GMFDiagramParser;

import java.io.ByteArrayInputStream;

/**
 * Initializes the captured denotation of a GMF diagram
 *
 * @author Laurent Wouters
 */
public class InitializeDiagramDenotation implements Runnable {
    /**
     * The diagram
     */
    private final Diagram diagram;
    /**
     * The container for the files to generate
     */
    private final IContainer targetContainer;
    /**
     * The name of the target files
     */
    private final String targetName;

    /**
     * Initializes this action
     *
     * @param diagram         The diagram
     * @param targetContainer The container for the files to generate
     * @param targetName      The name of the target files
     */
    public InitializeDiagramDenotation(Diagram diagram, IContainer targetContainer, String targetName) {
        this.diagram = diagram;
        this.targetContainer = targetContainer;
        this.targetName = targetName;
    }

    @Override
    public void run() {
        try {
            doRun();
        } catch (Exception exception) {
            MessageDialog.openError(Display.getCurrent().getActiveShell(), "Initialize Diagram Denotation",
                    exception.getMessage());
            exception.printStackTrace();
        }
    }

    /**
     * Executes this action
     *
     * @throws Exception When an error occurs
     */
    private void doRun() throws Exception {
        final IFile fileRepresentation = targetContainer.getFile(new Path(targetName + ".svg"));
        if (!fileRepresentation.exists())
            fileRepresentation.create(new ByteArrayInputStream(new byte[]{}), true, null);

        final IFile filePhrase = targetContainer.getFile(new Path(targetName + Constants.FILE_PHRASE));
        GMFDiagramParser parser = new GMFDiagramParser();
        Phrase phrase = parser.parse(diagram);
        if (!filePhrase.exists())
            filePhrase.create(new ByteArrayInputStream(new byte[]{}), true, null);
        filePhrase.setContents(new ByteArrayInputStream(phrase.serializedJSON().getBytes(IOUtils.CHARSET)), IFile.FORCE,
                null);

        final IFile fileDenotation = targetContainer.getFile(new Path(targetName + Constants.FILE_DENOTATION));
        if (!fileDenotation.exists())
            fileDenotation.create(new ByteArrayInputStream(new byte[]{}), true, null);

        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                CopyToImageUtil util = new CopyToImageUtil();
                try {
                    util.copyToImage(diagram, fileRepresentation.getLocation(), ImageFileFormat.SVG,
                            new NullProgressMonitor(), PreferencesHint.USE_DEFAULTS);
                } catch (CoreException exception) {
                    MessageDialog.openError(Display.getCurrent().getActiveShell(), "Initialize Diagram Denotation",
                            exception.getMessage());
                    exception.printStackTrace();
                }

                IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                IEditorDescriptor descriptor = PlatformUI.getWorkbench().getEditorRegistry()
                        .getDefaultEditor(fileDenotation.getName());
                try {
                    page.openEditor(new FileEditorInput(fileDenotation), descriptor.getId());
                } catch (CoreException exception) {
                    MessageDialog.openError(Display.getCurrent().getActiveShell(), "Initialize Diagram Denotation",
                            exception.getMessage());
                    exception.printStackTrace();
                }

                MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Initialize Diagram Denotation",
                        "Initialization is complete.");
            }
        });
    }
}
