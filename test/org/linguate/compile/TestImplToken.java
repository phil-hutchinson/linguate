/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile;

/**
 *
 * @author Phil Hutchinson
 */
public class TestImplToken implements Token
{
    TestImplGrammarTerminal element;
    String contents;

    public TestImplToken(TestImplGrammarTerminal element)
    {
        this(element, null);
    }

    public TestImplToken(TestImplGrammarTerminal element, String contents)
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
