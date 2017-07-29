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
import org.xowl.infra.utils.AutoReader;
import org.xowl.infra.utils.IOUtils;
import org.xowl.infra.utils.json.Json;
import org.xowl.infra.utils.json.JsonLexer;
import org.xowl.infra.utils.json.JsonParser;
import org.xowl.infra.utils.logging.BufferedLogger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Formats a JSON file
 *
 * @author Laurent Wouters
 */
public class FormatJsonFile implements Runnable {
    /**
     * The spacing to use for the formatting
     */
    private static final String SPACE = "    ";

    /**
     * The JSON file
     */
    private final IFile file;

    /**
     * Initializes this action
     *
     * @param file The JSON file
     */
    public FormatJsonFile(IFile file) {
        this.file = file;
    }

    @Override
    public void run() {
        try {
            doRun();
        } catch (Exception exception) {
            MessageDialog.openError(Display.getCurrent().getActiveShell(), "Format Json", exception.getMessage());
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
        ASTNode root;
        try (InputStream stream = file.getContents()) {
            root = Json.parse(logger, new AutoReader(stream));
            if (!logger.getErrorMessages().isEmpty() || root == null)
                throwCoreException(logger.getErrorsAsString());
        }

        StringBuffer buffer = new StringBuffer();
        print(buffer, "", root);
        file.setContents(new ByteArrayInputStream(buffer.toString().getBytes(IOUtils.CHARSET)), true, true, null);
    }

    /**
     * Prints the specified Json node
     *
     * @param buffer The string buffer
     * @param space  The current space
     * @param node   The node to serialize
     */
    private void print(StringBuffer buffer, String space, ASTNode node) {
        switch (node.getSymbol().getID()) {
            case JsonLexer.ID.LITERAL_INTEGER:
            case JsonLexer.ID.LITERAL_DECIMAL:
            case JsonLexer.ID.LITERAL_DOUBLE:
            case JsonLexer.ID.LITERAL_STRING:
            case JsonLexer.ID.LITERAL_NULL:
            case JsonLexer.ID.LITERAL_TRUE:
            case JsonLexer.ID.LITERAL_FALSE:
                buffer.append(node.getValue());
                break;
            case JsonParser.ID.array: {
                buffer.append("[");
                buffer.append(IOUtils.LINE_SEPARATOR);
                for (int i = 0; i != node.getChildren().size(); i++) {
                    if (i != 0) {
                        buffer.append(",");
                        buffer.append(IOUtils.LINE_SEPARATOR);
                    }
                    buffer.append(space);
                    buffer.append(SPACE);
                    print(buffer, space + SPACE, node.getChildren().get(i));
                }
                buffer.append(IOUtils.LINE_SEPARATOR);
                buffer.append(space);
                buffer.append("]");
                break;
            }
            case JsonParser.ID.object: {
                buffer.append("{");
                buffer.append(IOUtils.LINE_SEPARATOR);
                for (int i = 0; i != node.getChildren().size(); i++) {
                    ASTNode member = node.getChildren().get(i);
                    if (i != 0) {
                        buffer.append(",");
                        buffer.append(IOUtils.LINE_SEPARATOR);
                    }
                    buffer.append(space);
                    buffer.append(SPACE);
                    print(buffer, space + SPACE, member.getChildren().get(0));
                    buffer.append(": ");
                    print(buffer, space + SPACE, member.getChildren().get(1));
                }
                buffer.append(IOUtils.LINE_SEPARATOR);
                buffer.append(space);
                buffer.append("}");
                break;
            }
        }
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
