/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexer;

import java.io.Reader;
import org.linguate.compile.lexeme.Lexeme;

/**
 * A Lexer is responsible for converting an input source (a sequence of characters)
 * into a sequence of {@link org.linguate.compile.lexeme.Lexeme Lexemes}. Each lexeme
 * will be categorized as being a specific {@link org.linguate.compile.grammar.GrammarTerminal 
 * GrammarTerminal} and also contain a string that identifies the contents of the lexeme.
 * 
 * A lexer (sometimes known as a scanner) is usually the first major component of 
 * a compiler. It operates on raw input, and its output is usually passed to a parser.
 * 
 * As an example of a lexer's behaviour, it could take the input string 
 * "int j = i + 3;" and separate this into a sequence of lexemes: (keyword, "int"),
 * (identifier, "j"), (assignmentoperator, "="), (additionoperator, "+"),
 * (numericliteral, "3").
 * 
 * Currently, Lexer can lex an input stored in a string. a future ENHANCEMENT will
 * be to support additional input source types.
 * 
 * @author Phil Hutchinson
 */
public interface Lexer
{

    /**
     * Sets the {@link org.linguate.compile.lexer.LexemeFactory LexemeFactory} for
     * the lexer. Must be called before other methods, and should only be called once.
     * (If called additional times, a IllegalStateException should be thrown.)
     * 
     * @param lexemeFactory The LexemeFactory to use this Lexer should use. Cannot
     * be null.
     */
    void setLexemeFactory(LexemeFactory lexemeFactory);

    /**
     * Lex the input source string, using it to generate a sequence of 
     * {@link org.linguate.compile.lexeme.Lexeme Lexemes}.
     * 
     * @param source The input characters to lex.
     * @return A sequence of lexemes.
     */
    Iterable<? extends Lexeme> lex(String source);
    
}
