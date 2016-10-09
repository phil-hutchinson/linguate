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

    void addAccept(int state, GrammarTerminal accepts);

    void addTransition(int fromState, char inputSymbol, int toState);
    
    DFALexerDefinition getDefinition();
}
