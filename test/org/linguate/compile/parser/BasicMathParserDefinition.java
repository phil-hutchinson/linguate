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

    public final static BasicMathLRParserDefinition definition = new BasicMathLRParserDefinition();
    
    private final static int STATE_0 = definition.addState();
    private final static int STATE_1 = definition.addState();
    private final static int STATE_2 = definition.addState();
    private final static int STATE_3 = definition.addState();
    private final static int STATE_4 = definition.addState();
    private final static int STATE_5 = definition.addState();
    private final static int STATE_6 = definition.addState();
    private final static int STATE_7 = definition.addState();
    private final static int STATE_8 = definition.addState();
    private final static int STATE_9 = definition.addState();
    private final static int STATE_10 = definition.addState();
    private final static int STATE_11 = definition.addState();
    
//    private final static BasicMathLRParserAction ACCEPT_INPUT = new BasicMathLRParserAction();
//    private final static BasicMathLRParserAction SHIFT_TO_STATE_4 = new BasicMathLRParserAction(STATE_4);
//    private final static BasicMathLRParserAction SHIFT_TO_STATE_5 = new BasicMathLRParserAction(STATE_5);
//    private final static BasicMathLRParserAction SHIFT_TO_STATE_6 = new BasicMathLRParserAction(STATE_6);
//    private final static BasicMathLRParserAction SHIFT_TO_STATE_7 = new BasicMathLRParserAction(STATE_7);
//    private final static BasicMathLRParserAction SHIFT_TO_STATE_11 = new BasicMathLRParserAction(STATE_11);
//    private final static BasicMathLRParserAction REDUCE_WITH_RULE_A = new BasicMathLRParserAction(RULE_A);
//    private final static BasicMathLRParserAction REDUCE_WITH_RULE_B = new BasicMathLRParserAction(RULE_B);
//    private final static BasicMathLRParserAction REDUCE_WITH_RULE_C = new BasicMathLRParserAction(RULE_C);
//    private final static BasicMathLRParserAction REDUCE_WITH_RULE_D = new BasicMathLRParserAction(RULE_D);
//    private final static BasicMathLRParserAction REDUCE_WITH_RULE_E = new BasicMathLRParserAction(RULE_E);
//    private final static BasicMathLRParserAction REDUCE_WITH_RULE_F = new BasicMathLRParserAction(RULE_F);
    
    
    static 
    {
//        STATE_0.actions.put(NUMERIC_LITERAL, SHIFT_TO_STATE_5);
//        STATE_0.actions.put(LEFT_BRACKET, SHIFT_TO_STATE_4);
//        STATE_0.postReductionStates.put(EXPRESSION, STATE_1);
//        STATE_0.postReductionStates.put(TERM, STATE_2);
//        STATE_0.postReductionStates.put(FACTOR, STATE_3);
        definition.addShift(STATE_0, NUMERIC_LITERAL, STATE_5);
        definition.addShift(STATE_0, LEFT_BRACKET, STATE_4);
        definition.addPostReductionState(STATE_0, EXPRESSION, STATE_1);
        definition.addPostReductionState(STATE_0, TERM, STATE_2);
        definition.addPostReductionState(STATE_0, FACTOR, STATE_3);
        
//        STATE_1.actions.put(ADDITION_OPERATOR, SHIFT_TO_STATE_6);
//        STATE_1.endOfInputAction = ACCEPT_INPUT;
        definition.addShift(STATE_1, ADDITION_OPERATOR, STATE_6);
        definition.addAccept(STATE_1, null);
        
//        STATE_2.actions.put(ADDITION_OPERATOR, REDUCE_WITH_RULE_B);
//        STATE_2.actions.put(MULTIPLICATION_OPERATOR, SHIFT_TO_STATE_7);
//        STATE_2.actions.put(RIGHT_BRACKET, REDUCE_WITH_RULE_B);
//        STATE_2.endOfInputAction = REDUCE_WITH_RULE_B;
        definition.addReduce(STATE_2, ADDITION_OPERATOR, RULE_B);
        definition.addShift(STATE_2, MULTIPLICATION_OPERATOR, STATE_7);
        definition.addReduce(STATE_2, RIGHT_BRACKET, RULE_B);
        definition.addReduce(STATE_2, null, RULE_B);
        
//        STATE_3.actions.put(ADDITION_OPERATOR, REDUCE_WITH_RULE_D);
//        STATE_3.actions.put(MULTIPLICATION_OPERATOR, REDUCE_WITH_RULE_D);
//        STATE_3.actions.put(RIGHT_BRACKET, REDUCE_WITH_RULE_D);
//        STATE_3.endOfInputAction = REDUCE_WITH_RULE_D;
        definition.addReduce(STATE_3, ADDITION_OPERATOR, RULE_D);
        definition.addReduce(STATE_3, MULTIPLICATION_OPERATOR, RULE_D);
        definition.addReduce(STATE_3, RIGHT_BRACKET, RULE_D);
        definition.addReduce(STATE_3, null, RULE_D);
        
//        STATE_4.actions.put(NUMERIC_LITERAL, SHIFT_TO_STATE_5);
//        STATE_4.actions.put(LEFT_BRACKET, SHIFT_TO_STATE_4);
//        STATE_4.postReductionStates.put(EXPRESSION, STATE_8);
//        STATE_4.postReductionStates.put(TERM, STATE_2);
//        STATE_4.postReductionStates.put(FACTOR, STATE_3);
        definition.addShift(STATE_4, NUMERIC_LITERAL, STATE_5);
        definition.addShift(STATE_4, LEFT_BRACKET, STATE_4);
        definition.addPostReductionState(STATE_4, EXPRESSION, STATE_8);
        definition.addPostReductionState(STATE_4, TERM, STATE_2);
        definition.addPostReductionState(STATE_4, FACTOR, STATE_3);
        
//        STATE_5.actions.put(ADDITION_OPERATOR, REDUCE_WITH_RULE_F);
//        STATE_5.actions.put(MULTIPLICATION_OPERATOR, REDUCE_WITH_RULE_F);
//        STATE_5.actions.put(RIGHT_BRACKET, REDUCE_WITH_RULE_F);
//        STATE_5.endOfInputAction = REDUCE_WITH_RULE_F;
        definition.addReduce(STATE_5, ADDITION_OPERATOR, RULE_F);
        definition.addReduce(STATE_5, MULTIPLICATION_OPERATOR, RULE_F);
        definition.addReduce(STATE_5, RIGHT_BRACKET, RULE_F);
        definition.addReduce(STATE_5, null, RULE_F);

//        STATE_6.actions.put(NUMERIC_LITERAL, SHIFT_TO_STATE_5);
//        STATE_6.actions.put(LEFT_BRACKET, SHIFT_TO_STATE_4);
//        STATE_6.postReductionStates.put(TERM, STATE_9);
//        STATE_6.postReductionStates.put(FACTOR, STATE_3);
        definition.addShift(STATE_6, NUMERIC_LITERAL, STATE_5);
        definition.addShift(STATE_6, LEFT_BRACKET, STATE_4);
        definition.addPostReductionState(STATE_6, TERM, STATE_9);
        definition.addPostReductionState(STATE_6, FACTOR, STATE_3);

//        STATE_7.actions.put(NUMERIC_LITERAL, SHIFT_TO_STATE_5);
//        STATE_7.actions.put(LEFT_BRACKET, SHIFT_TO_STATE_4);
//        STATE_7.postReductionStates.put(FACTOR, STATE_10);
        definition.addShift(STATE_7, NUMERIC_LITERAL, STATE_5);
        definition.addShift(STATE_7, LEFT_BRACKET, STATE_4);
        definition.addPostReductionState(STATE_7, FACTOR, STATE_10);

//        STATE_8.actions.put(ADDITION_OPERATOR, SHIFT_TO_STATE_6);
//        STATE_8.actions.put(RIGHT_BRACKET, SHIFT_TO_STATE_11);
        definition.addShift(STATE_8, ADDITION_OPERATOR, STATE_6);
        definition.addShift(STATE_8, RIGHT_BRACKET, STATE_11);

//        STATE_9.actions.put(ADDITION_OPERATOR, REDUCE_WITH_RULE_A);
//        STATE_9.actions.put(MULTIPLICATION_OPERATOR, SHIFT_TO_STATE_7);
//        STATE_9.actions.put(RIGHT_BRACKET, REDUCE_WITH_RULE_A);
//        STATE_9.endOfInputAction = REDUCE_WITH_RULE_A;
        definition.addReduce(STATE_9, ADDITION_OPERATOR, RULE_A);
        definition.addShift(STATE_9, MULTIPLICATION_OPERATOR, STATE_7);
        definition.addReduce(STATE_9, RIGHT_BRACKET, RULE_A);
        definition.addReduce(STATE_9, null, RULE_A);

//        STATE_10.actions.put(ADDITION_OPERATOR, REDUCE_WITH_RULE_C);
//        STATE_10.actions.put(MULTIPLICATION_OPERATOR, REDUCE_WITH_RULE_C);
//        STATE_10.actions.put(RIGHT_BRACKET, REDUCE_WITH_RULE_C);
//        STATE_10.endOfInputAction = REDUCE_WITH_RULE_C;
        definition.addReduce(STATE_10, ADDITION_OPERATOR, RULE_C);
        definition.addReduce(STATE_10, MULTIPLICATION_OPERATOR, RULE_C);
        definition.addReduce(STATE_10, RIGHT_BRACKET, RULE_C);
        definition.addReduce(STATE_10, null, RULE_C);
        
//        STATE_11.actions.put(ADDITION_OPERATOR, REDUCE_WITH_RULE_E);
//        STATE_11.actions.put(MULTIPLICATION_OPERATOR, REDUCE_WITH_RULE_E);
//        STATE_11.actions.put(RIGHT_BRACKET, REDUCE_WITH_RULE_E);
//        STATE_11.endOfInputAction = REDUCE_WITH_RULE_E;
        definition.addReduce(STATE_11, ADDITION_OPERATOR, RULE_E);
        definition.addReduce(STATE_11, MULTIPLICATION_OPERATOR, RULE_E);
        definition.addReduce(STATE_11, RIGHT_BRACKET, RULE_E);
        definition.addReduce(STATE_11, null, RULE_E);
        
    }
    //termAndExpresssionParserDefinition = new TestImplLRParserDefinition();
    
}
