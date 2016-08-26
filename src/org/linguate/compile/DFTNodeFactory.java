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
public interface DFTNodeFactory
{
    DFTNode CreateInnerNode(GrammarProduction production, List<DFTNode> children);
    DFTNode CreateLeafNode(Token element);
}
