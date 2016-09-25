/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parser;

import org.linguate.compile.grammar.GrammarTerminal;
import org.linguate.compile.lexeme.Lexeme;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathLexeme implements Lexeme
{
    BasicMathGrammarTerminal element;
    String contents;

    public BasicMathLexeme(BasicMathGrammarTerminal element)
    {
        this(element, null);
    }

    public BasicMathLexeme(BasicMathGrammarTerminal element, String contents)
    {
        this.element = element;
        this.contents = contents;
    }
            
    @Override
    public GrammarTerminal getElement()
    {
        return element;
    }

    @Override
    public String getContents()
    {
        return contents;
    }
    
}
