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

package org.xowl.satellites.eclipse.editors;

import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.eclipse.ui.editors.text.TextEditor;

/**
 * The editor for the Functional OWL2 syntax
 *
 * @author Laurent Wouters
 */
public class FunctionalOWL2Editor extends TextEditor {
    /**
     * Initializes this editor
     */
    public FunctionalOWL2Editor() {
        super();
        setSourceViewerConfiguration(new HimeEditorConfiguration(new FunctionalOWL2Scanner()));
        setDocumentProvider(new FileDocumentProvider());
    }
}
