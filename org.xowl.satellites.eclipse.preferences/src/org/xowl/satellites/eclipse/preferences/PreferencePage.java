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

package org.xowl.satellites.eclipse.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The preference page for xOWL
 *
 * @author Laurent Wouters
 */
public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
    /**
     * Initializes this preference page
     */
    public PreferencePage() {
        super(GRID);
        setPreferenceStore(new SecurePreferencesStore(PreferenceConstants.XOWL_SECURE_NODE));
    }

    /**
     * Initializes this preference page
     *
     * @param title The title
     * @param image The image
     * @param style The style
     */
    public PreferencePage(String title, ImageDescriptor image, int style) {
        super(title, image, style);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    @Override
    public void init(IWorkbench workbench) {
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors
     * ()
     */
    @Override
    protected void createFieldEditors() {
        Composite parent = getFieldEditorParent();
        addField(new StringFieldEditor(PreferenceConstants.XOWL_EP_KEY, "xOWL API endpoint", parent));
        addField(new StringFieldEditor(PreferenceConstants.XOWL_LOGIN_KEY, "xOWL User login", parent));
        StringFieldEditor fieldPassword = new StringFieldEditor(PreferenceConstants.XOWL_PASSWORD_KEY, "xOWL User password", parent);
        addField(fieldPassword);
        fieldPassword.getTextControl(parent).setEchoChar('*');
    }
}
