/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parser;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathParserDefinition
{
    public final static BasicMathGrammarTerminal NUMERIC_LITERAL = new BasicMathGrammarTerminal("numeric");
    public final static BasicMathGrammarTerminal ADDITION_OPERATOR = new BasicMathGrammarTerminal("+");
    public final static BasicMathGrammarTerminal MULTIPLICATION_OPERATOR = new BasicMathGrammarTerminal("*");
    public final static BasicMathGrammarTerminal LEFT_BRACKET = new BasicMathGrammarTerminal("(");
    public final static BasicMathGrammarTerminal RIGHT_BRACKET = new BasicMathGrammarTerminal(")");
    
    public final static BasicMathGrammarNonTerminal TERM = new BasicMathGrammarNonTerminal("T");
    public final static BasicMathGrammarNonTerminal EXPRESSION = new BasicMathGrammarNonTerminal("E");
    public final static BasicMathGrammarNonTerminal FACTOR = new BasicMathGrammarNonTerminal("F");
    
    public final static BasicMathGrammarProduction RULE_A = new BasicMathGrammarProduction(
            EXPRESSION,
            EXPRESSION,
            ADDITION_OPERATOR,
            TERM
    );
    public final static BasicMathGrammarProduction RULE_B = new BasicMathGrammarProduction(
            EXPRESSION,
            TERM
    );
    public final static BasicMathGrammarProduction RULE_C = new BasicMathGrammarProduction(
            TERM,
            TERM,
            MULTIPLICATION_OPERATOR,
            FACTOR
    );
    public final static BasicMathGrammarProduction RULE_D = new BasicMathGrammarProduction(
            TERM,
            FACTOR
    );
    public final static BasicMathGrammarProduction RULE_E = new BasicMathGrammarProduction(
            FACTOR,
            LEFT_BRACKET,
            EXPRESSION,
            RIGHT_BRACKET
    );
    public final static BasicMathGrammarProduction RULE_F = new BasicMathGrammarProduction(
            FACTOR,
            NUMERIC_LITERAL
    );

    private final static BasicMathLRParserState STATE_0 = new BasicMathLRParserState("0");
    private final static BasicMathLRParserState STATE_1 = new BasicMathLRParserState("1");
    private final static BasicMathLRParserState STATE_2 = new BasicMathLRParserState("2");
    private final static BasicMathLRParserState STATE_3 = new BasicMathLRParserState("3");
    private final static BasicMathLRParserState STATE_4 = new BasicMathLRParserState("4");
    private final static BasicMathLRParserState STATE_5 = new BasicMathLRParserState("5");
    private final static BasicMathLRParserState STATE_6 = new BasicMathLRParserState("6");
    private final static BasicMathLRParserState STATE_7 = new BasicMathLRParserState("7");
    private final static BasicMathLRParserState STATE_8 = new BasicMathLRParserState("8");
    private final static BasicMathLRParserState STATE_9 = new BasicMathLRParserState("9");
    private final static BasicMathLRParserState STATE_10 = new BasicMathLRParserState("10");
    private final static BasicMathLRParserState STATE_11 = new BasicMathLRParserState("11");
    
    private final static BasicMathLRParserAction ACCEPT_INPUT = new BasicMathLRParserAction();
    private final static BasicMathLRParserAction SHIFT_TO_STATE_4 = new BasicMathLRParserAction(STATE_4);
    private final static BasicMathLRParserAction SHIFT_TO_STATE_5 = new BasicMathLRParserAction(STATE_5);
    private final static BasicMathLRParserAction SHIFT_TO_STATE_6 = new BasicMathLRParserAction(STATE_6);
    private final static BasicMathLRParserAction SHIFT_TO_STATE_7 = new BasicMathLRParserAction(STATE_7);
    private final static BasicMathLRParserAction SHIFT_TO_STATE_11 = new BasicMathLRParserAction(STATE_11);
    private final static BasicMathLRParserAction REDUCE_WITH_RULE_A = new BasicMathLRParserAction(RULE_A);
    private final static BasicMathLRParserAction REDUCE_WITH_RULE_B = new BasicMathLRParserAction(RULE_B);
    private final static BasicMathLRParserAction REDUCE_WITH_RULE_C = new BasicMathLRParserAction(RULE_C);
    private final static BasicMathLRParserAction REDUCE_WITH_RULE_D = new BasicMathLRParserAction(RULE_D);
    private final static BasicMathLRParserAction REDUCE_WITH_RULE_E = new BasicMathLRParserAction(RULE_E);
    private final static BasicMathLRParserAction REDUCE_WITH_RULE_F = new BasicMathLRParserAction(RULE_F);
    
    public final static BasicMathLRParserDefinition definition;
    
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
        
        definition = new BasicMathLRParserDefinition(STATE_0);
    }
    //termAndExpresssionParserDefinition = new TestImplLRParserDefinition();
    
}
