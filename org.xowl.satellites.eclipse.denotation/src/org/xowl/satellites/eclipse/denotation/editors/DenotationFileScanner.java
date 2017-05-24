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

package org.xowl.satellites.eclipse.denotation.editors;

import org.eclipse.swt.custom.StyleRange;
import org.xowl.satellites.eclipse.editors.HimePresentationRepairer;

import fr.cenotelie.hime.redist.Token;
import fr.cenotelie.hime.redist.TokenRepository;

/**
 * Implements a scanner for the denotation syntax
 *
 * @author Laurent Wouters
 */
public class DenotationFileScanner extends HimePresentationRepairer {
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
	public DenotationFileScanner() {
		super();
	}

	@Override
	protected TokenRepository doParse(String input) {
		DenotationFileLexer lexer = new DenotationFileLexer(input);
		lexer.setErrorHandler(this);
		// trigger the full lexing
		lexer.getNextToken();
		return lexer.getTokens();
	}

	@Override
	protected StyleRange doStyle(Token token) {
		switch (token.getSymbol().getID()) {
		case DenotationFileLexer.ID.COMMENT:
			return doCreateStyle(token, COMMENT_COLOR);
		case DenotationFileLexer.ID.BASE:
		case DenotationFileLexer.ID.PREFIX:
		case DenotationFileLexer.ID.RULE:
		case DenotationFileLexer.ID.IS:
		case DenotationFileLexer.ID.SIGN:
		case DenotationFileLexer.ID.SEME:
		case DenotationFileLexer.ID.AND:
		case DenotationFileLexer.ID.WITH:
		case DenotationFileLexer.ID.RELATION:
		case DenotationFileLexer.ID.BOUND:
		case DenotationFileLexer.ID.BIND:
		case DenotationFileLexer.ID.TO:
		case DenotationFileLexer.ID.A:
		case DenotationFileLexer.ID.ID:
		case DenotationFileLexer.ID.AS:
			return doCreateStyle(token, KEYWORD_COLOR);
		case DenotationFileLexer.ID.TRUE:
		case DenotationFileLexer.ID.FALSE:
		case DenotationFileLexer.ID.INTEGER:
		case DenotationFileLexer.ID.DECIMAL:
		case DenotationFileLexer.ID.DOUBLE:
		case DenotationFileLexer.ID.STRING:
			return doCreateStyle(token, LITERAL_COLOR);
		case DenotationFileLexer.ID.IRIREF:
		case DenotationFileLexer.ID.PNAME_NS:
		case DenotationFileLexer.ID.PNAME_LN:
			return doCreateStyle(token, IRI_COLOR);
		}
		return null;
	}
}
