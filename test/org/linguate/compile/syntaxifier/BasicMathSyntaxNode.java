/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.linguate.compile.syntaxtree.SyntaxNode;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathSyntaxNode implements SyntaxNode
{
    BasicMathSyntaxElement element;
    int value;
    ArrayList<BasicMathSyntaxNode> children = new ArrayList<BasicMathSyntaxNode>();

    public BasicMathSyntaxNode(BasicMathSyntaxElement element)
    {
        this.element = element;
    }

    public BasicMathSyntaxNode(BasicMathSyntaxElement element, int value)
    {
        this.element = element;
        this.value = value;
    }
    
    public int getValue()
    {
        return value;
    }
    
    @Override
    public BasicMathSyntaxElement getElement()
    {
        return element;
    }

    @Override
    public List<? extends BasicMathSyntaxNode> getChildren()
    {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void setChildren(List<? extends SyntaxNode> children)
    {
        this.children.clear();
        for(SyntaxNode child : children)
        {
           if (child instanceof BasicMathSyntaxNode)
           {
               this.children.add((BasicMathSyntaxNode)child);
           }
           else
           {
               throw new IllegalArgumentException("BasicMathSyntaxNode children must be instances of BasicMathSyntaxNode.");
           }
        }
    }
    
}
