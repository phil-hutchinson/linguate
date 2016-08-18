/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile;

import java.util.*;

/**
 *
 * @author Phil Hutchinson
 */
public class TestImplParseNode implements ParseNode
{
    private TestImplGrammarSymbol element;
    public int value;
    public final ArrayList<TestImplParseNode> children = new ArrayList<>();

    public TestImplParseNode(TestImplGrammarSymbol element)
    {
        this(element, null);
    }
    
    public TestImplParseNode(TestImplGrammarSymbol element, int value)
    {
        this(element, null);
        this.value = value;
    }
    
    public TestImplParseNode(TestImplGrammarSymbol element, List<TestImplParseNode> children)
    {
        this.element = element;
        if (children != null)
        {
            children.forEach(this.children::add);
        }
    }
    
    
    @Override
    public GrammarSymbol getElement()
    {
        return element;
    }
    
}
