/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.lexeme;

import org.linguate.compile.grammar.GrammarTerminal;

/**
 * A lexeme is an individual "word". It is the output of a lexer, which turns a 
 * stream of characters into a stream of lexemes, and the input of a parser,
 * which turns a stream of lexemes into a parse tree.
 * 
 * @author Phil Hutchinson
 */
public interface Lexeme
{

    /**
     * Returns the grammar terminal associated with this lexeme.
     * @return The grammar terminal this lexeme represents
     */
    GrammarTerminal getElement();

    /**
     * Returns the contents of the lexeme. This will usually be the
     * set of characters in the input stream that were used to create it.
     * @return The contents of the lexeme
     */
    String getContents();
}
