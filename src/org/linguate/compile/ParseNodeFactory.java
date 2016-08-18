/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile;

import java.util.List;

/**
 *
 * @author Phil Hutchinson
 */
public interface ParseNodeFactory
{
    ParseNode CreateInnerNode(GrammarProduction production, List<ParseNode> children);
    ParseNode CreateLeafNode(Token element);
}
