/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexer;

import org.linguate.compile.grammar.GrammarTerminal;
import org.linguate.compile.lexeme.Lexeme;

/**
 *
 * @author Phil Hutchinson
 */
public interface LexemeFactory
{
        Lexeme CreateLexeme(GrammarTerminal terminal, String contents);
}
