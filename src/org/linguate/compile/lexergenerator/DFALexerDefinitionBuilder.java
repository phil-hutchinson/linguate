/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexergenerator;

import org.linguate.compile.grammar.GrammarTerminal;
import org.linguate.compile.lexer.DFALexerDefinition;

/**
 * A DFALexerDefinitionBuilder is used to build a {@link org.linguate.compile.lexer.DFALexerDefinition
 * DFALexerDefinition}. It has a building phase (where it begins), and a completed phase that 
 * doesn't allow any further changes. At this point, the DFALexerDefinitionBuilder has 
 * served its purpose and is no further use.
 * 
 * <p>During the building phase, the {@link #addCharactersToCongruency(int, java.lang.Iterable) 
 * addCharactersToCongruency}, {@link #addTransition(int, int, int) addTransition},
 * and {@link #addAccept(int, org.linguate.compile.grammar.GrammarTerminal) addAccept}
 * methods can be used to build the definition.
 * 
 * <p>The {@link #getDefinition() getDefinition} method is used to generate the method, and moves
 * the object to the completed phase, at which point the DFALexerDefinitionBuilder's task
 * is complete.
 * 
 * <p>The DFALexerDefinitionBuilder uses congruency groups - for a complete discussion
 * of congruency groups, see the {@link org.linguate.compile.lexer.DFALexerDefinition 
 * DFALexerDefinition} documentation.
 * 
 * @author Phil Hutchinson
 */
public interface DFALexerDefinitionBuilder {

    /**
     * Add characters to a congruency group. A congruency group is a group of 
     * characters whose behaviour is identical with respect to the DFA. (i.e.
     * they cause the exact same set of transitions.)
     * @param congruencyNumber An identifier number for the congruency group. Must
     * be greater than or equal to zero.
     * @param chars The collection of characters to add to the group.
     */
    void addCharactersToCongruency(int congruencyNumber, Iterable<Character> chars);

    /**
     * Add a transition to the DFA being built.
     * 
     * @param fromState The pre-transition state number
     * @param congruencyNumber The number of the congruency group. Must have been 
     * previously passed to {@link #addCharactersToCongruency(int, java.lang.Iterable) 
     * addCharactersToCongruency}.
     * @param toState The post-transition state number
     */
    void addTransition(int fromState, int congruencyNumber, int toState);

    /**
     * Set the state as an accept state, specifying the {@link org.linguate.compile.grammar.GrammarTerminal
     * GrammarTerminal} that it accepts.
     * 
     * <p>If called more than once for the same state, the previous accepting GrammarTerminal 
     * will be overwritten.
     * @param state The state that will accept the GrammarTerminal. Must be non-negative.
     * @param accepts The accepted GrammarTerminal. Must be non-null.
     */
    void addAccept(int state, GrammarTerminal accepts);
    
    /**
     * Generates the definition specified by previous calls to other methods. After
     * calling this method, no further method calls can be made.
     * 
     * @return The definition for a DFA-based Lexer.
     */
    DFALexerDefinition getDefinition();
}
