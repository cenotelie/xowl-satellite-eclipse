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

import fr.cenotelie.commons.utils.json.JsonLexer;
import fr.cenotelie.hime.redist.Token;
import fr.cenotelie.hime.redist.TokenRepository;
import org.eclipse.swt.custom.StyleRange;
import org.xowl.satellites.eclipse.editors.HimePresentationRepairer;

/**
 * Implements a scanner for the syntax of a phrase file
 *
 * @author Laurent Wouters
 */
public class PhraseScanner extends HimePresentationRepairer {
    /**
     * The color of comment
     */
    private static final int COMMENT_COLOR = 0x00006400;
    /**
     * The color of literals
     */
    private static final int LITERAL_COLOR = 0x008A2BE2;
    /**
     * The color of literal string
     */
    private static final int LITERAL_STRING = 0x000000FF;

    /**
     * Initializes this scanner
     */
    public PhraseScanner() {
        super();
    }

    @Override
    protected TokenRepository doParse(String input) {
        JsonLexer lexer = new JsonLexer(input);
        lexer.setErrorHandler(this);
        // trigger the full lexing
        lexer.getNextToken();
        return lexer.getTokens();
    }

    @Override
    protected StyleRange doStyle(Token token) {
        switch (token.getSymbol().getID()) {
            case JsonLexer.ID.COMMENT:
                return doCreateStyle(token, COMMENT_COLOR);
            case JsonLexer.ID.LITERAL_NULL:
            case JsonLexer.ID.LITERAL_TRUE:
            case JsonLexer.ID.LITERAL_FALSE:
            case JsonLexer.ID.LITERAL_INTEGER:
            case JsonLexer.ID.LITERAL_DECIMAL:
            case JsonLexer.ID.LITERAL_DOUBLE:
                return doCreateStyle(token, LITERAL_COLOR);
            case JsonLexer.ID.LITERAL_STRING:
                return doCreateStyle(token, LITERAL_STRING);
        }
        return null;
    }
}