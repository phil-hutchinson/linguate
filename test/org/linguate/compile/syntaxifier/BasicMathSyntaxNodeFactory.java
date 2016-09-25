/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

import java.util.HashMap;
import org.linguate.compile.syntaxtree.SyntaxNode;
import org.linguate.compile.syntaxtree.SyntaxNodeFactory;
import org.linguate.compile.lexeme.Lexeme;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathSyntaxNodeFactory implements SyntaxNodeFactory
{
    @Override
    public BasicMathSyntaxNode CreateNodeForElement(SyntaxElement syntaxElement)
    {
        return new BasicMathSyntaxNode((BasicMathSyntaxElement)syntaxElement);
    }

    @Override
    public BasicMathSyntaxNode CreateNodeForLexeme(Lexeme lexeme)
    {
        BasicMathSyntaxElement element;
        element = new BasicMathSyntaxElement(lexeme.getElement().getName());
        if (lexeme.getElement() == BasicMathSyntaxDefinition.NUMERIC_LITERAL)
        {
            int value = Integer.parseInt(lexeme.getContents());
            return new BasicMathSyntaxNode(element, value);
        }
        else
        {
            return new BasicMathSyntaxNode(element);
        }
    }
    
}
