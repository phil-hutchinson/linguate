/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathParserDefinition
{
    public final static TestImplGrammarTerminal NUMERIC_LITERAL = new TestImplGrammarTerminal("numeric");
    public final static TestImplGrammarTerminal ADDITION_OPERATOR = new TestImplGrammarTerminal("+");
    public final static TestImplGrammarTerminal MULTIPLICATION_OPERATOR = new TestImplGrammarTerminal("*");
    public final static TestImplGrammarTerminal LEFT_BRACKET = new TestImplGrammarTerminal("(");
    public final static TestImplGrammarTerminal RIGHT_BRACKET = new TestImplGrammarTerminal(")");
    
    public final static TestImplGrammarNonTerminal TERM = new TestImplGrammarNonTerminal("T");
    public final static TestImplGrammarNonTerminal EXPRESSION = new TestImplGrammarNonTerminal("E");
    public final static TestImplGrammarNonTerminal FACTOR = new TestImplGrammarNonTerminal("F");
    
    public final static TestImplGrammarProduction RULE_A = new TestImplGrammarProduction(
            EXPRESSION,
            EXPRESSION,
            ADDITION_OPERATOR,
            TERM
    );
    public final static TestImplGrammarProduction RULE_B = new TestImplGrammarProduction(
            EXPRESSION,
            TERM
    );
    public final static TestImplGrammarProduction RULE_C = new TestImplGrammarProduction(
            TERM,
            TERM,
            MULTIPLICATION_OPERATOR,
            FACTOR
    );
    public final static TestImplGrammarProduction RULE_D = new TestImplGrammarProduction(
            TERM,
            FACTOR
    );
    public final static TestImplGrammarProduction RULE_E = new TestImplGrammarProduction(
            FACTOR,
            LEFT_BRACKET,
            EXPRESSION,
            RIGHT_BRACKET
    );
    public final static TestImplGrammarProduction RULE_F = new TestImplGrammarProduction(
            FACTOR,
            NUMERIC_LITERAL
    );

    private final static TestImplLRParserState STATE_0 = new TestImplLRParserState("0");
    private final static TestImplLRParserState STATE_1 = new TestImplLRParserState("1");
    private final static TestImplLRParserState STATE_2 = new TestImplLRParserState("2");
    private final static TestImplLRParserState STATE_3 = new TestImplLRParserState("3");
    private final static TestImplLRParserState STATE_4 = new TestImplLRParserState("4");
    private final static TestImplLRParserState STATE_5 = new TestImplLRParserState("5");
    private final static TestImplLRParserState STATE_6 = new TestImplLRParserState("6");
    private final static TestImplLRParserState STATE_7 = new TestImplLRParserState("7");
    private final static TestImplLRParserState STATE_8 = new TestImplLRParserState("8");
    private final static TestImplLRParserState STATE_9 = new TestImplLRParserState("9");
    private final static TestImplLRParserState STATE_10 = new TestImplLRParserState("10");
    private final static TestImplLRParserState STATE_11 = new TestImplLRParserState("11");
    
    private final static TestImplLRParserAction ACCEPT_INPUT = new TestImplLRParserAction();
    private final static TestImplLRParserAction SHIFT_TO_STATE_4 = new TestImplLRParserAction(STATE_4);
    private final static TestImplLRParserAction SHIFT_TO_STATE_5 = new TestImplLRParserAction(STATE_5);
    private final static TestImplLRParserAction SHIFT_TO_STATE_6 = new TestImplLRParserAction(STATE_6);
    private final static TestImplLRParserAction SHIFT_TO_STATE_7 = new TestImplLRParserAction(STATE_7);
    private final static TestImplLRParserAction SHIFT_TO_STATE_11 = new TestImplLRParserAction(STATE_11);
    private final static TestImplLRParserAction REDUCE_WITH_RULE_A = new TestImplLRParserAction(RULE_A);
    private final static TestImplLRParserAction REDUCE_WITH_RULE_B = new TestImplLRParserAction(RULE_B);
    private final static TestImplLRParserAction REDUCE_WITH_RULE_C = new TestImplLRParserAction(RULE_C);
    private final static TestImplLRParserAction REDUCE_WITH_RULE_D = new TestImplLRParserAction(RULE_D);
    private final static TestImplLRParserAction REDUCE_WITH_RULE_E = new TestImplLRParserAction(RULE_E);
    private final static TestImplLRParserAction REDUCE_WITH_RULE_F = new TestImplLRParserAction(RULE_F);
    
    public final static TestImplLRParserDefinition definition;
    
    static 
    {
        STATE_0.actions.put(NUMERIC_LITERAL, SHIFT_TO_STATE_5);
        STATE_0.actions.put(LEFT_BRACKET, SHIFT_TO_STATE_4);
        STATE_0.postReductionStates.put(EXPRESSION, STATE_1);
        STATE_0.postReductionStates.put(TERM, STATE_2);
        STATE_0.postReductionStates.put(FACTOR, STATE_3);
        
        STATE_1.actions.put(ADDITION_OPERATOR, SHIFT_TO_STATE_6);
        STATE_1.endOfInputAction = ACCEPT_INPUT;
        
        STATE_2.actions.put(ADDITION_OPERATOR, REDUCE_WITH_RULE_B);
        STATE_2.actions.put(MULTIPLICATION_OPERATOR, SHIFT_TO_STATE_7);
        STATE_2.actions.put(RIGHT_BRACKET, REDUCE_WITH_RULE_B);
        STATE_2.endOfInputAction = REDUCE_WITH_RULE_B;
        
        STATE_3.actions.put(ADDITION_OPERATOR, REDUCE_WITH_RULE_D);
        STATE_3.actions.put(MULTIPLICATION_OPERATOR, REDUCE_WITH_RULE_D);
        STATE_3.actions.put(RIGHT_BRACKET, REDUCE_WITH_RULE_D);
        STATE_3.endOfInputAction = REDUCE_WITH_RULE_D;
        
        STATE_4.actions.put(NUMERIC_LITERAL, SHIFT_TO_STATE_5);
        STATE_4.actions.put(LEFT_BRACKET, SHIFT_TO_STATE_4);
        STATE_4.postReductionStates.put(EXPRESSION, STATE_8);
        STATE_4.postReductionStates.put(TERM, STATE_2);
        STATE_4.postReductionStates.put(FACTOR, STATE_3);

        STATE_5.actions.put(ADDITION_OPERATOR, REDUCE_WITH_RULE_F);
        STATE_5.actions.put(MULTIPLICATION_OPERATOR, REDUCE_WITH_RULE_F);
        STATE_5.actions.put(RIGHT_BRACKET, REDUCE_WITH_RULE_F);
        STATE_5.endOfInputAction = REDUCE_WITH_RULE_F;

        STATE_6.actions.put(NUMERIC_LITERAL, SHIFT_TO_STATE_5);
        STATE_6.actions.put(LEFT_BRACKET, SHIFT_TO_STATE_4);
        STATE_6.postReductionStates.put(TERM, STATE_9);
        STATE_6.postReductionStates.put(FACTOR, STATE_3);

        STATE_7.actions.put(NUMERIC_LITERAL, SHIFT_TO_STATE_5);
        STATE_7.actions.put(LEFT_BRACKET, SHIFT_TO_STATE_4);
        STATE_7.postReductionStates.put(FACTOR, STATE_10);

        STATE_8.actions.put(ADDITION_OPERATOR, SHIFT_TO_STATE_6);
        STATE_8.actions.put(RIGHT_BRACKET, SHIFT_TO_STATE_11);

        STATE_9.actions.put(ADDITION_OPERATOR, REDUCE_WITH_RULE_A);
        STATE_9.actions.put(MULTIPLICATION_OPERATOR, SHIFT_TO_STATE_7);
        STATE_9.actions.put(RIGHT_BRACKET, REDUCE_WITH_RULE_A);
        STATE_9.endOfInputAction = REDUCE_WITH_RULE_A;

        STATE_10.actions.put(ADDITION_OPERATOR, REDUCE_WITH_RULE_C);
        STATE_10.actions.put(MULTIPLICATION_OPERATOR, REDUCE_WITH_RULE_C);
        STATE_10.actions.put(RIGHT_BRACKET, REDUCE_WITH_RULE_C);
        STATE_10.endOfInputAction = REDUCE_WITH_RULE_C;

        STATE_11.actions.put(ADDITION_OPERATOR, REDUCE_WITH_RULE_E);
        STATE_11.actions.put(MULTIPLICATION_OPERATOR, REDUCE_WITH_RULE_E);
        STATE_11.actions.put(RIGHT_BRACKET, REDUCE_WITH_RULE_E);
        STATE_11.endOfInputAction = REDUCE_WITH_RULE_E;
        
        definition = new TestImplLRParserDefinition(STATE_0);
    }
    //termAndExpresssionParserDefinition = new TestImplLRParserDefinition();
    
}
