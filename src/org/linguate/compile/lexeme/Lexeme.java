/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexeme;

import org.linguate.compile.grammar.GrammarTerminal;

/**
 * A lexeme is the output of a lexer and the input of a parser, and is the smallest
 * syntactic unit. Examples of lexemes include keywords, identifiers and literals,
 * 
 * @author Phil Hutchinson
 */
public interface Lexeme
{

    /**
     * Returns the grammar terminal associated with this lexeme.
     * @return the grammar terminal this lexeme represents
     */
    GrammarTerminal getElement();

    /**
     * Returns the contents of the lexeme. This will usually be the
     * set of characters in the input stream that were used to create it.
     * @return the contents of the lexeme
     */
    String getContents();
}
