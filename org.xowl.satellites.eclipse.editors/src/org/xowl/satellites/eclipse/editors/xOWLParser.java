/*
 * WARNING: this file has been generated by
 * Hime Parser Generator 3.0.0.0
 */

package org.xowl.satellites.eclipse.editors;

import fr.cenotelie.hime.redist.Symbol;
import fr.cenotelie.hime.redist.parsers.InitializationException;
import fr.cenotelie.hime.redist.parsers.RNGLRAutomaton;
import fr.cenotelie.hime.redist.parsers.RNGLRParser;

/**
 * Represents a parser
 */
class xOWLParser extends RNGLRParser {
    /**
     * The automaton for this parser
     */
    private static final RNGLRAutomaton commonAutomaton = RNGLRAutomaton.find(xOWLParser.class, "xOWLParser.bin");

    /**
     * Contains the constant IDs for the variables and virtuals in this parser
     */
    public static class ID {
        /**
         * The unique identifier for variable document
         */
        public static final int document = 0x0040;
        /**
         * The unique identifier for variable prefixes
         */
        public static final int prefixes = 0x0041;
        /**
         * The unique identifier for variable prefix
         */
        public static final int prefix = 0x0042;
        /**
         * The unique identifier for variable ontology
         */
        public static final int ontology = 0x0043;
        /**
         * The unique identifier for variable ontologyIRI
         */
        public static final int ontologyIRI = 0x0044;
        /**
         * The unique identifier for variable iri
         */
        public static final int iri = 0x0045;
        /**
         * The unique identifier for variable imports
         */
        public static final int imports = 0x0046;
        /**
         * The unique identifier for variable import
         */
        public static final int _import = 0x0047;
        /**
         * The unique identifier for variable elements
         */
        public static final int elements = 0x0048;
        /**
         * The unique identifier for variable element
         */
        public static final int element = 0x0049;
        /**
         * The unique identifier for variable annotations
         */
        public static final int annotations = 0x004A;
        /**
         * The unique identifier for variable annotation
         */
        public static final int annotation = 0x004B;
        /**
         * The unique identifier for variable annotationSubject
         */
        public static final int annotationSubject = 0x004C;
        /**
         * The unique identifier for variable annotationProperty
         */
        public static final int annotationProperty = 0x004D;
        /**
         * The unique identifier for variable annotationValue
         */
        public static final int annotationValue = 0x004E;
        /**
         * The unique identifier for variable entity
         */
        public static final int entity = 0x004F;
        /**
         * The unique identifier for variable individual
         */
        public static final int individual = 0x0050;
        /**
         * The unique identifier for variable namedIndividual
         */
        public static final int namedIndividual = 0x0051;
        /**
         * The unique identifier for variable anonymousIndividual
         */
        public static final int anonymousIndividual = 0x0052;
        /**
         * The unique identifier for variable literal
         */
        public static final int literal = 0x0053;
        /**
         * The unique identifier for variable literalTyped
         */
        public static final int literalTyped = 0x0054;
        /**
         * The unique identifier for variable literalString
         */
        public static final int literalString = 0x0055;
        /**
         * The unique identifier for variable literalLang
         */
        public static final int literalLang = 0x0056;
        /**
         * The unique identifier for variable expObjectProperty
         */
        public static final int expObjectProperty = 0x0057;
        /**
         * The unique identifier for variable expObjectPropertyChain
         */
        public static final int expObjectPropertyChain = 0x0058;
        /**
         * The unique identifier for variable expInverseObjectProperty
         */
        public static final int expInverseObjectProperty = 0x0059;
        /**
         * The unique identifier for variable expDataProperty
         */
        public static final int expDataProperty = 0x005A;
        /**
         * The unique identifier for variable expDatarange
         */
        public static final int expDatarange = 0x005B;
        /**
         * The unique identifier for variable expDataIntersectionOf
         */
        public static final int expDataIntersectionOf = 0x005C;
        /**
         * The unique identifier for variable expDataUnionOf
         */
        public static final int expDataUnionOf = 0x005D;
        /**
         * The unique identifier for variable expDataComplementOf
         */
        public static final int expDataComplementOf = 0x005E;
        /**
         * The unique identifier for variable expDataOneOf
         */
        public static final int expDataOneOf = 0x005F;
        /**
         * The unique identifier for variable expDatatypeRestriction
         */
        public static final int expDatatypeRestriction = 0x0060;
        /**
         * The unique identifier for variable expRestriction
         */
        public static final int expRestriction = 0x0061;
        /**
         * The unique identifier for variable expClass
         */
        public static final int expClass = 0x0062;
        /**
         * The unique identifier for variable expObjectInterfactionOf
         */
        public static final int expObjectInterfactionOf = 0x0063;
        /**
         * The unique identifier for variable expObjectUnionOf
         */
        public static final int expObjectUnionOf = 0x0064;
        /**
         * The unique identifier for variable expObjectComplementOf
         */
        public static final int expObjectComplementOf = 0x0065;
        /**
         * The unique identifier for variable expObjectOneOf
         */
        public static final int expObjectOneOf = 0x0066;
        /**
         * The unique identifier for variable expObjectSomeValuesFrom
         */
        public static final int expObjectSomeValuesFrom = 0x0067;
        /**
         * The unique identifier for variable expObjectAllValuesFrom
         */
        public static final int expObjectAllValuesFrom = 0x0068;
        /**
         * The unique identifier for variable expObjectHasValue
         */
        public static final int expObjectHasValue = 0x0069;
        /**
         * The unique identifier for variable expObjectHasSelf
         */
        public static final int expObjectHasSelf = 0x006A;
        /**
         * The unique identifier for variable expObjectMinCardinality
         */
        public static final int expObjectMinCardinality = 0x006B;
        /**
         * The unique identifier for variable expObjectMaxCardinality
         */
        public static final int expObjectMaxCardinality = 0x006C;
        /**
         * The unique identifier for variable expObjectExactCardinality
         */
        public static final int expObjectExactCardinality = 0x006D;
        /**
         * The unique identifier for variable expDataSomeValuesFrom
         */
        public static final int expDataSomeValuesFrom = 0x006E;
        /**
         * The unique identifier for variable expDataAllValuesFrom
         */
        public static final int expDataAllValuesFrom = 0x006F;
        /**
         * The unique identifier for variable expDataHasValue
         */
        public static final int expDataHasValue = 0x0070;
        /**
         * The unique identifier for variable expDataMinCardinality
         */
        public static final int expDataMinCardinality = 0x0071;
        /**
         * The unique identifier for variable expDataMaxCardinality
         */
        public static final int expDataMaxCardinality = 0x0072;
        /**
         * The unique identifier for variable expDataExactCardinality
         */
        public static final int expDataExactCardinality = 0x0073;
        /**
         * The unique identifier for variable axiom
         */
        public static final int axiom = 0x0074;
        /**
         * The unique identifier for variable axiomDeclaration
         */
        public static final int axiomDeclaration = 0x0075;
        /**
         * The unique identifier for variable axiomClass
         */
        public static final int axiomClass = 0x0076;
        /**
         * The unique identifier for variable axiomSubClassOf
         */
        public static final int axiomSubClassOf = 0x0077;
        /**
         * The unique identifier for variable axiomEquivalentClasses
         */
        public static final int axiomEquivalentClasses = 0x0078;
        /**
         * The unique identifier for variable axiomDisjointClasses
         */
        public static final int axiomDisjointClasses = 0x0079;
        /**
         * The unique identifier for variable axiomDisjointUnion
         */
        public static final int axiomDisjointUnion = 0x007A;
        /**
         * The unique identifier for variable axiomObjectProperty
         */
        public static final int axiomObjectProperty = 0x007B;
        /**
         * The unique identifier for variable axiomSubOjectPropertyOf
         */
        public static final int axiomSubOjectPropertyOf = 0x007C;
        /**
         * The unique identifier for variable axiomEquivalentObjectProperties
         */
        public static final int axiomEquivalentObjectProperties = 0x007D;
        /**
         * The unique identifier for variable axiomDisjointObjectProperties
         */
        public static final int axiomDisjointObjectProperties = 0x007E;
        /**
         * The unique identifier for variable axiomInverseObjectProperties
         */
        public static final int axiomInverseObjectProperties = 0x007F;
        /**
         * The unique identifier for variable axiomObjectPropertyDomain
         */
        public static final int axiomObjectPropertyDomain = 0x0080;
        /**
         * The unique identifier for variable axiomObjectPropertyRange
         */
        public static final int axiomObjectPropertyRange = 0x0081;
        /**
         * The unique identifier for variable axiomFunctionalObjectProperty
         */
        public static final int axiomFunctionalObjectProperty = 0x0082;
        /**
         * The unique identifier for variable axiomInverseFunctionalObjectProperty
         */
        public static final int axiomInverseFunctionalObjectProperty = 0x0083;
        /**
         * The unique identifier for variable axiomReflexiveObjectProperty
         */
        public static final int axiomReflexiveObjectProperty = 0x0084;
        /**
         * The unique identifier for variable axiomIrreflexiveObjectProperty
         */
        public static final int axiomIrreflexiveObjectProperty = 0x0085;
        /**
         * The unique identifier for variable axiomSymmetricObjectProperty
         */
        public static final int axiomSymmetricObjectProperty = 0x0086;
        /**
         * The unique identifier for variable axiomAsymmetricObjectProperty
         */
        public static final int axiomAsymmetricObjectProperty = 0x0087;
        /**
         * The unique identifier for variable axiomTransitiveObjectProperty
         */
        public static final int axiomTransitiveObjectProperty = 0x0088;
        /**
         * The unique identifier for variable axiomDataProperty
         */
        public static final int axiomDataProperty = 0x0089;
        /**
         * The unique identifier for variable axiomSubDataPropertyOf
         */
        public static final int axiomSubDataPropertyOf = 0x008A;
        /**
         * The unique identifier for variable axiomEquivalentDataProperties
         */
        public static final int axiomEquivalentDataProperties = 0x008B;
        /**
         * The unique identifier for variable axiomDisjointDataProperties
         */
        public static final int axiomDisjointDataProperties = 0x008C;
        /**
         * The unique identifier for variable axiomDataPropertyDomain
         */
        public static final int axiomDataPropertyDomain = 0x008D;
        /**
         * The unique identifier for variable axiomDataPropertyRange
         */
        public static final int axiomDataPropertyRange = 0x008E;
        /**
         * The unique identifier for variable axiomFunctionalDataProperty
         */
        public static final int axiomFunctionalDataProperty = 0x008F;
        /**
         * The unique identifier for variable axiomDatatype
         */
        public static final int axiomDatatype = 0x0090;
        /**
         * The unique identifier for variable axiomHasKey
         */
        public static final int axiomHasKey = 0x0091;
        /**
         * The unique identifier for variable axiomHasKeyObjectProperties
         */
        public static final int axiomHasKeyObjectProperties = 0x0092;
        /**
         * The unique identifier for variable axiomHasKeyDataProperties
         */
        public static final int axiomHasKeyDataProperties = 0x0093;
        /**
         * The unique identifier for variable axiomAssertion
         */
        public static final int axiomAssertion = 0x0094;
        /**
         * The unique identifier for variable axiomSameIndividual
         */
        public static final int axiomSameIndividual = 0x0095;
        /**
         * The unique identifier for variable axiomDifferentIndividuals
         */
        public static final int axiomDifferentIndividuals = 0x0096;
        /**
         * The unique identifier for variable axiomClassAssertion
         */
        public static final int axiomClassAssertion = 0x0097;
        /**
         * The unique identifier for variable axiomObjectPropertyAssertion
         */
        public static final int axiomObjectPropertyAssertion = 0x0098;
        /**
         * The unique identifier for variable axiomNegativeObjectPropertyAssertion
         */
        public static final int axiomNegativeObjectPropertyAssertion = 0x0099;
        /**
         * The unique identifier for variable axiomDataPropertyAssertion
         */
        public static final int axiomDataPropertyAssertion = 0x009A;
        /**
         * The unique identifier for variable axiomNegativeDataPropertyAssertion
         */
        public static final int axiomNegativeDataPropertyAssertion = 0x009B;
        /**
         * The unique identifier for variable axiomAnnotation
         */
        public static final int axiomAnnotation = 0x009C;
        /**
         * The unique identifier for variable axiomAnnotationAssertion
         */
        public static final int axiomAnnotationAssertion = 0x009D;
        /**
         * The unique identifier for variable axiomSubAnnotationPropertyOf
         */
        public static final int axiomSubAnnotationPropertyOf = 0x009E;
        /**
         * The unique identifier for variable axiomAnnotationPropertyDomain
         */
        public static final int axiomAnnotationPropertyDomain = 0x009F;
        /**
         * The unique identifier for variable axiomAnnotationPropertyRange
         */
        public static final int axiomAnnotationPropertyRange = 0x00A0;
        /**
         * The unique identifier for variable atom
         */
        public static final int atom = 0x00A1;
        /**
         * The unique identifier for variable list
         */
        public static final int list = 0x00A2;
        /**
         * The unique identifier for variable vector
         */
        public static final int vector = 0x00A3;
        /**
         * The unique identifier for variable map
         */
        public static final int map = 0x00A4;
        /**
         * The unique identifier for variable couple
         */
        public static final int couple = 0x00A5;
        /**
         * The unique identifier for variable set
         */
        public static final int set = 0x00A6;
        /**
         * The unique identifier for variable constructor
         */
        public static final int constructor = 0x00A7;
        /**
         * The unique identifier for variable quote
         */
        public static final int quote = 0x00A8;
        /**
         * The unique identifier for variable deref
         */
        public static final int deref = 0x00A9;
        /**
         * The unique identifier for variable metadata
         */
        public static final int metadata = 0x00AA;
        /**
         * The unique identifier for variable regexp
         */
        public static final int regexp = 0x00AB;
        /**
         * The unique identifier for variable var_quote
         */
        public static final int var_quote = 0x00AC;
        /**
         * The unique identifier for variable anon_function
         */
        public static final int anon_function = 0x00AD;
        /**
         * The unique identifier for variable ignore
         */
        public static final int ignore = 0x00AE;
        /**
         * The unique identifier for variable syntax_quote
         */
        public static final int syntax_quote = 0x00AF;
        /**
         * The unique identifier for variable unquote
         */
        public static final int unquote = 0x00B0;
        /**
         * The unique identifier for variable unquote_splicing
         */
        public static final int unquote_splicing = 0x00B1;
        /**
         * The unique identifier for variable conditional
         */
        public static final int conditional = 0x00B2;
        /**
         * The unique identifier for variable form
         */
        public static final int form = 0x00B3;
        /**
         * The unique identifier for variable literalInteger
         */
        public static final int literalInteger = 0x00B4;
        /**
         * The unique identifier for variable rule
         */
        public static final int rule = 0x00B5;
        /**
         * The unique identifier for variable ruleAntecedents
         */
        public static final int ruleAntecedents = 0x00B6;
        /**
         * The unique identifier for variable ruleConsequents
         */
        public static final int ruleConsequents = 0x00B7;
        /**
         * The unique identifier for variable ruleGuard
         */
        public static final int ruleGuard = 0x00B8;
        /**
         * The unique identifier for variable assertion
         */
        public static final int assertion = 0x00B9;
        /**
         * The unique identifier for variable axiomPattern
         */
        public static final int axiomPattern = 0x00BA;
        /**
         * The unique identifier for variable expDynamic
         */
        public static final int expDynamic = 0x00BB;
        /**
         * The unique identifier for variable axiomFunctionDefinition
         */
        public static final int axiomFunctionDefinition = 0x00BC;
        /**
         * The unique identifier for variable __axiom
         */
        public static final int __axiom = 0x012F;
    }

