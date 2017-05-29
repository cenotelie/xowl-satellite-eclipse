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
import fr.cenotelie.hime.redist.lexer.IContextProvider;
import fr.cenotelie.hime.redist.lexer.TokenKernel;
import fr.cenotelie.hime.redist.parsers.InitializationException;

/**
 * Implements a scanner for the xOWL syntax
 *
 * @author Laurent Wouters
 */
public class xOWLScanner extends HimePresentationRepairer {
	/**
	 * The filtering lexer that filters out the COMMENT for the parser
	 *
	 * @author Laurent Wouters
	 */
	private static class FilteringLexer extends xOWLLexer {
		/**
		 * Initializes this lexer
		 * 
		 * @param input
		 *            The lexer's input
		 */
		public FilteringLexer(String input) {
			super(input);
		}

		@Override
		public TokenKernel getNextToken(IContextProvider contexts) {
			TokenKernel kernel = super.getNextToken(contexts);
			while (kernel.getTerminalID() == xRDFLexer.ID.COMMENT)
				kernel = super.getNextToken(contexts);
			return kernel;
		}
	}

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
	public xOWLScanner() {
		super();
	}

	@Override
	protected TokenRepository doParse(String input) {
		xOWLLexer lexer = new FilteringLexer(input);
		xOWLParser parser;
		try {
			parser = new xOWLParser(lexer);
		} catch (InitializationException exception) {
			exception.printStackTrace();
			return null;
		}
		// parses the input
		parser.parse();
		return lexer.getTokens();
	}

	@Override
	protected StyleRange doStyle(Token token) {
		switch (token.getSymbol().getID()) {
		case xOWLLexer.ID.COMMENT:
			return doCreateStyle(token, COMMENT_COLOR);
		case xOWLLexer.ID.RULE_BLOCK_RULE:
		case xOWLLexer.ID.RULE_BLOCK_ANTECEDENTS:
		case xOWLLexer.ID.RULE_BLOCK_CONSEQUENTS:
		case xOWLLexer.ID.RULE_BLOCK_GUARD:
		case xOWLLexer.ID.RULE_ELEMENT_META:
		case xOWLLexer.ID.RULE_ELEMENT_NOT:
		case xOWLLexer.ID.XOWL_OPAQUE_EXP:
		case xOWLLexer.ID.CLJ_KEYWORD:
			return doCreateStyle(token, KEYWORD_COLOR);
		case xOWLLexer.ID.OWL2_INTEGER:
		case xOWLLexer.ID.OWL2_STRING:
		case xOWLLexer.ID.LITERAL_STRING:
		case xOWLLexer.ID.LITERAL_CHAR:
		case xOWLLexer.ID.LITERAL_NIL:
		case xOWLLexer.ID.LITERAL_TRUE:
		case xOWLLexer.ID.LITERAL_FALSE:
		case xOWLLexer.ID.LITERAL_INTEGER:
		case xOWLLexer.ID.LITERAL_RATIO:
		case xOWLLexer.ID.LITERAL_FLOAT:
		case xOWLLexer.ID.LITERAL_ARGUMENT:
			return doCreateStyle(token, LITERAL_COLOR);
		case xOWLLexer.ID.IRIREF:
			return doCreateStyle(token, IRI_COLOR);
		case xOWLLexer.ID.BLANK_NODE_LABEL:
		case xOWLLexer.ID.PNAME_NS:
		case xOWLLexer.ID.PNAME_LN:
			return doCreateStyle(token, BLANK_COLOR);
		case xOWLLexer.ID.LANGTAG:
			return doCreateStyle(token, LANGTAG_COLOR);
		case xOWLLexer.ID.XOWL_QVAR:
			return doCreateStyle(token, VARIABLE_COLOR);
		}
		if (token.getSymbol().getID() >= 0x00A4)
			return doCreateStyle(token, KEYWORD_COLOR);
		return null;
	}
}
