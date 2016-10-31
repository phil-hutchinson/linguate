/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parser;

import java.util.*;
import org.linguate.compile.dft.DFTNode;
import org.linguate.compile.dft.DFTNodeFactory;
import org.linguate.compile.grammar.GrammarProduction;
import org.linguate.compile.lexeme.Lexeme;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathDFTNodeFactory implements DFTNodeFactory
{
    
    @Override
    public DFTNode generateNodeForProduction(GrammarProduction production, List<DFTNode> children)
    {
        BasicMathGrammarNonTerminal testImplElement = (BasicMathGrammarNonTerminal) production.getHead();
        ArrayList<BasicMathDFTNode> typedChildren = new ArrayList<>();
        for(DFTNode child : children)
        {
            typedChildren.add((BasicMathDFTNode)child);
        }
        
        BasicMathDFTNode result = new BasicMathDFTNode(testImplElement, typedChildren);

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
    public DFTNode generateNodeForLexeme(Lexeme element)
    {
        if (element.getContents() == null)
        {
            return new BasicMathDFTNode((BasicMathGrammarSymbol)element.getElement());
        }
        else
        {
            return new BasicMathDFTNode((BasicMathGrammarSymbol)element.getElement(), Integer.parseInt(element.getContents()));
        }
    }
    
}
