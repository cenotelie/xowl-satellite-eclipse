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

import fr.cenotelie.commons.utils.http.URIUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.xowl.platform.kernel.artifacts.ArtifactArchetypeFree;

/**
 * The page for the selection of the artifact metadata
 *
 * @author Laurent Wouters
 */
public class ShareMeaningWizardPage extends WizardPage {
    /**
     * The input file
     */
    private final IFile fileInput;
    /**
     * The text input for the artifact name
     */
    private Text artifactName;
    /**
     * The text input for the artifact base
     */
    private Text artifactBase;
    /**
     * The text input for the artifact version
     */
    private Text artifactVersion;
    /**
     * The text input for the artifact archetype
     */
    private Text artifactArchetype;

    /**
     * Initializes this page
     *
     * @param fileInput The input file
     */
    public ShareMeaningWizardPage(IFile fileInput) {
        super("ShareMeaningWizardPage");
        setTitle("Share Meaning - Wizard");
        setDescription("This wizard shares the selected meaning with others on the collaboration platform.");
        this.fileInput = fileInput;
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 2;
        layout.verticalSpacing = 9;

        Label label = new Label(container, SWT.NULL);
        label.setText("Artifact name:");

        artifactName = new Text(container, SWT.BORDER | SWT.SINGLE);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        artifactName.setLayoutData(gd);
        artifactName.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        label = new Label(container, SWT.NULL);
        label.setText("Artifact family URI:");

        artifactBase = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        artifactBase.setLayoutData(gd);
        artifactBase.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        label = new Label(container, SWT.NULL);
        label.setText("Artifact version:");

        artifactVersion = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        artifactVersion.setLayoutData(gd);
        artifactVersion.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        label = new Label(container, SWT.NULL);
        label.setText("Artifact archetype:");

        artifactArchetype = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        artifactArchetype.setLayoutData(gd);
        artifactArchetype.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        initialize();
        dialogChanged();
        setControl(container);
    }

    /**
     * Initializes the content of this page
     */
    private void initialize() {
        String fileName = fileInput.getName().substring(0,
                fileInput.getName().length() - fileInput.getFileExtension().length() - 1);
        artifactName.setText(fileName);
        artifactBase.setText("http://xowl.org/artifacts/" + URIUtils.encodeComponent(fileName));
        artifactVersion.setText("v1");
        artifactArchetype.setText(ArtifactArchetypeFree.INSTANCE.getIdentifier());
    }

    /**
     * Ensures that both text fields are set.
     */
    private void dialogChanged() {
        if (getArtifactName().length() == 0) {
            updateStatus("Artifact name must be specified");
            return;
        }
        if (getArtifactBase().length() == 0) {
            updateStatus("Artifact family URI must be specified");
            return;
        }
        if (getArtifactVersion().length() == 0) {
            updateStatus("Artifact version must be specified");
            return;
        }
        if (getArtifactArchetype().length() == 0) {
            updateStatus("Artifact archetype must be specified");
            return;
        }
        updateStatus(null);
    }

    /**
     * Updates the status message of this page
     *
     * @param message The status message
     */
    private void updateStatus(String message) {
        setErrorMessage(message);
        setPageComplete(message == null);
    }

    /**
     * Gets the name of the artifact
     *
     * @return The name of the artifact
     */
    public String getArtifactName() {
        return artifactName.getText();
    }

    /**
     * Gets the base of the artifact
     *
     * @return The base of the artifact
     */
    public String getArtifactBase() {
        return artifactBase.getText();
    }

    /**
     * Gets the version of the artifact
     *
     * @return The version of the artifact
     */
    public String getArtifactVersion() {
        return artifactVersion.getText();
    }

    /**
     * Gets the archetype of the artifact
     *
     * @return The archetype of the artifact
     */
    public String getArtifactArchetype() {
        return artifactArchetype.getText();
    }
}