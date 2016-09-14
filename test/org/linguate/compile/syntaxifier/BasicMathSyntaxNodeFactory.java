/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

import java.util.HashMap;
import org.linguate.compile.syntaxtree.SyntaxNode;
import org.linguate.compile.syntaxtree.SyntaxNodeFactory;
import org.linguate.compile.token.Token;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathSyntaxNodeFactory implements SyntaxNodeFactory
{
    private HashMap<Token, BasicMathSyntaxElement> tokenToElementMap = new HashMap<>();
    
    @Override
    public BasicMathSyntaxNode CreateNodeForElement(SyntaxElement syntaxElement)
    {
        return new BasicMathSyntaxNode((BasicMathSyntaxElement)syntaxElement);
    }

    @Override
    public BasicMathSyntaxNode CreateNodeForToken(Token token)
    {
        BasicMathSyntaxElement element;
        if (!tokenToElementMap.containsKey(token))
        {
            element = new BasicMathSyntaxElement(token.getElement().getName());
            tokenToElementMap.put(token, element);
        }
        else
        {
            element = tokenToElementMap.get(token);
        }
        if (token.getElement() == BasicMathSyntaxDefinition.NUMERIC_LITERAL)
        {
            int value = Integer.parseInt(token.getContents());
            return new BasicMathSyntaxNode(element, value);
        }
        else
        {
            return new BasicMathSyntaxNode(element);
        }
    }
    
}
