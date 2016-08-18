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
public class BasicMathParseNodeFactory implements ParseNodeFactory
{
    
    @Override
    public ParseNode CreateInnerNode(GrammarProduction production, List<ParseNode> children)
    {
        TestImplGrammarNonTerminal testImplElement = (TestImplGrammarNonTerminal) production.getHead();
        ArrayList<TestImplParseNode> typedChildren = new ArrayList<>();
        for(ParseNode child : children)
        {
            typedChildren.add((TestImplParseNode)child);
        }
        
        TestImplParseNode result = new TestImplParseNode(testImplElement, typedChildren);

        if (production == BasicMathParserDefinition.RULE_A)
        {
            result.value = typedChildren.get(0).value + typedChildren.get(2).value;
        }
        else if 
            (
                (production == BasicMathParserDefinition.RULE_B)
                || (production == BasicMathParserDefinition.RULE_D)
                || (production == BasicMathParserDefinition.RULE_F)
            )
        {
            result.value = typedChildren.get(0).value;
        }
        else if (production == BasicMathParserDefinition.RULE_C)
        {
            result.value = typedChildren.get(0).value * typedChildren.get(2).value;
        }
        else if (production == BasicMathParserDefinition.RULE_E)
        {
            result.value = typedChildren.get(1).value;
        }
        return result;
    }

    @Override
    public ParseNode CreateLeafNode(Token element)
    {
        if (element.getContents() == null)
        {
            return new TestImplParseNode((TestImplGrammarSymbol)element.getElement());
        }
        else
        {
            return new TestImplParseNode((TestImplGrammarSymbol)element.getElement(), Integer.parseInt(element.getContents()));
        }
    }
    
}
