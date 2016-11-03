/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexer;

import org.linguate.compile.grammar.GrammarTerminal;
import org.linguate.compile.lexeme.Lexeme;

/**
 * A LesemeFactory is responsible for generating the {@link org.linguate.compile.lexeme.Lexeme 
 * Lexeme}, allowing the lexer itself to offload this process, as it does not 
 * need to be aware of exactly which Lexeme-implementing class will be used.
 * 
 * @author Phil Hutchinson
 */
public interface LexemeFactory
{

    /**
     * Returns a new {@link org.linguate.compile.lexeme.Lexeme Lexeme} for a 
     * grammar terminal and input string.
     * 
     * The element returned by the {@link org.linguate.compile.lexeme.Lexeme#getElement() 
     * getElement} method of the Lexeme will generally be the same as the element
     * passed into the method, but this is not a requirement.
     * 
     * @param terminal The {@link org.linguate.compile.grammar.GrammarTerminal
     * GrammarTerminal} to create the lexeme for.
     * @param contents The sequence of characters in the input file that was 
     * matched to make the 
     * @return The generated lexeme.
     */
    Lexeme createLexeme(GrammarTerminal terminal, String contents);
}
