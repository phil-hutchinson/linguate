/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parser;

import org.linguate.compile.grammar.GrammarNonTerminal;
import org.linguate.compile.grammar.GrammarTerminal;

/**
 * The LRParserState represents the current state of an LR Parser. One of the 
 * basic mechanisms of an LR Parser is that it moves from state to state in 
 * response to input symbols, similar to a DFA. Unlike a DFA, it will also
 * sometimes change state based on stack contents.
 * 
 * <p>The LRParserState also has methods that allows querying for information
 * related to the state, such as the correct action to perform, based on 
 * symbol.
 * @author Phil Hutchinson
 */
public interface LRParserState
{

    /**
     * Gets the name of the state. Will likely be removed.
     * @return name of state.
     */
    String getName();

    /**
     * Returns a {@link org.linguate.compile.parser.LRParserState LRParserState}
     * object describing the next action that should be taken, based on 
     * the next grammar symbol.
     * 
     * @param nextSymbol {@link org.linguate.compile.grammar.GrammarTerminal
     * GrammarTerminal} to provide action for
     * @return the parser action required for the given grammar terminal
     */
    LRParserAction getAction(GrammarTerminal nextSymbol);

    /**
     * Returns the action to perform in the event that there is no more input.
     * @return the parser action required at end of input.
     */
    LRParserAction getEndOfInputAction();

    /**
     * Returns the state to change to if a reduction removes all of the 
     * elements on the parse stack above the current item. The exact grammar
     * rule that the reduction was performed for does not matter, but the
     * head of the rule is used to help determine the resultant states
     * @param productionHead the head of the production rule that triggered the
     * reduction
     * @return the new state the parser should be in post-reduction
     */
    LRParserState getPostReductionState(GrammarNonTerminal productionHead);
}
