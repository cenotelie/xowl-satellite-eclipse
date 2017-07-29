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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.xowl.infra.utils.IOUtils;
import org.xowl.infra.utils.api.Reply;
import org.xowl.infra.utils.http.HttpConstants;
import org.xowl.infra.utils.http.URIUtils;
import org.xowl.platform.kernel.remote.PlatformApiDeserializerForOSGi;
import org.xowl.platform.kernel.remote.RemotePlatformAccess;
import org.xowl.satellites.eclipse.denotation.Constants;
import org.xowl.satellites.eclipse.preferences.Preferences;

import java.io.InputStream;

/**
 * Shares the a produced meaning
 *
 * @author Laurent Wouters
 */
public class ShareMeaning implements Runnable {
    /**
     * The file containing the meaning to share
     */
    private final IFile fileMeaning;
    /**
     * The name of the artifact
     */
    private final String artifactName;
    /**
     * The base (family) IRI of the artifact
     */
    private final String artifactBase;
    /**
     * The version of the artifact
     */
    private final String artifactVersion;
    /**
     * The archetype of the artifact
     */
    private final String artifactArchetype;

    /**
     * Initializes this action
     *
     * @param fileMeaning       The file containing the meaning to share
     * @param artifactName      The name of the artifact
     * @param artifactBase      The base (family) IRI of the artifact
     * @param artifactVersion   The version of the artifact
     * @param artifactArchetype The archetype of the artifact
     */
    public ShareMeaning(IFile fileMeaning, String artifactName, String artifactBase, String artifactVersion,
                        String artifactArchetype) {
        this.fileMeaning = fileMeaning;
        this.artifactName = artifactName;
        this.artifactBase = artifactBase;
        this.artifactVersion = artifactVersion;
        this.artifactArchetype = artifactArchetype;
    }

    @Override
    public void run() {
        try {
            doRun();
        } catch (Exception exception) {
            MessageDialog.openError(Display.getCurrent().getActiveShell(), "Share Meaning", exception.getMessage());
            exception.printStackTrace();
        }
    }

    /**
     * Executes this action
     *
     * @throws Exception When an error occurs
     */
    private void doRun() throws Exception {
        String endpoint = Preferences.getApiEndpoint();
        String login = Preferences.getLogin();
        String password = Preferences.getPassword();
        if (endpoint == null || endpoint.isEmpty())
            throwCoreException("The API endpoint is not configured");
        if (login == null || login.isEmpty())
            throwCoreException("The login is not configured");
        if (password == null || password.isEmpty())
            throwCoreException("The password is not configured");

        byte[] content;
        try (InputStream stream = fileMeaning.getContents()) {
            content = IOUtils.load(stream);
        }

        try (RemotePlatformAccess connection = new RemotePlatformAccess(endpoint, new PlatformApiDeserializerForOSGi())) {
            Reply reply = connection.login(login, password);
            if (!reply.isSuccess())
                throwCoreException(reply.getMessage());

            reply = connection.doRequest(
                    "/connectors/generics/sw" + "?name=" + URIUtils.encodeComponent(artifactName) + "&base="
                            + URIUtils.encodeComponent(artifactBase) + "&version="
                            + URIUtils.encodeComponent(artifactVersion) + "&archetype="
                            + URIUtils.encodeComponent(artifactArchetype),
                    "POST", content, Constants.MEANING_SYNTAX, false, HttpConstants.MIME_JSON);
            if (!reply.isSuccess())
                throwCoreException(reply.getMessage());
        }

        MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Share Meaning",
                "The meaning has been shared as an artifact.");
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