    /**
     * The collection of variables matched by this parser
     * <p>
     * The variables are in an order consistent with the automaton,
     * so that variable indices in the automaton can be used to retrieve the variables in this table
     */
    private static final Symbol[] variables = {
            new Symbol(0x0040, "document"),
            new Symbol(0x0041, "prefixes"),
            new Symbol(0x0042, "prefix"),
            new Symbol(0x0043, "ontology"),
            new Symbol(0x0044, "ontologyIRI"),
            new Symbol(0x0045, "iri"),
            new Symbol(0x0046, "imports"),
            new Symbol(0x0047, "import"),
            new Symbol(0x0048, "elements"),
            new Symbol(0x0049, "element"),
            new Symbol(0x004A, "annotations"),
            new Symbol(0x004B, "annotation"),
            new Symbol(0x004C, "annotationSubject"),
            new Symbol(0x004D, "annotationProperty"),
            new Symbol(0x004E, "annotationValue"),
            new Symbol(0x004F, "entity"),
            new Symbol(0x0050, "individual"),
            new Symbol(0x0051, "namedIndividual"),
            new Symbol(0x0052, "anonymousIndividual"),
            new Symbol(0x0053, "literal"),
            new Symbol(0x0054, "literalTyped"),
            new Symbol(0x0055, "literalString"),
            new Symbol(0x0056, "literalLang"),
            new Symbol(0x0057, "expObjectProperty"),
            new Symbol(0x0058, "expObjectPropertyChain"),
            new Symbol(0x0059, "expInverseObjectProperty"),
            new Symbol(0x005A, "expDataProperty"),
            new Symbol(0x005B, "expDatarange"),
            new Symbol(0x005C, "expDataIntersectionOf"),
            new Symbol(0x005D, "expDataUnionOf"),
            new Symbol(0x005E, "expDataComplementOf"),
            new Symbol(0x005F, "expDataOneOf"),
            new Symbol(0x0060, "expDatatypeRestriction"),
            new Symbol(0x0061, "expRestriction"),
            new Symbol(0x0062, "expClass"),
            new Symbol(0x0063, "expObjectInterfactionOf"),
            new Symbol(0x0064, "expObjectUnionOf"),
            new Symbol(0x0065, "expObjectComplementOf"),
            new Symbol(0x0066, "expObjectOneOf"),
            new Symbol(0x0067, "expObjectSomeValuesFrom"),
            new Symbol(0x0068, "expObjectAllValuesFrom"),
            new Symbol(0x0069, "expObjectHasValue"),
            new Symbol(0x006A, "expObjectHasSelf"),
            new Symbol(0x006B, "expObjectMinCardinality"),
            new Symbol(0x006C, "expObjectMaxCardinality"),
            new Symbol(0x006D, "expObjectExactCardinality"),
            new Symbol(0x006E, "expDataSomeValuesFrom"),
            new Symbol(0x006F, "expDataAllValuesFrom"),
            new Symbol(0x0070, "expDataHasValue"),
            new Symbol(0x0071, "expDataMinCardinality"),
            new Symbol(0x0072, "expDataMaxCardinality"),
            new Symbol(0x0073, "expDataExactCardinality"),
            new Symbol(0x0074, "axiom"),
            new Symbol(0x0075, "axiomDeclaration"),
            new Symbol(0x0076, "axiomClass"),
            new Symbol(0x0077, "axiomSubClassOf"),
            new Symbol(0x0078, "axiomEquivalentClasses"),
            new Symbol(0x0079, "axiomDisjointClasses"),
            new Symbol(0x007A, "axiomDisjointUnion"),
            new Symbol(0x007B, "axiomObjectProperty"),
            new Symbol(0x007C, "axiomSubOjectPropertyOf"),
            new Symbol(0x007D, "axiomEquivalentObjectProperties"),
            new Symbol(0x007E, "axiomDisjointObjectProperties"),
            new Symbol(0x007F, "axiomInverseObjectProperties"),
            new Symbol(0x0080, "axiomObjectPropertyDomain"),
            new Symbol(0x0081, "axiomObjectPropertyRange"),
            new Symbol(0x0082, "axiomFunctionalObjectProperty"),
            new Symbol(0x0083, "axiomInverseFunctionalObjectProperty"),
            new Symbol(0x0084, "axiomReflexiveObjectProperty"),
            new Symbol(0x0085, "axiomIrreflexiveObjectProperty"),
            new Symbol(0x0086, "axiomSymmetricObjectProperty"),
            new Symbol(0x0087, "axiomAsymmetricObjectProperty"),
            new Symbol(0x0088, "axiomTransitiveObjectProperty"),
            new Symbol(0x0089, "axiomDataProperty"),
            new Symbol(0x008A, "axiomSubDataPropertyOf"),
            new Symbol(0x008B, "axiomEquivalentDataProperties"),
            new Symbol(0x008C, "axiomDisjointDataProperties"),
            new Symbol(0x008D, "axiomDataPropertyDomain"),
            new Symbol(0x008E, "axiomDataPropertyRange"),
            new Symbol(0x008F, "axiomFunctionalDataProperty"),
            new Symbol(0x0090, "axiomDatatype"),
            new Symbol(0x0091, "axiomHasKey"),
            new Symbol(0x0092, "axiomHasKeyObjectProperties"),
            new Symbol(0x0093, "axiomHasKeyDataProperties"),
            new Symbol(0x0094, "axiomAssertion"),
            new Symbol(0x0095, "axiomSameIndividual"),
            new Symbol(0x0096, "axiomDifferentIndividuals"),
            new Symbol(0x0097, "axiomClassAssertion"),
            new Symbol(0x0098, "axiomObjectPropertyAssertion"),
            new Symbol(0x0099, "axiomNegativeObjectPropertyAssertion"),
            new Symbol(0x009A, "axiomDataPropertyAssertion"),
            new Symbol(0x009B, "axiomNegativeDataPropertyAssertion"),
            new Symbol(0x009C, "axiomAnnotation"),
            new Symbol(0x009D, "axiomAnnotationAssertion"),
            new Symbol(0x009E, "axiomSubAnnotationPropertyOf"),
            new Symbol(0x009F, "axiomAnnotationPropertyDomain"),
            new Symbol(0x00A0, "axiomAnnotationPropertyRange"),
            new Symbol(0x00A1, "atom"),
            new Symbol(0x00A2, "list"),
            new Symbol(0x00A3, "vector"),
            new Symbol(0x00A4, "map"),
            new Symbol(0x00A5, "couple"),
            new Symbol(0x00A6, "set"),
            new Symbol(0x00A7, "constructor"),
            new Symbol(0x00A8, "quote"),
            new Symbol(0x00A9, "deref"),
            new Symbol(0x00AA, "metadata"),
            new Symbol(0x00AB, "regexp"),
            new Symbol(0x00AC, "var_quote"),
            new Symbol(0x00AD, "anon_function"),
            new Symbol(0x00AE, "ignore"),
            new Symbol(0x00AF, "syntax_quote"),
            new Symbol(0x00B0, "unquote"),
            new Symbol(0x00B1, "unquote_splicing"),
            new Symbol(0x00B2, "conditional"),
            new Symbol(0x00B3, "form"),
            new Symbol(0x00B4, "literalInteger"),
            new Symbol(0x00B5, "rule"),
            new Symbol(0x00B6, "ruleAntecedents"),
            new Symbol(0x00B7, "ruleConsequents"),
            new Symbol(0x00B8, "ruleGuard"),
            new Symbol(0x00B9, "assertion"),
            new Symbol(0x00BA, "axiomPattern"),
            new Symbol(0x00BB, "expDynamic"),
            new Symbol(0x00BC, "axiomFunctionDefinition"),
            new Symbol(0x00BD, "__V189"),
            new Symbol(0x00BE, "__V190"),
            new Symbol(0x00C1, "__V193"),
            new Symbol(0x00C3, "__V195"),
            new Symbol(0x00C4, "__V196"),
            new Symbol(0x00C7, "__V199"),
            new Symbol(0x00CA, "__V202"),
            new Symbol(0x00CC, "__V204"),
            new Symbol(0x00CF, "__V207"),
            new Symbol(0x00D1, "__V209"),
            new Symbol(0x00D3, "__V211"),
            new Symbol(0x00D5, "__V213"),
            new Symbol(0x00D8, "__V216"),
            new Symbol(0x00E1, "__V225"),
            new Symbol(0x00E3, "__V227"),
            new Symbol(0x00F1, "__V241"),
            new Symbol(0x00F3, "__V243"),
            new Symbol(0x00F5, "__V245"),
            new Symbol(0x00F8, "__V248"),
            new Symbol(0x00FA, "__V250"),
            new Symbol(0x0107, "__V263"),
            new Symbol(0x0109, "__V265"),
            new Symbol(0x010F, "__V271"),
            new Symbol(0x0110, "__V272"),
            new Symbol(0x0112, "__V274"),
            new Symbol(0x0114, "__V276"),
            new Symbol(0x011E, "__V286"),
            new Symbol(0x011F, "__V287"),
            new Symbol(0x0120, "__V288"),
            new Symbol(0x0121, "__V289"),
            new Symbol(0x0122, "__V290"),
            new Symbol(0x0123, "__V291"),
            new Symbol(0x0124, "__V292"),
            new Symbol(0x0125, "__V293"),
            new Symbol(0x0126, "__V294"),
            new Symbol(0x0127, "__V295"),
            new Symbol(0x0128, "__V296"),
            new Symbol(0x0129, "__V297"),
            new Symbol(0x012A, "__V298"),
            new Symbol(0x012B, "__V299"),
            new Symbol(0x012C, "__V300"),
            new Symbol(0x012E, "__V302"),
            new Symbol(0x012F, "__axiom")};
    /**
     * The collection of virtuals matched by this parser
     * <p>
     * The virtuals are in an order consistent with the automaton,
     * so that virtual indices in the automaton can be used to retrieve the virtuals in this table
     */
    private static final Symbol[] virtuals = {
    };

    /**
     * Initializes a new instance of the parser
     *
     * @param lexer The input lexer
     */
    public xOWLParser(xOWLLexer lexer) throws InitializationException {
        super(commonAutomaton, variables, virtuals, null, lexer);
    }
}
