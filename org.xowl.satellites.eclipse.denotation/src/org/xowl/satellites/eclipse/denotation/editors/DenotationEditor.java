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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.xowl.satellites.eclipse.denotation.Constants;

/**
 * Implements an editor for a complete denotation (representation, signs,
 * denotation and meaning)
 *
 * @author Laurent Wouters
 */
public class DenotationEditor extends MultiPageEditorPart {
	/**
	 * The text for the representation page
	 */
	private static final String PAGE_REPRESENTATION = "Representation";
	/**
	 * The text for the phrase page
	 */
	private static final String PAGE_PHRASE = "Phrase";
	/**
	 * The text for the denotation page
	 */
	private static final String PAGE_DENOTATION = "Denotation";
	/**
	 * The text for the meaning page
	 */
	private static final String PAGE_MEANING = "Meaning";

	/**
	 * The resource listener for parts of this denotation editor
	 *
	 * @author Laurent Wouters
	 */
	private class ResourceListener implements IResourceChangeListener {
		/**
		 * The naked name for the files of the denotation
		 */
		private final String nakedName;
		/**
		 * The observed path
		 */
		private final IPath observedPath;

		/**
		 * Initializes this listener
		 * 
		 * @param container
		 *            The container to observe
		 * @param nakedName
		 *            The naked name for the files of the denotation
		 */
		public ResourceListener(IContainer container, String nakedName) {
			this.nakedName = nakedName;
			this.observedPath = container.getFullPath();
		}

		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta delta = event.getDelta().findMember(observedPath);
			if (delta == null)
				return;
			for (IResourceDelta child : delta.getAffectedChildren()) {
				if (!(child.getResource() instanceof IFile))
					continue;
				String resource = child.getResource().getName();
				String resourceExtension = child.getResource().getFileExtension();
				if (resourceExtension == null)
					continue;
				String resourceNakedName = resource.substring(0, resource.length() - resourceExtension.length() - 1);
				if (!nakedName.equals(resourceNakedName))
					continue;
				if (child.getKind() == IResourceDelta.ADDED)
					onResourceAdded((IFile) child.getResource(), "." + resourceExtension);
				else if (child.getKind() == IResourceDelta.REMOVED)
					onResourceRemoved((IFile) child.getResource(), "." + resourceExtension);
				else if (child.getKind() == IResourceDelta.CONTENT)
					onResourceChanged((IFile) child.getResource(), "." + resourceExtension);
			}
		}
	}

	/**
	 * The resource listener
	 */
	private ResourceListener resourceListener;

	/**
	 * The inner denotation file editor
	 */
	private DenotationFileEditor denotationEditor;

	@Override
	protected void createPages() {
		IEditorInput input = getEditorInput();
		IFile file = ((FileEditorInput) input).getFile();

		String nakedName = file.getName().substring(0, file.getName().length() - file.getFileExtension().length() - 1);
		resourceListener = new ResourceListener(file.getParent(), nakedName);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceListener, IResourceChangeEvent.POST_CHANGE);

		// get the files for the editor parts
		IFile fileRepresentation = file.getParent().getFile(new Path(nakedName + Constants.FILE_REPRESENTATION));
		IFile fileSvg = file.getParent().getFile(new Path(nakedName + ".svg"));
		IFile filePhrase = file.getParent().getFile(new Path(nakedName + Constants.FILE_PHRASE));
		IFile fileDenotation = file.getParent().getFile(new Path(nakedName + Constants.FILE_DENOTATION));
		IFile fileMeaning = file.getParent().getFile(new Path(nakedName + Constants.FILE_MEANING));
		setPartName(nakedName);

		// initialize the editor parts
		int index = 0;
		if (fileRepresentation != null && fileRepresentation.exists())
			doAddPageForRepresentation(fileRepresentation, index++);
		if (fileSvg != null && fileSvg.exists())
			doAddPageForRepresentationSvg(fileSvg, index++);
		if (filePhrase != null && filePhrase.exists())
			doAddPageForPhrase(filePhrase, index++);
		if (fileDenotation != null && fileDenotation.exists())
			doAddPageForDenotation(fileDenotation, index++);
		if (fileMeaning != null && fileMeaning.exists())
			doAddPageForMeaning(fileMeaning, index++);
	}

	/**
	 * Adds an editor page for the representation
	 * 
	 * @param file
	 *            The representation file
	 * @param index
	 *            The index for the editor
	 */
	private void doAddPageForRepresentation(IFile file, int index) {
		addPage(index, new RepresentationWithAnnotations(getContainer(), file));
		setPageText(index, PAGE_REPRESENTATION);
	}

	/**
	 * Adds an editor page for the svg representation
	 * 
	 * @param file
	 *            The svg file
	 * @param index
	 *            The index for the editor
	 */
	private void doAddPageForRepresentationSvg(IFile file, int index) {
		addPage(index, new RepresentationWithAnnotations(getContainer(), file));
		setPageText(index, PAGE_REPRESENTATION);
	}

	/**
	 * Adds an editor page for the phrase
	 * 
	 * @param file
	 *            The phrase file
	 * @param index
	 *            The index for the editor
	 */
	private void doAddPageForPhrase(IFile file, int index) {
		try {
			addPage(index, new PhraseEditor(), new FileEditorInput(file));
			setPageText(index, PAGE_PHRASE);
		} catch (PartInitException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Adds an editor page for the denotation
	 * 
	 * @param file
	 *            The denotation file
	 * @param index
	 *            The index for the editor
	 */
	private void doAddPageForDenotation(IFile file, int index) {
		try {
			denotationEditor = new DenotationFileEditor();
			addPage(index, denotationEditor, new FileEditorInput(file));
			setPageText(index, PAGE_DENOTATION);
		} catch (PartInitException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Adds an editor page for the meaning
	 * 
	 * @param file
	 *            The meaning file
	 * @param index
	 *            The index for the editor
	 */
	private void doAddPageForMeaning(IFile file, int index) {
		try {
			addPage(index, new MeaningEditor(), new FileEditorInput(file));
			setPageText(index, PAGE_MEANING);
		} catch (PartInitException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Event when a resource for a part of this editor has been added
	 * 
	 * @param file
	 *            The new file
	 * @param extension
	 *            The file extension
	 */
	private void onResourceAdded(final IFile file, final String extension) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (Constants.FILE_REPRESENTATION.equals(extension)) {
					int index = 0;
					doAddPageForRepresentation(file, index);
				} else if (".svg".equalsIgnoreCase(extension)) {
					int index = 0;
					doAddPageForRepresentationSvg(file, index);
				} else if (Constants.FILE_PHRASE.equals(extension)) {
					for (int i = 0; i != getPageCount(); i++) {
						String name = getPageText(i);
						if (PAGE_DENOTATION.equals(name) || PAGE_MEANING.equals(name)) {
							doAddPageForPhrase(file, i);
							return;
						}
					}
					doAddPageForPhrase(file, getPageCount());
				} else if (Constants.FILE_DENOTATION.equals(extension)) {
					for (int i = 0; i != getPageCount(); i++) {
						String name = getPageText(i);
						if (PAGE_MEANING.equals(name)) {
							doAddPageForDenotation(file, i);
							return;
						}
					}
					doAddPageForDenotation(file, getPageCount());
				} else if (Constants.FILE_MEANING.equals(extension)) {
					doAddPageForMeaning(file, getPageCount());
				}
			}
		});
	}

	/**
	 * Event when a resource for a part of this editor has been removed
	 * 
	 * @param file
	 *            The new file
	 * @param extension
	 *            The file extension
	 */
	private void onResourceRemoved(final IFile file, final String extension) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (Constants.FILE_REPRESENTATION.equals(extension) || ".svg".equalsIgnoreCase(extension)) {
					for (int i = 0; i != getPageCount(); i++) {
						String name = getPageText(i);
						if (PAGE_REPRESENTATION.equals(name)) {
							removePage(i);
							i--;
						}
					}
				} else if (Constants.FILE_PHRASE.equals(extension)) {
					for (int i = 0; i != getPageCount(); i++) {
						String name = getPageText(i);
						if (PAGE_PHRASE.equals(name)) {
							removePage(i);
							break;
						}
					}
				} else if (Constants.FILE_DENOTATION.equals(extension)) {
					for (int i = 0; i != getPageCount(); i++) {
						String name = getPageText(i);
						if (PAGE_DENOTATION.equals(name)) {
							removePage(i);
							break;
						}
					}
				} else if (Constants.FILE_MEANING.equals(extension)) {
					for (int i = 0; i != getPageCount(); i++) {
						String name = getPageText(i);
						if (PAGE_MEANING.equals(name)) {
							removePage(i);
							break;
						}
					}
				}
			}
		});
	}

	/**
	 * Event when a resource for a part of this editor has been changed
	 * 
	 * @param file
	 *            The new file
	 * @param extension
	 *            The file extension
	 */
	private void onResourceChanged(IFile file, String extension) {

	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if (denotationEditor != null)
			denotationEditor.doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		if (denotationEditor != null)
			denotationEditor.doSaveAs();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return (denotationEditor != null);
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceListener);
		super.dispose();
	}
}
