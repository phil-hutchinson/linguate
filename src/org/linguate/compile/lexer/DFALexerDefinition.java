/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexer;

import java.util.Set;
import org.linguate.compile.grammar.GrammarTerminal;

/**
 *
 * @author Phil Hutchinson
 */
public interface  DFALexerDefinition
{
    final int DEAD_STATE = -1;
    int getNextState(int currentState, char character);
    int getCharacterCongruency(char character);
    int getNextState(int currentState, int characterCongruency);
    GrammarTerminal accepts(int state);
    Set<GrammarTerminal> getAllAcceptTerminals();
}
