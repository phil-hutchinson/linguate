/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathSyntaxRuleComponent implements SyntaxRuleComponent
{
    private BasicMathSyntaxElement element;
    private int position;
    ComponentType componentType;
    
    public BasicMathSyntaxRuleComponent(BasicMathSyntaxElement element)
    {
        this.element = element;
        componentType = ComponentType.NewNode;
    }

    public BasicMathSyntaxRuleComponent(int position)
    {
        this.position = position;
        componentType = ComponentType.ExistingNodeByPosition;
    }
    
    @Override
    public ComponentType getComponentType()
    {
        return componentType;
    }
    
    @Override
    public SyntaxElement getNewNodeElement()
    {
        if (componentType != ComponentType.NewNode)
        {
            throw new IllegalStateException("Component rule is not a new node rule.");
        }
        
        return element;
    }

    @Override
    public int getExistingNodePosition()
    {
        if (componentType != ComponentType.ExistingNodeByPosition)
        {
            throw new IllegalStateException("Component rule is not a node by position rule.");
        }
        
        return position;
    }

    
}
