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

import org.eclipse.jface.preference.IPreferenceStore;

/**
 * API for the access to the xOWL preferences
 * 
 * @author Laurent Wouters
 */
public class Preferences {
	/**
	 * The backing preference store
	 */
	private static IPreferenceStore store;

	/**
	 * Gets the preference store
	 * 
	 * @return The preference store
	 */
	public static synchronized IPreferenceStore getStore() {
		if (store == null)
			store = new SecurePreferencesStore(PreferenceConstants.XOWL_SECURE_NODE);
		return store;
	}

	/**
	 * Gets the configured API endpoint
	 * 
	 * @return The configured API endpoint
	 */
	public static String getApiEndpoint() {
		return getStore().getString(PreferenceConstants.XOWL_EP_KEY);
	}

	/**
	 * Gets the configured login
	 * 
	 * @return The configured login
	 */
	public static String getLogin() {
		return getStore().getString(PreferenceConstants.XOWL_LOGIN_KEY);
	}

	/**
	 * Gets the configured password
	 * 
	 * @return The configured password
	 */
	public static String getPassword() {
		return getStore().getString(PreferenceConstants.XOWL_PASSWORD_KEY);
	}
}
