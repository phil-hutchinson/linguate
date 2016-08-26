/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathGrammarSymbol implements GrammarSymbol
{
    private String name;

    public BasicMathGrammarSymbol(String name)
    {
        this.name = name;
    }
    
    @Override
    public String getName()
    {
        return name;
    }
    
}
