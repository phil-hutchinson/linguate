/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parser;

import org.linguate.compile.grammar.GrammarNonTerminal;
import org.linguate.compile.grammar.GrammarProduction;
import org.linguate.compile.grammar.GrammarTerminal;

/**
 * An LRParserDefinition represents a set of rules that are used to drive an
 * {@link org.linguate.compile.parser.LRParser LRParser}. Generally, the same
 * parser can be used for any LR-compliant grammar - it is only the parser
 * definition that changes.
 * 
 * <p>The parser definition provides a set of states. As is typical for an LR Parser,
 * input symbols cause transitions from one state to another (shifts). These items
 * exist on a stack, and are collected by applying grammar productions (reductions.)
 * All of these actions take place in a deterministic fashion, and the parser
 * definition provides the exact rules as to when shifts and reductions should take
 * place.
 * 
 * @author Phil Hutchinson
 */
public interface LRParserDefinition
{
    /**
     * ActionType represents the possible actions a parser can take.
     * <p>The Accept action indicates that the parser should accept the current
     * input. This should only happen at then end of input.
     * <p>The Shift action indicates that the parser should shift the next
     * {@link org.linguate.compile.grammar.GrammarTerminal GrammarTerminal} onto 
     * the stack, and then what {@link org.linguate.compile.parser.LRParserState 
     * LRParserState} to change to.
     * <p>The Reduce action indicates that the parser should reduce the symbols
     * at the top of the stack (i.e. replace a series of symbols with a non-terminal)
     */
    enum ActionType
    {
        Accept,
        Shift,
        Reduce,
        Reject,
    }

    /**
     * The initial state of the parser definition.
     */
    final int START_STATE = 0;
    final int NO_POST_REDUCTION_STATE = -1;
    
    /**
     * Gets the ActionType of this LRParserAction
     * @param state The current state of the parser
     * @param nextSymbol The next grammar terminal in the input.
     * @return the ActionType, representing what action the parser should take.
     */
    ActionType getActionType(int state, GrammarTerminal nextSymbol);
    
    /**
     * Gets an integer representing the state to move to during a shift operation. 
     * This method should only be called when {@link #getActionType(int, 
     * org.linguate.compile.grammar.GrammarTerminal) getActionType} returns 
     * ActionType.Shift
     * @param state The current state of the parser
     * @param nextSymbol The next grammar terminal in the input.
     * @return the state number the parser will be in after performing shift.
     */
    int getShiftState(int state, GrammarTerminal nextSymbol);
    
    /**
     * Gets the {@link org.linguate.compile.grammar.GrammarProduction GrammarProduction}
     * that specifies the details for the reduction. Specifically, the parser will replace
     * the top n symbols on the stack with the head of the production rule, where n
     * is the number of items in the body of the production rule. This method should 
     * only be called when {@link #getAction() getAction} returns ActionType.Reduce
     * @param state The current state of the parser
     * @param nextSymbol The next grammar terminal in the input.
     * @return the grammar production to use as a basis for reduction
     */
    GrammarProduction getReduceRule(int state, GrammarTerminal nextSymbol);
    
    /**
     * Returns the state to change to if a reduction removes all of the 
     * elements on the parse stack above the current item. The exact grammar
     * rule that the reduction was performed for does not matter, but the
     * head of the rule is used to help determine the resultant states
     * @param state The state of the stack item directly preceding the items
     * that are being reduced.
     * @param productionHead the head of the production rule that triggered the
     * reduction, which will now replace its children on the parser stack.
     * @return the new state number the parser should be in post-reduction
     */
    int getPostReductionState(int state, GrammarNonTerminal productionHead);
}
