/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.dft;

import org.linguate.compile.grammar.GrammarProduction;
import java.util.List;
import org.linguate.compile.lexeme.Lexeme;

/**
 *
 * @author Phil Hutchinson
 */
public interface DFTNodeFactory
{
    DFTNode CreateInnerNode(GrammarProduction production, List<DFTNode> children);
    DFTNode CreateLeafNode(Lexeme element);
}
