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

import fr.cenotelie.hime.redist.Token;
import fr.cenotelie.hime.redist.TokenRepository;
import org.eclipse.swt.custom.StyleRange;

/**
 * Implements a scanner for the Hime Grammar syntax
 *
 * @author Laurent Wouters
 */
public class HimeGrammarScanner extends HimePresentationRepairer {
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
     * Initializes this scanner
     */
    public HimeGrammarScanner() {
        super();
    }

    @Override
    protected TokenRepository doParse(String input) {
        HimeGrammarLexer lexer = new HimeGrammarLexer(input);
        lexer.setErrorHandler(this);
        // trigger the full lexing
        lexer.getNextToken();
        return lexer.getTokens();
    }

    @Override
    protected StyleRange doStyle(Token token) {
        switch (token.getSymbol().getID()) {
            case HimeGrammarLexer.ID.COMMENT_LINE:
            case HimeGrammarLexer.ID.COMMENT_BLOCK:
                return doCreateStyle(token, COMMENT_COLOR);
            case HimeGrammarLexer.ID.BLOCK_GRAMMAR:
            case HimeGrammarLexer.ID.BLOCK_OPTIONS:
            case HimeGrammarLexer.ID.BLOCK_TERMINALS:
            case HimeGrammarLexer.ID.BLOCK_RULES:
            case HimeGrammarLexer.ID.BLOCK_CONTEXT:
            case HimeGrammarLexer.ID.FRAGMENT:
                return doCreateStyle(token, KEYWORD_COLOR);
            case HimeGrammarLexer.ID.LITERAL_TEXT:
                return doCreateStyle(token, LITERAL_COLOR);
        }
        return null;
    }
}
