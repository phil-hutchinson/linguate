/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parsergenerator;

import org.linguate.compile.grammar.GrammarNonTerminal;
import org.linguate.compile.grammar.GrammarProduction;
import org.linguate.compile.grammar.GrammarTerminal;
import org.linguate.compile.parser.LRParserDefinition;

/**
 * A LRParserDefinitionBuilder is used to build a {@link org.linguate.compile.parser.LRParserDefinition
 * LRParserDefinition}. It has a building phase (where it begins), and a completed phase that 
 * doesn't allow any further changes. At this point, the DFALexerDefinitionBuilder has 
 * served its purpose and is of no further use.
 * 
 * <p>During the building phase, the {@link #addState() addState}, {@link 
 * #addShift(int, org.linguate.compile.grammar.GrammarTerminal, int) addShift},
 * {@link #addReduction(int, org.linguate.compile.grammar.GrammarTerminal, org.linguate.compile.grammar.GrammarProduction) 
 * addReduction} and {@link #addPostReductionState(int, org.linguate.compile.grammar.GrammarNonTerminal, int) 
 * addPostRecutionState} methods can be used to build the definition.
 * 
 * <p>The {@link #getDefinition() getDefinition} method is used to generate the method, and moves
 * the object to the completed phase, at which point the LRParserDefinitionBuilder's task
 * is complete, and it is of no further use.

 * @author Phil Hutchinson
 */
public interface LRParserDefinitionBuilder {

    /**
     * Adds a state to the parser definition. The first time that addState is
     * called for an instance of LRParserDefinitionBuilder, it will return the
     * start state.
     * 
     * @return the number of the new state that has been added
     */
    int addState();

    /**
     * Adds a rule for a shift, based on the current state and a specific GrammarTerminal.
     * 
     * Throws if a shift or reduce rule has already been added for the
     * fromState/nextSymbol combination.
     * 
     * Both states must have been previously created using {@link #addState() 
     * addState}: otherwise this method throws.
     * 
     * @param fromState the existing state at the top of the parser stack 
     * @param nextSymbol the next grammar terminal in the input
     * @param toState the new state after the shift
     */
    void addShift(int fromState, GrammarTerminal nextSymbol, int toState);

    /**
     * Adds a rule for a reduction, based on the current state and a specific
     * GrammarTerminal.
     * 
     * Throws if a shift or reduce rule has already been added for the 
     * state/nextSymbol combination.
     * 
     * The state must have been previously created using {@link #addState() 
     * addState}: otherwise this method throws.
     * 
     * @param state the existing state at the top of the parser stack
     * @param nextSymbol the next grammar terminal in the input
     * @param production the production rule used to perform the reduction
     */
    void addReduction(int state, GrammarTerminal nextSymbol, GrammarProduction production);

    /**
     * Adds a rule used to determine the state after a reduction.
     * 
     * Throws if a post reduction rule has already been added for the same
     * stackState/productionHead combo.
     * 
     * Both states must have been previously created using {@link #addState() 
     * addState}: otherwise this method throws.
     * 
     * @param stackState the state on top of the parser stack after the items that
     * are to be reduced have been removed
     * @param productionHead the head of the production rule used to perform
     * the reduction
     * @param newState the resultant state
     */
    void addPostReductionState(int stackState, GrammarNonTerminal productionHead, int newState);
    
    /**
     * Create a {@link org.linguate.compile.parser.LRParserDefinition LRParserDefinition}
     * based on the rules that have been previously added.
     * 
     * After getDefinition() has been called, no further rules can be added, and
     * the returned LRParserDefinition should be immutable.
     * 
     * Throws if {@link #addState() addState} has not been called at least once.
     * 
     * @return a definition that can be used by a {@link org.linguate.compile.parser.LRParser
     * LRParser} to parse sequences of {@link org.linguate.compile.grammar.GrammarTerminal
     * GrammarTerminals}
     */
    LRParserDefinition getDefinition();
}
