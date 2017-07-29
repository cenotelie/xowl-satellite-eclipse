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

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

/**
 * An editor configuration that uses Hime for the presentation
 *
 * @author Laurent Wouters
 */
public class HimeEditorConfiguration extends SourceViewerConfiguration {
    /**
     * The presentation repairer to use
     */
    private final HimePresentationRepairer repairer;

    /**
     * Initializes this configuration
     *
     * @param repairer The repairer to use
     */
    public HimeEditorConfiguration(HimePresentationRepairer repairer) {
        this.repairer = repairer;
    }

    @Override
    public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
        PresentationReconciler reconciler = new PresentationReconciler();
        reconciler.setDamager(new HimePresentationDamager(), IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(repairer, IDocument.DEFAULT_CONTENT_TYPE);
        return reconciler;
    }
}
