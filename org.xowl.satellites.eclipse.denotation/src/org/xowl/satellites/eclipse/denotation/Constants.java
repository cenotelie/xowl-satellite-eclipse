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

import org.xowl.infra.store.Repository;

/**
 * Defines some constants for the denotation capture
 * 
 * @author Laurent Wouters
 */
public interface Constants {
	/**
	 * The file extension for a representation
	 */
	String FILE_REPRESENTATION = ".representation";
	/**
	 * The file extension for a phrase
	 */
	String FILE_PHRASE = ".phrase";
	/**
	 * The file extension for a denotation
	 */
	String FILE_DENOTATION = ".denotation";
	/**
	 * The file extension for a meaning
	 */
	String FILE_MEANING = Repository.SYNTAX_TRIG_EXTENSION;
	/**
	 * The syntax for a meaning file
	 */
	String MEANING_SYNTAX = Repository.SYNTAX_TRIG;
}
