/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
