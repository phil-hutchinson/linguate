/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

/**
 *
 * @author Phil Hutchinson
 */
public interface SyntaxRuleComponent
{
    enum ComponentType
    {
        NewNode,
        ExistingNodeByPosition,
    }
    
    ComponentType getComponentType();
    SyntaxElement getNewNodeElement();
    int getNodePosition();
    int getChildNodePosition();
}
