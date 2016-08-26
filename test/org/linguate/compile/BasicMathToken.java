/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathToken implements Token
{
    BasicMathGrammarTerminal element;
    String contents;

    public BasicMathToken(BasicMathGrammarTerminal element)
    {
        this(element, null);
    }

    public BasicMathToken(BasicMathGrammarTerminal element, String contents)
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
