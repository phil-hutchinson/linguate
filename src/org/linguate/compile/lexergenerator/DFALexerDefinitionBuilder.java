/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexergenerator;

import org.linguate.compile.grammar.GrammarTerminal;
import org.linguate.compile.lexer.DFALexerDefinition;

/**
 *
 * @author phil
 */
public interface DFALexerDefinitionBuilder {
    void addCharactersToCongruency(int congruencyNumber, Iterable<Character> chars);
    void addTransition(int fromState, int congruencyNumber, int toState);
    void addAccept(int state, GrammarTerminal accepts);
    
    DFALexerDefinition getDefinition();
}
