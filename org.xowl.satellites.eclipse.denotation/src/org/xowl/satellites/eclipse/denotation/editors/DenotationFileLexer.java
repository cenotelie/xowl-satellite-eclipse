/*
 * WARNING: this file has been generated by
 * Hime Parser Generator 3.0.0.0
 */

package org.xowl.satellites.eclipse.denotation.editors;

import fr.cenotelie.hime.redist.Symbol;
import fr.cenotelie.hime.redist.lexer.Automaton;
import fr.cenotelie.hime.redist.lexer.ContextFreeLexer;

import java.io.InputStreamReader;

/**
 * Represents a lexer
 */
class DenotationFileLexer extends ContextFreeLexer {
    /**
     * The automaton for this lexer
     */
    private static final Automaton commonAutomaton = Automaton.find(DenotationFileLexer.class, "DenotationFileLexer.bin");

    /**
     * Contains the constant IDs for the terminals for this lexer
     */
    public static class ID {
        /**
         * The unique identifier for terminal OP_EQ
         */
        public static final int OP_EQ = 0x0016;
        /**
         * The unique identifier for terminal OP_MEMBER
         */
        public static final int OP_MEMBER = 0x0018;
        /**
         * The unique identifier for terminal A
         */
        public static final int A = 0x0028;
        /**
         * The unique identifier for terminal LP
         */
        public static final int LP = 0x002B;
        /**
         * The unique identifier for terminal RP
         */
        public static final int RP = 0x002C;
        /**
         * The unique identifier for terminal LCB
         */
        public static final int LCB = 0x002D;
        /**
         * The unique identifier for terminal RCB
         */
        public static final int RCB = 0x002E;
        /**
         * The unique identifier for terminal LB
         */
        public static final int LB = 0x002F;
        /**
         * The unique identifier for terminal RB
         */
        public static final int RB = 0x0030;
        /**
         * The unique identifier for terminal COMMA
         */
        public static final int COMMA = 0x0031;
        /**
         * The unique identifier for terminal COLON
         */
        public static final int COLON = 0x0032;
        /**
         * The unique identifier for terminal PNAME_NS
         */
        public static final int PNAME_NS = 0x000E;
        /**
         * The unique identifier for terminal INTEGER
         */
        public static final int INTEGER = 0x0010;
        /**
         * The unique identifier for terminal BLANK
         */
        public static final int BLANK = 0x0006;
        /**
         * The unique identifier for terminal COMMENT
         */
        public static final int COMMENT = 0x0005;
        /**
         * The unique identifier for terminal IRIREF
         */
        public static final int IRIREF = 0x0007;
        /**
         * The unique identifier for terminal OP_NEQ
         */
        public static final int OP_NEQ = 0x0017;
        /**
         * The unique identifier for terminal DECIMAL
         */
        public static final int DECIMAL = 0x0011;
        /**
         * The unique identifier for terminal VARIABLE
         */
        public static final int VARIABLE = 0x0019;
        /**
         * The unique identifier for terminal TO
         */
        public static final int TO = 0x0027;
        /**
         * The unique identifier for terminal IS
         */
        public static final int IS = 0x001F;
        /**
         * The unique identifier for terminal ID
         */
        public static final int ID = 0x0029;
        /**
         * The unique identifier for terminal AS
         */
        public static final int AS = 0x002A;
        /**
         * The unique identifier for terminal PNAME_LN
         */
        public static final int PNAME_LN = 0x000F;
        /**
         * The unique identifier for terminal STRING
         */
        public static final int STRING = 0x0015;
        /**
         * The unique identifier for terminal AND
         */
        public static final int AND = 0x0022;
        /**
         * The unique identifier for terminal DOUBLE
         */
        public static final int DOUBLE = 0x0012;
        /**
         * The unique identifier for terminal BASE
         */
        public static final int BASE = 0x001A;
        /**
         * The unique identifier for terminal BIND
         */
        public static final int BIND = 0x0026;
        /**
         * The unique identifier for terminal TRUE
         */
        public static final int TRUE = 0x001C;
        /**
         * The unique identifier for terminal RULE
         */
        public static final int RULE = 0x001E;
        /**
         * The unique identifier for terminal SIGN
         */
        public static final int SIGN = 0x0020;
        /**
         * The unique identifier for terminal SEME
         */
        public static final int SEME = 0x0021;
        /**
         * The unique identifier for terminal WITH
         */
        public static final int WITH = 0x0023;
        /**
         * The unique identifier for terminal BOUND
         */
        public static final int BOUND = 0x0025;
        /**
         * The unique identifier for terminal FALSE
         */
        public static final int FALSE = 0x001D;
        /**
         * The unique identifier for terminal PREFIX
         */
        public static final int PREFIX = 0x001B;
        /**
         * The unique identifier for terminal RELATION
         */
        public static final int RELATION = 0x0024;
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
     * The terminals are in an order consistent with the automaton,
     * so that terminal indices in the automaton can be used to retrieve the terminals in this table
     */
    private static final Symbol[] terminals = {
            new Symbol(0x0001, "ε"),
            new Symbol(0x0002, "$"),
            new Symbol(0x0016, "OP_EQ"),
            new Symbol(0x0018, "OP_MEMBER"),
            new Symbol(0x0028, "A"),
            new Symbol(0x002B, "LP"),
            new Symbol(0x002C, "RP"),
            new Symbol(0x002D, "LCB"),
            new Symbol(0x002E, "RCB"),
            new Symbol(0x002F, "LB"),
            new Symbol(0x0030, "RB"),
            new Symbol(0x0031, "COMMA"),
            new Symbol(0x0032, "COLON"),
            new Symbol(0x000E, "PNAME_NS"),
            new Symbol(0x0010, "INTEGER"),
            new Symbol(0x0006, "BLANK"),
            new Symbol(0x0005, "COMMENT"),
            new Symbol(0x0007, "IRIREF"),
            new Symbol(0x0017, "OP_NEQ"),
            new Symbol(0x0011, "DECIMAL"),
            new Symbol(0x0019, "VARIABLE"),
            new Symbol(0x0027, "TO"),
            new Symbol(0x001F, "IS"),
            new Symbol(0x0029, "ID"),
            new Symbol(0x002A, "AS"),
            new Symbol(0x000F, "PNAME_LN"),
            new Symbol(0x0015, "STRING"),
            new Symbol(0x0022, "AND"),
            new Symbol(0x0012, "DOUBLE"),
            new Symbol(0x001A, "BASE"),
            new Symbol(0x0026, "BIND"),
            new Symbol(0x001C, "TRUE"),
            new Symbol(0x001E, "RULE"),
            new Symbol(0x0020, "SIGN"),
            new Symbol(0x0021, "SEME"),
            new Symbol(0x0023, "WITH"),
            new Symbol(0x0025, "BOUND"),
            new Symbol(0x001D, "FALSE"),
            new Symbol(0x001B, "PREFIX"),
            new Symbol(0x0024, "RELATION")};

    /**
     * Initializes a new instance of the lexer
     *
     * @param input The lexer's input
     */
    public DenotationFileLexer(String input) {
        super(commonAutomaton, terminals, 0x0006, input);
    }

    /**
     * Initializes a new instance of the lexer
     *
     * @param input The lexer's input
     */
    public DenotationFileLexer(InputStreamReader input) {
        super(commonAutomaton, terminals, 0x0006, input);
    }
}
