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
import fr.cenotelie.hime.redist.lexer.IContextProvider;
import fr.cenotelie.hime.redist.lexer.TokenKernel;
import org.eclipse.swt.custom.StyleRange;

/**
 * Implements a scanner for the xRDF syntax
 *
 * @author Laurent Wouters
 */
public class xRDFScanner extends HimePresentationRepairer {
    /**
     * The filtering lexer that filters out the COMMENT for the parser
     *
     * @author Laurent Wouters
     */
    private static class FilteringLexer extends xRDFLexer {
        /**
         * Initializes this lexer
         *
         * @param input The lexer's input
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
    public xRDFScanner() {
        super();
    }

    @Override
    protected TokenRepository doParse(String input) {
        xRDFLexer lexer = new FilteringLexer(input);
        xRDFParser parser = new xRDFParser(lexer);
        // parses the input
        parser.parse();
        return lexer.getTokens();
    }

    @Override
    protected StyleRange doStyle(Token token) {
        switch (token.getSymbol().getID()) {
            case xRDFLexer.ID.COMMENT:
                return doCreateStyle(token, COMMENT_COLOR);
            case xRDFLexer.ID.A:
            case xRDFLexer.ID.PREFIX:
            case xRDFLexer.ID.BASE:
            case xRDFLexer.ID.SELECT:
            case xRDFLexer.ID.DISTINCT:
            case xRDFLexer.ID.REDUCED:
            case xRDFLexer.ID.AS:
            case xRDFLexer.ID.CONSTRUCT:
            case xRDFLexer.ID.WHERE:
            case xRDFLexer.ID.DESCRIBE:
            case xRDFLexer.ID.ASK:
            case xRDFLexer.ID.FROM:
            case xRDFLexer.ID.NAMED:
            case xRDFLexer.ID.GROUP:
            case xRDFLexer.ID.BY:
            case xRDFLexer.ID.HAVING:
            case xRDFLexer.ID.ORDER:
            case xRDFLexer.ID.ASC:
            case xRDFLexer.ID.DESC:
            case xRDFLexer.ID.LIMIT:
            case xRDFLexer.ID.OFFSET:
            case xRDFLexer.ID.VALUES:
            case xRDFLexer.ID.SILENT:
            case xRDFLexer.ID.TO:
            case xRDFLexer.ID.INTO:
            case xRDFLexer.ID.LOAD:
            case xRDFLexer.ID.CLEAR:
            case xRDFLexer.ID.DROP:
            case xRDFLexer.ID.CREATE:
            case xRDFLexer.ID.ADD:
            case xRDFLexer.ID.MOVE:
            case xRDFLexer.ID.COPY:
            case xRDFLexer.ID.INSERT:
            case xRDFLexer.ID.DELETE:
            case xRDFLexer.ID.WITH:
            case xRDFLexer.ID.DATA:
            case xRDFLexer.ID.USING:
            case xRDFLexer.ID.DEFAULT:
            case xRDFLexer.ID.GRAPH:
            case xRDFLexer.ID.ALL:
            case xRDFLexer.ID.OPTIONAL:
            case xRDFLexer.ID.SERVICE:
            case xRDFLexer.ID.BIND:
            case xRDFLexer.ID.UNION:
            case xRDFLexer.ID.MINUS:
            case xRDFLexer.ID.FILTER:
            case xRDFLexer.ID.UNDEF:
            case xRDFLexer.ID.IN:
            case xRDFLexer.ID.NOT:
            case xRDFLexer.ID.EXISTS:
            case xRDFLexer.ID.SEPARATOR:
            case xRDFLexer.ID.RULE:
            case xRDFLexer.ID.META:
            case xRDFLexer.ID.CLJ_KEYWORD:
                return doCreateStyle(token, KEYWORD_COLOR);
            case xRDFLexer.ID.INTEGER:
            case xRDFLexer.ID.DECIMAL:
            case xRDFLexer.ID.DOUBLE:
            case xRDFLexer.ID.STRING_LITERAL_QUOTE:
            case xRDFLexer.ID.STRING_LITERAL_SINGLE_QUOTE:
            case xRDFLexer.ID.STRING_LITERAL_LONG_SINGLE_QUOTE:
            case xRDFLexer.ID.STRING_LITERAL_LONG_QUOTE:
            case xRDFLexer.ID.TRUE:
            case xRDFLexer.ID.FALSE:
            case xRDFLexer.ID.LITERAL_STRING:
            case xRDFLexer.ID.LITERAL_CHAR:
            case xRDFLexer.ID.LITERAL_NIL:
            case xRDFLexer.ID.LITERAL_TRUE:
            case xRDFLexer.ID.LITERAL_FALSE:
            case xRDFLexer.ID.LITERAL_INTEGER:
            case xRDFLexer.ID.LITERAL_RATIO:
            case xRDFLexer.ID.LITERAL_FLOAT:
            case xRDFLexer.ID.LITERAL_ARGUMENT:
                return doCreateStyle(token, LITERAL_COLOR);
            case xRDFLexer.ID.IRIREF:
                return doCreateStyle(token, IRI_COLOR);
            case xRDFLexer.ID.BLANK_NODE_LABEL:
            case xRDFLexer.ID.PNAME_NS:
            case xRDFLexer.ID.PNAME_LN:
                return doCreateStyle(token, BLANK_COLOR);
            case xRDFLexer.ID.LANGTAG:
                return doCreateStyle(token, LANGTAG_COLOR);
            case xRDFLexer.ID.VARIABLE:
                return doCreateStyle(token, VARIABLE_COLOR);
        }
        return null;
    }
}
