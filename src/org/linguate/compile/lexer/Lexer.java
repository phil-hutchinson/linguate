/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexer;

import java.io.Reader;
import org.linguate.compile.lexeme.Lexeme;

/**
 *
 * @author Phil Hutchinson
 */
public interface Lexer
{
    void setLexemeFactory(LexemeFactory lexemeFactory);
    Iterable<? extends Lexeme> lex(String source);
}
