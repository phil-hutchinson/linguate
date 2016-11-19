/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parser;

import org.linguate.compile.grammar.GrammarProduction;

/**
 * A LRParserAction is a component within the definition tables for an LR Parser,
 * and represents an action to take based on the current parser state and next
 * grammar symbol.
 * 
 * @author Phil Hutchinson
 */
public interface LRParserAction 
{
    
    /**
     * ActionType represents what type of action a parser should take.
     * <p>The Accept action indicates that the parser should accept the current
     * input.
     * <p>The Shift action indicates that the parser should shift the next
     * {@link org.linguate.compile.grammar.GrammarTerminal GrammarTerminal} onto 
     * the stack, and then what {@link org.linguate.compile.parser.LRParserState 
     * LRParserState} to change to.
     * <p>The Reduce action indicates that the parser should reduce the symbols
     * at the top of the stack (i.e. replace a series of symbols with a non-terminal)
     */
    public enum ActionType
    {
        Accept,
        Shift,
        Reduce,
    }
    
    /**
     * Gets the ActionType of this LRParserAction
     * @return the ActionType, representing what action the parser should take.
     */
    public ActionType getAction();

    /**
     * Gets the {@link org.linguate.compile.parser.LRParserState LRParserState} to
     * move to after a shift operation. This method should only be called when 
     * {@link #getAction() getAction} returns ActionType.Shift
     * @return the state the parser will be in after performing shift.
     */
    public LRParserState getShiftState();

    /**
     * Gets the {@link org.linguate.compile.grammar.GrammarProduction GrammarProduction}
     * that specifies the details for the reduction. Specifically, the parser will replace
     * the top n symbols on the stack with the head of the production rule, where n
     * is the number of items in the body of the production rule. This method should 
     * only be called when {@link #getAction() getAction} returns ActionType.Reduce
     * @return the grammar production to use as a basis for reduction
     */
    public GrammarProduction getReduceRule();
}
