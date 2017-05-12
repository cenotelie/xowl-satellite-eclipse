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

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat;
import org.eclipse.gmf.runtime.diagram.ui.render.util.CopyToImageUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * A wizard to initialize the capture of the denotation of a diagram
 * 
 * @author Laurent Wouters
 */
public class DiagramCaptureInitWizard extends Wizard implements INewWizard {
	/**
	 * The first wizard page
	 */
	private DiagramCaptureInitWizardPage page1;
	/**
	 * The diagram to capture
	 */
	private final Diagram diagram;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public DiagramCaptureInitWizard(Diagram diagram) {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("Capture Diagram Meaning - Wizard");
		this.diagram = diagram;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	@Override
	public void addPages() {
		page1 = new DiagramCaptureInitWizardPage(diagram);
		addPage(page1);
	}

	@Override
	public boolean performFinish() {
		final String containerName = page1.getContainerName();
		final String fileName = page1.getFileName();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, fileName, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Finishes the wizard
	 */
	private void doFinish(String containerName, String fileName, final IProgressMonitor monitor) throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException("Container \"" + containerName + "\" does not exist.");
		}
		IContainer container = (IContainer) resource;

		monitor.beginTask("Exporting diagram to " + fileName + ".svg", 1);
		final IFile file = container.getFile(new Path(fileName + ".svg"));
		if (!file.exists())
			file.create(new ByteArrayInputStream(new byte[] {}), true, monitor);
		Runnable runnable = new Runnable() {
			public void run() {
				CopyToImageUtil util = new CopyToImageUtil();
				try {
					util.copyToImage(diagram, file.getLocation(), ImageFileFormat.SVG, monitor,
							PreferencesHint.USE_DEFAULTS);
				} catch (CoreException exception) {
					exception.printStackTrace();
				}
			}
		};
		Display.getDefault().asyncExec(runnable);
		monitor.worked(1);
		/*
		 * monitor.setTaskName("Opening file for editing...");
		 * getShell().getDisplay().asyncExec(new Runnable() { public void run()
		 * { IWorkbenchPage page =
		 * PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		 * try { IDE.openEditor(page, file, true); } catch (PartInitException e)
		 * { } } });
		 */
	}

	/**
	 * Throws a core exception
	 * 
	 * @param message
	 *            The message's exception
	 * @throws CoreException
	 *             The exception
	 */
	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "org.xowl.satellites.eclipse.denotation", IStatus.OK, message, null);
		throw new CoreException(status);
	}
}