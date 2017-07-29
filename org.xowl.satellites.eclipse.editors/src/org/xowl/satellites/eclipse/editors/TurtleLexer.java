/*
 * WARNING: this file has been generated by
 * Hime Parser Generator 2.0.5.0
 */

package org.xowl.satellites.eclipse.editors;

import fr.cenotelie.hime.redist.Symbol;
import fr.cenotelie.hime.redist.lexer.Automaton;
import fr.cenotelie.hime.redist.lexer.ContextFreeLexer;

import java.io.InputStreamReader;

/**
 * Represents a lexer
 */
class TurtleLexer extends ContextFreeLexer {
    /**
     * The automaton for this lexer
     */
    private static final Automaton commonAutomaton = Automaton.find(TurtleLexer.class, "TurtleLexer.bin");

    /**
     * Contains the constant IDs for the terminals for this lexer
     */
    public static class ID {
        /**
         * The unique identifier for terminal A
         */
        public static final int A = 0x001E;
        /**
         * The unique identifier for terminal BLANK
         */
        public static final int BLANK = 0x0006;
        /**
         * The unique identifier for terminal NEW_LINE
         */
        public static final int NEW_LINE = 0x0003;
        /**
         * The unique identifier for terminal WHITE_SPACE
         */
        public static final int WHITE_SPACE = 0x0004;
        /**
         * The unique identifier for terminal PNAME_NS
         */
        public static final int PNAME_NS = 0x000F;
        /**
         * The unique identifier for terminal INTEGER
         */
        public static final int INTEGER = 0x0013;
        /**
         * The unique identifier for terminal COMMENT
         */
        public static final int COMMENT = 0x0005;
        /**
         * The unique identifier for terminal LANGTAG
         */
        public static final int LANGTAG = 0x0007;
        /**
         * The unique identifier for terminal IRIREF
         */
        public static final int IRIREF = 0x0008;
        /**
         * The unique identifier for terminal ANON
         */
        public static final int ANON = 0x0012;
        /**
         * The unique identifier for terminal STRING_LITERAL_QUOTE
         */
        public static final int STRING_LITERAL_QUOTE = 0x0016;
        /**
         * The unique identifier for terminal STRING_LITERAL_SINGLE_QUOTE
         */
        public static final int STRING_LITERAL_SINGLE_QUOTE = 0x0017;
        /**
         * The unique identifier for terminal DECIMAL
         */
        public static final int DECIMAL = 0x0014;
        /**
         * The unique identifier for terminal PNAME_LN
         */
        public static final int PNAME_LN = 0x0010;
        /**
         * The unique identifier for terminal BLANK_NODE_LABEL
         */
        public static final int BLANK_NODE_LABEL = 0x0011;
        /**
         * The unique identifier for terminal DOUBLE
         */
        public static final int DOUBLE = 0x0015;
        /**
         * The unique identifier for terminal BASE
         */
        public static final int BASE = 0x001B;
        /**
         * The unique identifier for terminal TRUE
         */
        public static final int TRUE = 0x001F;
        /**
         * The unique identifier for terminal BASE2
         */
        public static final int BASE2 = 0x001D;
        /**
         * The unique identifier for terminal FALSE
         */
        public static final int FALSE = 0x0020;
        /**
         * The unique identifier for terminal STRING_LITERAL_LONG_QUOTE
         */
        public static final int STRING_LITERAL_LONG_QUOTE = 0x0019;
        /**
         * The unique identifier for terminal STRING_LITERAL_LONG_SINGLE_QUOTE
         */
        public static final int STRING_LITERAL_LONG_SINGLE_QUOTE = 0x0018;
        /**
         * The unique identifier for terminal PREFIX
         */
        public static final int PREFIX = 0x001A;
        /**
         * The unique identifier for terminal PREFIX2
         */
        public static final int PREFIX2 = 0x001C;
    }

    /**
     * Contains the constant IDs for the contexts for this lexer
     */
    public static class Context {
        /**
         * The unique identifier for the default context
         */
        public static final int DEFAULT = 0;
    }

    /**
     * The collection of terminals matched by this lexer
     * <p>
     * The terminals are in an order consistent with the automaton, so that
     * terminal indices in the automaton can be used to retrieve the terminals
     * in this table
     */
    private static final Symbol[] terminals = {new Symbol(0x0001, "ε"), new Symbol(0x0002, "$"), new Symbol(0x003F, "["), new Symbol(0x001E, "A"), new Symbol(0x003A, "."), new Symbol(0x003B, ";"), new Symbol(0x003D, ","), new Symbol(0x0040, "]"), new Symbol(0x0041, "("), new Symbol(0x0043, ")"), new Symbol(0x0006, "BLANK"), new Symbol(0x0003, "NEW_LINE"), new Symbol(0x0004, "WHITE_SPACE"), new Symbol(0x000F, "PNAME_NS"), new Symbol(0x0013, "INTEGER"), new Symbol(0x0005, "COMMENT"), new Symbol(0x0007, "LANGTAG"), new Symbol(0x0008, "IRIREF"), new Symbol(0x0012, "ANON"), new Symbol(0x0016, "STRING_LITERAL_QUOTE"), new Symbol(0x0017, "STRING_LITERAL_SINGLE_QUOTE"), new Symbol(0x0014, "DECIMAL"), new Symbol(0x0044, "^^"), new Symbol(0x0010, "PNAME_LN"), new Symbol(0x0011, "BLANK_NODE_LABEL"), new Symbol(0x0015, "DOUBLE"), new Symbol(0x001B, "BASE"), new Symbol(0x001F, "TRUE"), new Symbol(0x001D, "BASE2"), new Symbol(0x0020, "FALSE"), new Symbol(0x0019, "STRING_LITERAL_LONG_QUOTE"),
            new Symbol(0x0018, "STRING_LITERAL_LONG_SINGLE_QUOTE"), new Symbol(0x001A, "PREFIX"), new Symbol(0x001C, "PREFIX2")};

    /**
     * Initializes a new instance of the lexer
     *
     * @param input The lexer's input
     */
    public TurtleLexer(String input) {
        super(commonAutomaton, terminals, 0x0006, input);
    }

    /**
     * Initializes a new instance of the lexer
     *
     * @param input The lexer's input
     */
    public TurtleLexer(InputStreamReader input) {
        super(commonAutomaton, terminals, 0x0006, input);
    }
}
