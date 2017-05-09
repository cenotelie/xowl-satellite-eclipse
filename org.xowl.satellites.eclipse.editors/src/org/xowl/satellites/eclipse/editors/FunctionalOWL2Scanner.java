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

import org.eclipse.swt.custom.StyleRange;

import fr.cenotelie.hime.redist.Token;
import fr.cenotelie.hime.redist.TokenRepository;

/**
 * Implements a scanner for the Functional OWL2 syntax
 *
 * @author Laurent Wouters
 */
public class FunctionalOWL2Scanner extends HimePresentationRepairer {
	/**
	 * The color of keywords
	 */
	private static final int KEYWORD_COLOR = 0x000000FF;
	/**
	 * The color of comment
	 */
	private static final int COMMENT_COLOR = 0x00006400;
	/**
	 * The color of literals
	 */
	private static final int LITERAL_COLOR = 0x008A2BE2;
	/**
	 * The color of IRIs
	 */
	private static final int IRI_COLOR = 0x00DC143C;
	/**
	 * The color of blank nodes
	 */
	private static final int BLANK_COLOR = 0x002F4F4F;
	/**
	 * The color of lang tags
	 */
	private static final int LANGTAG_COLOR = 0x002F4F4F;

	/**
	 * Initializes this scanner
	 */
	public FunctionalOWL2Scanner() {
		super();
	}

	@Override
	protected TokenRepository doParse(String input) {
		FunctionalOWL2Lexer lexer = new FunctionalOWL2Lexer(input);
		lexer.setErrorHandler(this);
		// trigger the full lexing
		lexer.getNextToken();
		return lexer.getTokens();
	}

	@Override
	protected StyleRange doStyle(Token token) {
		switch (token.getSymbol().getID()) {
		case FunctionalOWL2Lexer.ID.COMMENT:
			return doCreateStyle(token, COMMENT_COLOR);
		case FunctionalOWL2Lexer.ID.PREFIX:
		case FunctionalOWL2Lexer.ID.ONTOLOGY:
		case FunctionalOWL2Lexer.ID.IMPORT:
			return doCreateStyle(token, KEYWORD_COLOR);
		case FunctionalOWL2Lexer.ID.INTEGER:
		case FunctionalOWL2Lexer.ID.STRING:
			return doCreateStyle(token, LITERAL_COLOR);
		case FunctionalOWL2Lexer.ID.IRIREF:
			return doCreateStyle(token, IRI_COLOR);
		case FunctionalOWL2Lexer.ID.BLANK_NODE_LABEL:
		case FunctionalOWL2Lexer.ID.PNAME_NS:
		case FunctionalOWL2Lexer.ID.PNAME_LN:
			return doCreateStyle(token, BLANK_COLOR);
		case FunctionalOWL2Lexer.ID.LANGTAG:
			return doCreateStyle(token, LANGTAG_COLOR);
		}
		if (token.getSymbol().getID() >= 0x00A4)
			return doCreateStyle(token, KEYWORD_COLOR);
		return null;
	}
}
