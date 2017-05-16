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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.xowl.infra.denotation.Denotation;
import org.xowl.satellites.eclipse.denotation.Constants;

/**
 * The page for the selection of the target files
 * 
 * @author Laurent Wouters
 */
public class ProduceMeaningWizardPage extends WizardPage {
	/**
	 * The input file
	 */
	private final IFile fileInput;
	/**
	 * The text input for the selection of the .phrase file
	 */
	private Text filePhrase;
	/**
	 * The text input for the selection of the .denotation file
	 */
	private Text fileDenotation;
	/**
	 * The text input for the selection of the target file
	 */
	private Text fileMeaning;
	/**
	 * The text input for the graph URI
	 */
	private Text graph;

	/**
	 * Initializes this page
	 * 
	 * @param fileInput
	 *            The input file
	 */
	public ProduceMeaningWizardPage(IFile fileInput) {
		super("ProduceMeaningWizardPage");
		setTitle("Produce Meaning - Wizard");
		setDescription("This wizard produces the target meaning for a captured denotation.");
		this.fileInput = fileInput;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;

		Label label = new Label(container, SWT.NULL);
		label.setText("Phrase file:");

		filePhrase = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		filePhrase.setLayoutData(gd);
		filePhrase.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ResourceSelectionDialog dialog = new ResourceSelectionDialog(getShell(),
						ResourcesPlugin.getWorkspace().getRoot(), "Select .phrase file");
				if (dialog.open() == ContainerSelectionDialog.OK) {
					Object[] result = dialog.getResult();
					if (result.length == 1) {
						filePhrase.setText(((Path) result[0]).toString());
					}
				}
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("Denotation file:");

		fileDenotation = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fileDenotation.setLayoutData(gd);
		fileDenotation.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ResourceSelectionDialog dialog = new ResourceSelectionDialog(getShell(),
						ResourcesPlugin.getWorkspace().getRoot(), "Select .denotation file");
				if (dialog.open() == ContainerSelectionDialog.OK) {
					Object[] result = dialog.getResult();
					if (result.length == 1) {
						fileDenotation.setText(((Path) result[0]).toString());
					}
				}
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("Meaning file:");

		fileMeaning = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fileMeaning.setLayoutData(gd);
		fileMeaning.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				SaveAsDialog dialog = new SaveAsDialog(getShell());
				if (dialog.open() == ContainerSelectionDialog.OK) {
					fileMeaning.setText(dialog.getResult().toString());
				}
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("Meaning graph:");

		graph = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		graph.setLayoutData(gd);
		graph.addModifyListener(new ModifyListener() {
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
		if (fileInput.getName().endsWith(Constants.FILE_PHRASE)) {
			String nakedName = fileInput.getName().substring(0,
					fileInput.getName().length() - Constants.FILE_PHRASE.length());
			filePhrase.setText(fileInput.getFullPath().toString());
			IFile fileOther = fileInput.getParent().getFile(new Path(nakedName + Constants.FILE_DENOTATION));
			if (fileOther != null && fileOther.exists())
				fileDenotation.setText(fileOther.getFullPath().toString());
			IFile file = fileInput.getParent().getFile(new Path(nakedName + Constants.FILE_MEANING));
			fileMeaning.setText(file.getFullPath().toString());
		} else if (fileInput.getName().endsWith(Constants.FILE_DENOTATION)) {
			String nakedName = fileInput.getName().substring(0,
					fileInput.getName().length() - Constants.FILE_DENOTATION.length());
			fileDenotation.setText(fileInput.getFullPath().toString());
			IFile fileOther = fileInput.getParent().getFile(new Path(nakedName + Constants.FILE_PHRASE));
			if (fileOther != null && fileOther.exists())
				filePhrase.setText(fileOther.getFullPath().toString());
			IFile file = fileInput.getParent().getFile(new Path(nakedName + Constants.FILE_MEANING));
			fileMeaning.setText(file.getFullPath().toString());
		}
		graph.setText(Denotation.GRAPH_SEMES);
	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {
		IContainer container = ResourcesPlugin.getWorkspace().getRoot();
		if (getFilePhrase().length() == 0) {
			updateStatus("Phrase file must be specified");
			return;
		}
		if (getFileDenotation().length() == 0) {
			updateStatus("Denotation file must be specified");
			return;
		}
		if (getFileMeaning().length() == 0) {
			updateStatus("Meaning file must be specified");
			return;
		}
		if (getGraph().length() == 0) {
			updateStatus("Meaning graph must be specified");
			return;
		}

		IFile file = container.getFile(new Path(getFilePhrase()));
		if (!file.exists() || !file.isAccessible()) {
			updateStatus("Phrase file must exist and be accessible");
			return;
		}
		file = container.getFile(new Path(getFileDenotation()));
		if (!file.exists() || !file.isAccessible()) {
			updateStatus("Denotation file must exist and be accessible");
			return;
		}
		updateStatus(null);
	}

	/**
	 * Updates the status message of this page
	 * 
	 * @param message
	 *            The status message
	 */
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	/**
	 * Gets the path to the phrase file
	 * 
	 * @return The path to the phrase file
	 */
	public String getFilePhrase() {
		return filePhrase.getText();
	}

	/**
	 * Gets the path to the denotation file
	 * 
	 * @return The path to the denotation file
	 */
	public String getFileDenotation() {
		return fileDenotation.getText();
	}

	/**
	 * Gets the path to the target file
	 * 
	 * @return The path to the target file
	 */
	public String getFileMeaning() {
		return fileMeaning.getText();
	}

	/**
	 * Gets the graph URI for the meaning
	 * 
	 * @return The graph URI for the meaning
	 */
	public String getGraph() {
		return graph.getText();
	}
}