/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.linguate.compile.dft.DFTNode;
import org.linguate.compile.dft.DFTNodeFactory;
import org.linguate.compile.grammar.GrammarProduction;
import org.linguate.compile.syntaxtree.SyntaxNode;
import org.linguate.compile.syntaxtree.SyntaxNodeFactory;
import org.linguate.compile.token.Token;

/**
 *
 * @author Phil Hutchinson
 */
public class Syntaxifier implements DFTNodeFactory
{
    SyntaxDefinition syntaxDefinition;
    SyntaxNodeFactory syntaxNodeFactory;
    
    public Syntaxifier(SyntaxDefinition syntaxDefinition, SyntaxNodeFactory syntaxNodeFactory)
    {
        if (syntaxDefinition == null)
        {
            throw new NullPointerException("Syntax Definition cannot be null.");
        }
        
        if (syntaxNodeFactory == null)
        {
            throw new NullPointerException("Syntax Node Factory cannot be null.");
        }
        
        this.syntaxDefinition = syntaxDefinition;
        this.syntaxNodeFactory = syntaxNodeFactory;
    }
    
    public SyntaxNode GetTree(DFTNode sourceDFTNode)
    {
        return ((SyntaxifierDFTNode) sourceDFTNode).getSyntaxNode();
    }
    
    @Override
    public DFTNode CreateInnerNode(GrammarProduction production, List<DFTNode> children)
    {
        if (!syntaxDefinition.getRuleMap().containsKey(production))
        {
            throw new IllegalArgumentException("Production not found in syntax definition.");
        }
        SyntaxRule syntaxRule = syntaxDefinition.getRuleMap().get(production);
        SyntaxRuleComponent parentRule = syntaxRule.getParentComponent();
        List<? extends SyntaxRuleComponent> childrenRules = syntaxRule.getChildrenComponents();
        
        SyntaxNode headSyntaxNode;
        
        if (parentRule.getComponentType() == SyntaxRuleComponent.ComponentType.ExistingNodeByPosition)
        {
            headSyntaxNode = ((SyntaxifierDFTNode) children.get(parentRule.getNodePosition())).getSyntaxNode();
        }
        else // NewNode
        {
            headSyntaxNode = syntaxNodeFactory.CreateNodeForElement(parentRule.getNewNodeElement());
        }
        
        List<SyntaxNode> childrenNodes = new ArrayList<SyntaxNode>();
        
        for (SyntaxRuleComponent childRule : childrenRules)
        {
            if (childRule.getComponentType() == SyntaxRuleComponent.ComponentType.ExistingNodeByPosition)
            {
                SyntaxifierDFTNode containerNode = (SyntaxifierDFTNode) children.get(childRule.getNodePosition());
                childrenNodes.add(containerNode.getSyntaxNode());
            }
            else // NewNode
            {
                SyntaxNode newNode = syntaxNodeFactory.CreateNodeForElement(childRule.getNewNodeElement());
                childrenNodes.add(newNode);
            }
        }
        
        headSyntaxNode.setChildren(childrenNodes);
        
        return new SyntaxifierDFTNode(headSyntaxNode, production.getHead());
    }

    @Override
    public DFTNode CreateLeafNode(Token element)
    {
        SyntaxNode syntaxNode = syntaxNodeFactory.CreateNodeForToken(element);
        return new SyntaxifierDFTNode(syntaxNode, element.getElement());
    }
    
}
