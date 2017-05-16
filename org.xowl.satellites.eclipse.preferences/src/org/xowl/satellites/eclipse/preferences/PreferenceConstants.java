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

/**
 * The constants for the preferences related to xOWL on the Eclipse
 * platform
 *
 * @author Laurent Wouters
 */
public interface PreferenceConstants {
	/**
	 * The part to the node in the secure storage
	 */
	String XOWL_SECURE_NODE = "/org.xowl.satellites.eclipse.preferences";
	/**
	 * The key for the API endpoint
	 */
	String XOWL_EP_KEY = "org.xowl.satellites.eclipse.preferences.endpoint";
	/**
	 * The user login to use on the platform
	 */
	String XOWL_LOGIN_KEY = "org.xowl.satellites.eclipse.preferences.login";
	/**
	 * The user password to use on the platform
	 */
	String XOWL_PASSWORD_KEY = "org.xowl.satellites.eclipse.preferences.password";
	/**
	 * The initial value for the API endpoint
	 */
	String XOWL_EP_VALUE = "https://localhost:8443/api/";
}
