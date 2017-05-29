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

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Implements a property tester for this plugin
 * 
 * @author Laurent Wouters
 */
public class DenotationPropertyTester extends PropertyTester {
	/**
	 * The IsPhrase property
	 */
	public static final String IS_PHRASE = "IsPhrase";
	/**
	 * The IsPhraseOrDenotation property
	 */
	public static final String IS_PHRASE_OR_DENOTATION = "IsPhraseOrDenotation";
	/**
	 * The IsMeaning property
	 */
	public static final String IS_MEANING = "IsMeaning";
	/**
	 * The IsGmfNotationElement property
	 */
	public static final String IS_GMF_NOTATION_ELEMENT = "IsGmfNotationElement";

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (IS_PHRASE.equals(property) && receiver instanceof IStructuredSelection) {
			if (((IStructuredSelection) receiver).size() != 1)
				return false;
			Object selected = ((IStructuredSelection) receiver).getFirstElement();
			if (selected == null)
				return false;
			if (!(selected instanceof IFile))
				return false;
			IFile file = (IFile) selected;
			return file.getName().endsWith(Constants.FILE_PHRASE);
		} else if (IS_PHRASE_OR_DENOTATION.equals(property) && receiver instanceof IStructuredSelection) {
			if (((IStructuredSelection) receiver).size() != 1)
				return false;
			Object selected = ((IStructuredSelection) receiver).getFirstElement();
			if (selected == null)
				return false;
			if (!(selected instanceof IFile))
				return false;
			IFile file = (IFile) selected;
			return (file.getName().endsWith(Constants.FILE_PHRASE)
					|| file.getName().endsWith(Constants.FILE_DENOTATION));
		} else if (IS_MEANING.equals(property) && receiver instanceof IStructuredSelection) {
			if (((IStructuredSelection) receiver).size() != 1)
				return false;
			Object selected = ((IStructuredSelection) receiver).getFirstElement();
			if (selected == null)
				return false;
			if (!(selected instanceof IFile))
				return false;
			IFile file = (IFile) selected;
			return file.getName().endsWith(Constants.FILE_MEANING);
		} else if (IS_GMF_NOTATION_ELEMENT.equals(property) && receiver instanceof IStructuredSelection) {
			if (((IStructuredSelection) receiver).size() != 1)
				return false;
			Object selected = ((IStructuredSelection) receiver).getFirstElement();
			if (selected == null)
				return false;
			if (selected instanceof Diagram)
				return true;
			if (selected instanceof EditPart) {
				return ((EditPart) selected).getModel() instanceof Diagram;
			}
		}
		return false;
	}
}
