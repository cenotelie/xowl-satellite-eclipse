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
 * Implements a scanner for the SPARQL syntax
 *
 * @author Laurent Wouters
 */
public class SPARQLScanner extends HimePresentationRepairer {
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
	 * The color of variables
	 */
	private static final int VARIABLE_COLOR = 0x00FF8C00;

	/**
	 * Initializes this scanner
	 */
	public SPARQLScanner() {
		super();
	}

	@Override
	protected TokenRepository doParse(String input) {
		SPARQLLexer lexer = new SPARQLLexer(input);
		lexer.setErrorHandler(this);
		// trigger the full lexing
		lexer.getNextToken();
		return lexer.getTokens();
	}

	@Override
	protected StyleRange doStyle(Token token) {
		switch (token.getSymbol().getID()) {
		case SPARQLLexer.ID.COMMENT:
			return doCreateStyle(token, COMMENT_COLOR);
		case SPARQLLexer.ID.A:
		case SPARQLLexer.ID.PREFIX:
		case SPARQLLexer.ID.BASE:
		case SPARQLLexer.ID.SELECT:
		case SPARQLLexer.ID.DISTINCT:
		case SPARQLLexer.ID.REDUCED:
		case SPARQLLexer.ID.AS:
		case SPARQLLexer.ID.CONSTRUCT:
		case SPARQLLexer.ID.WHERE:
		case SPARQLLexer.ID.DESCRIBE:
		case SPARQLLexer.ID.ASK:
		case SPARQLLexer.ID.FROM:
		case SPARQLLexer.ID.NAMED:
		case SPARQLLexer.ID.GROUP:
		case SPARQLLexer.ID.BY:
		case SPARQLLexer.ID.HAVING:
		case SPARQLLexer.ID.ORDER:
		case SPARQLLexer.ID.ASC:
		case SPARQLLexer.ID.DESC:
		case SPARQLLexer.ID.LIMIT:
		case SPARQLLexer.ID.OFFSET:
		case SPARQLLexer.ID.VALUES:
		case SPARQLLexer.ID.SILENT:
		case SPARQLLexer.ID.TO:
		case SPARQLLexer.ID.INTO:
		case SPARQLLexer.ID.LOAD:
		case SPARQLLexer.ID.CLEAR:
		case SPARQLLexer.ID.DROP:
		case SPARQLLexer.ID.CREATE:
		case SPARQLLexer.ID.ADD:
		case SPARQLLexer.ID.MOVE:
		case SPARQLLexer.ID.COPY:
		case SPARQLLexer.ID.INSERT:
		case SPARQLLexer.ID.DELETE:
		case SPARQLLexer.ID.WITH:
		case SPARQLLexer.ID.DATA:
		case SPARQLLexer.ID.USING:
		case SPARQLLexer.ID.DEFAULT:
		case SPARQLLexer.ID.GRAPH:
		case SPARQLLexer.ID.ALL:
		case SPARQLLexer.ID.OPTIONAL:
		case SPARQLLexer.ID.SERVICE:
		case SPARQLLexer.ID.BIND:
		case SPARQLLexer.ID.UNION:
		case SPARQLLexer.ID.MINUS:
		case SPARQLLexer.ID.FILTER:
		case SPARQLLexer.ID.UNDEF:
		case SPARQLLexer.ID.IN:
		case SPARQLLexer.ID.NOT:
		case SPARQLLexer.ID.EXISTS:
		case SPARQLLexer.ID.SEPARATOR:
			return doCreateStyle(token, KEYWORD_COLOR);
		case SPARQLLexer.ID.INTEGER:
		case SPARQLLexer.ID.DECIMAL:
		case SPARQLLexer.ID.DOUBLE:
		case SPARQLLexer.ID.STRING_LITERAL_QUOTE:
		case SPARQLLexer.ID.STRING_LITERAL_SINGLE_QUOTE:
		case SPARQLLexer.ID.STRING_LITERAL_LONG_SINGLE_QUOTE:
		case SPARQLLexer.ID.STRING_LITERAL_LONG_QUOTE:
		case SPARQLLexer.ID.TRUE:
		case SPARQLLexer.ID.FALSE:
			return doCreateStyle(token, LITERAL_COLOR);
		case SPARQLLexer.ID.IRIREF:
			return doCreateStyle(token, IRI_COLOR);
		case SPARQLLexer.ID.BLANK_NODE_LABEL:
		case SPARQLLexer.ID.PNAME_NS:
		case SPARQLLexer.ID.PNAME_LN:
			return doCreateStyle(token, BLANK_COLOR);
		case SPARQLLexer.ID.LANGTAG:
			return doCreateStyle(token, LANGTAG_COLOR);
		case SPARQLLexer.ID.VARIABLE:
			return doCreateStyle(token, VARIABLE_COLOR);
		}
		return null;
	}
}
