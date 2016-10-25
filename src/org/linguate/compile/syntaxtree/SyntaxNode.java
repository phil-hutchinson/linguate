/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxtree;

import java.util.List;

/**
 *
 * @author Phil Hutchinson
 */
public interface SyntaxNode
{
    SyntaxElement getElement();
    List<? extends SyntaxNode> getChildren();
    void setChildren(List<? extends SyntaxNode> children);
}
