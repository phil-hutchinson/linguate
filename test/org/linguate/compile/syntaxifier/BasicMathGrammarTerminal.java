/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

import org.linguate.compile.grammar.GrammarTerminal;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathGrammarTerminal implements GrammarTerminal
{
    String name;

    public BasicMathGrammarTerminal(String name)
    {
        this.name = name;
    }
    
    @Override
    public String getName()
    {
        return name;
    }
}
