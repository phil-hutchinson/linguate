/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexer;

import java.util.Set;
import org.linguate.compile.grammar.GrammarTerminal;

/**
 * a DFALexerDefinition defines the Deterministic Finite Automaton that is used
 * by a {@link org.linguate.compile.lexer.DFALexer DFALexer}.
 * 
 * <p>A Deterministic Finite Automaton consists of states, each of which can
 * be an accepting or non-accepting state. An accepting state accepts input
 * as valid if no further input is added. One of the states is also the start 
 * state: this is always state 0. It also consists of transitions from one state
 * to another, based on what the next character of input is.
 * 
 * <p>The DFA used by the DFALexerDefinition differs from the traditional definition
 * of a DFA because it allows for distinct accept states (unlike a traditional DFA).
 * This allows a DFA to not only determine whether an input sequence represents a valid 
 * grammar terminal, but also which grammar terminal it represents.
 * 
 * <p>The DFALexerDefinition also introduces the concept of character congruency. Two
 * characters are considered congruent if they behave identically with respect to 
 * all transitions - i.e. for two characters x and y, if for every start state of 
 * a transition, both x and y result in the same end state, then x and y are congruent.
 * The purpose of defining congruent characters is to reduce the size of the
 * DFADefinition.
 * 
 * <p>The start state is defined by the constant START_STATE, and must always be valid. 
 * 
 * There are two ways of determining transitions. Both methods should always
 * result in the same value:
 * <ol>
 * <li>By directly calling {@link #getNextState(int, char) getNextState(int,char)}
 *  specifying the start state and input character.
 * <li>By first calling {@link #getCharacterCongruency(char) getCharacterCongruency}
 * to get the group the character belongs to, and then calling {@link #getNextState(int, int) 
 * getNextState(int, int)} specifying the start state and the congruency group 
 * obtained from the first call.
 * </ol>
 * @author Phil Hutchinson
 */
public interface  DFALexerDefinition
{
    
    /**
     * The initial state of the DFA.
     */
    final int START_STATE = 0;
    /** 
     * Pseudo-state used when there is no valid transition from a state for an input.
     * A transition to DEAD_STATE indicates that the current input is invalid,
     * and no subsequent sequence of characters would lead to an accepting state - 
     * i.e. the result of any transition from the dead state will be the dead state.
     * 
     */
    final int DEAD_STATE = -1;
    
    /**
     * Pseudo-congruency group used when a congruency is requested for a character
     * but none exists.
     */
    final int NO_CONGRUENCY_FOR_CHARACTER = -1;

    /**
     * Identifies the appropriate transition based on a starting state and input
     * character.
     * 
     * @param currentState The state before the transition. Should be the {@link 
     * #START_STATE}, or a state previously obtained from one of the getNextState
     * overloads.
     * @param character The next character of input
     * @return The resulting state, or {@link #DEAD_STATE DEAD_STATE} if no transition
     * exists from the given state for the given character
     */
    int getNextState(int currentState, char character);

    /**
     * Returns the group of congruent characters (as an integer) that the 
     * given character belongs to.
     * 
     * @param character The character to obtain the congruency group for
     * @return The congruency group, or {@link #NO_CONGRUENCY_FOR_CHARACTER
     * NO_CONGRENCY_FOR_CHARACTER} if the character is not part of any congruency
     * group.
     */
    int getCharacterCongruency(char character);
    
    /**
     * Identifies the appropriate transition based on a starting state and input
     * character congruency group.
     * 
     * <p>This method should always return {@link #DEAD_STATE DEAD_STATE} if passed {@link
     * #NO_CONGRUENCY_FOR_CHARACTER NO_CONGRUENCY_FOR_CHARACTER} as the character
     * congruency.
     * 
     * @param currentState The state before the transition. Should be the {@link 
     * #START_STATE}, or a state previously obtained from one of the getNextState
     * overloads.
     * @param characterCongruency The character congruency group, obtained by calling
     * {@link #getCharacterCongruency(char) getCharacterCongruency}
     * @return The resulting state, or {@link #DEAD_STATE DEAD_STATE} if no transition
     * exists from the given state for the given character
     */
    int getNextState(int currentState, int characterCongruency);

    /**
     * Returns the {@link org.linguate.compile.grammar.GrammarTerminal GrammarTerminal}
     * accepted by the current state, or null if the state is a non-accepting state.
     * 
     * <p>Any GrammarTerminal that is returned by this method (for any value of state)
     * should be included in the set of GrammarTerminal that is returned by {@link 
     * #getAllAcceptTerminals() getAllAcceptTerminals}.
     * @param state The state the GrammarTerminal is being requested for.
     * @return The GrammarTerminal the state accepts, or null if it is non-accepting.
     */
    GrammarTerminal accepts(int state);

    /**
     * Returns a set of all grammar terminals that are accepted by this DFA.
     * @return Grammar Terminals accepted by this DFA
     */
    Set<GrammarTerminal> getAllAcceptTerminals();
}
