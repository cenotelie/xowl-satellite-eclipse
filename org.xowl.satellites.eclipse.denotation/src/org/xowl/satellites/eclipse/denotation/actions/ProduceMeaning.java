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

import fr.cenotelie.hime.redist.ASTNode;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.xowl.infra.denotation.Denotation;
import org.xowl.infra.denotation.phrases.Phrase;
import org.xowl.infra.denotation.rules.DenotationRule;
import org.xowl.infra.denotation.rules.DenotationRuleLoader;
import org.xowl.infra.store.rdf.Quad;
import org.xowl.infra.store.writers.TriGSerializer;
import org.xowl.infra.utils.AutoReader;
import org.xowl.infra.utils.IOUtils;
import org.xowl.infra.utils.json.Json;
import org.xowl.infra.utils.logging.BufferedLogger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Collection;

/**
 * Produces the meaning of a captured denotation
 *
 * @author Laurent Wouters
 */
public class ProduceMeaning implements Runnable {
    /**
     * The file containing the phrase
     */
    private final IFile filePhrase;
    /**
     * The file containing the denotation of the phrase
     */
    private final IFile fileDenotation;
    /**
     * The target file for the produced meaning
     */
    private final IFile fileTarget;
    /**
     * The graph URI for the target
     */
    private final String graph;

    /**
     * Initializes this action
     *
     * @param filePhrase     The file containing the phrase
     * @param fileDenotation The file containing the denotation of the phrase
     * @param fileTaget      The target file for the produced meaning
     * @param graph          The graph URI for the target
     */
    public ProduceMeaning(IFile filePhrase, IFile fileDenotation, IFile fileTaget, String graph) {
        this.filePhrase = filePhrase;
        this.fileDenotation = fileDenotation;
        this.fileTarget = fileTaget;
        this.graph = graph;
    }

    @Override
    public void run() {
        try {
            doRun();
        } catch (Exception exception) {
            MessageDialog.openError(Display.getCurrent().getActiveShell(), "Produce Meaning", exception.getMessage());
            exception.printStackTrace();
        }
    }

    /**
     * Executes this action
     *
     * @throws Exception When an error occurs
     */
    private void doRun() throws Exception {
        // parses the phrase
        BufferedLogger logger = new BufferedLogger();
        Phrase phrase;
        try (InputStream stream = filePhrase.getContents()) {
            ASTNode phraseRoot = Json.parse(logger, new AutoReader(stream));
            if (!logger.getErrorMessages().isEmpty() || phraseRoot == null)
                throwCoreException(logger.getErrorsAsString());
            phrase = new Phrase(phraseRoot);
        }

        // parses the denotation
        Collection<DenotationRule> rules;
        try (InputStream stream = fileDenotation.getContents()) {
            DenotationRuleLoader loader = new DenotationRuleLoader();
            rules = loader.load(new AutoReader(stream), logger);
            if (!logger.getErrorMessages().isEmpty() || rules == null)
                throwCoreException(logger.getErrorsAsString());
        }

        // creates the denotation
        Denotation denotation = new Denotation(phrase, graph);
        for (DenotationRule rule : rules)
            denotation.addRule(rule);

        // serializes the quads
        Collection<Quad> quads = denotation.getSemantic();
        StringWriter writer = new StringWriter();
        TriGSerializer serializer = new TriGSerializer(writer);
        serializer.serialize(logger, quads.iterator());
        if (!logger.getErrorMessages().isEmpty())
            throwCoreException(logger.getErrorsAsString());

        // writes to the target
        if (fileTarget.exists())
            fileTarget.setContents(new ByteArrayInputStream(writer.toString().getBytes(IOUtils.CHARSET)), true, true,
                    null);
        else
            fileTarget.create(new ByteArrayInputStream(writer.toString().getBytes(IOUtils.CHARSET)), true, null);

        MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Produce Meaning",
                "Meaning has been produced as " + fileTarget.getName());
    }

    /**
     * Throws a core exception
     *
     * @param message The message's exception
     * @throws CoreException The exception
     */
    private void throwCoreException(String message) throws CoreException {
        IStatus status = new Status(IStatus.ERROR, "org.xowl.satellites.eclipse.denotation", IStatus.OK, message, null);
        throw new CoreException(status);
    }
}
