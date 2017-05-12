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

package org.xowl.satellites.eclipse.denotation.parsers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.xowl.infra.denotation.phrases.Parser;
import org.xowl.infra.denotation.phrases.Phrase;
import org.xowl.infra.denotation.phrases.PhraseVocabulary;
import org.xowl.infra.denotation.phrases.Sign;

/**
 * Implements a phrase parser for a GMF notation diagram
 * 
 * @author Laurent Wouters
 */
public class DiagramParser implements Parser<Diagram> {
	@Override
	public String getIdentifier() {
		return DiagramParser.class.getCanonicalName();
	}

	@Override
	public String getName() {
		return "xOWL Eclipse Satellite - GMF Notation Diagram parser";
	}

	@Override
	public PhraseVocabulary getVocabulary() {
		return PhraseVocabulary.REGISTER;
	}

	@Override
	public Phrase parse(Diagram diagram) {
		List<Sign> signs = new ArrayList<>();
		return new Phrase(diagram.getName(), diagram.getName(), signs);
	}
}
