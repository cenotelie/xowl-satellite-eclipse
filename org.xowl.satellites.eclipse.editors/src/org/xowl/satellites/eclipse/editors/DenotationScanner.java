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
 * Implements a scanner for the denotation syntax
 *
 * @author Laurent Wouters
 */
public class DenotationScanner extends HimePresentationRepairer {
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
	 * Initializes this scanner
	 */
	public DenotationScanner() {
		super();
	}

	@Override
	protected TokenRepository doParse(String input) {
		DenotationLexer lexer = new DenotationLexer(input);
		lexer.setErrorHandler(this);
		// trigger the full lexing
		lexer.getNextToken();
		return lexer.getTokens();
	}

	@Override
	protected StyleRange doStyle(Token token) {
		switch (token.getSymbol().getID()) {
		case DenotationLexer.ID.COMMENT:
			return doCreateStyle(token, COMMENT_COLOR);
		case DenotationLexer.ID.BASE:
		case DenotationLexer.ID.PREFIX:
		case DenotationLexer.ID.RULE:
		case DenotationLexer.ID.IS:
		case DenotationLexer.ID.SIGN:
		case DenotationLexer.ID.SEME:
		case DenotationLexer.ID.AND:
		case DenotationLexer.ID.WITH:
		case DenotationLexer.ID.RELATION:
		case DenotationLexer.ID.BOUND:
		case DenotationLexer.ID.BIND:
		case DenotationLexer.ID.TO:
		case DenotationLexer.ID.A:
		case DenotationLexer.ID.ID:
		case DenotationLexer.ID.AS:
			return doCreateStyle(token, KEYWORD_COLOR);
		case DenotationLexer.ID.TRUE:
		case DenotationLexer.ID.FALSE:
		case DenotationLexer.ID.INTEGER:
		case DenotationLexer.ID.DECIMAL:
		case DenotationLexer.ID.DOUBLE:
		case DenotationLexer.ID.STRING:
			return doCreateStyle(token, LITERAL_COLOR);
		case DenotationLexer.ID.IRIREF:
		case DenotationLexer.ID.PNAME_NS:
		case DenotationLexer.ID.PNAME_LN:
			return doCreateStyle(token, IRI_COLOR);
		}
		return null;
	}
}
