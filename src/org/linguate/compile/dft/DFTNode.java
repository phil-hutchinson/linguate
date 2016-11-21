/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.dft;

import org.linguate.compile.grammar.GrammarSymbol;

/**
 * A DFTNode is a node in a tree generated by a depth first traversal. An example
 * of a depth first traversal would be the order in which a bottom-up parser 
 * processes its elements.
 * 
 * <p>The DFTNode and its companion {@link org.linguate.compile.dft.DFTNodeFactory 
 * DFTNodeFactory} are primarily for use by bottom-up parsers to offload the work
 * of generating the nodes of the parse tree, so that the parser itself does not 
 * need to be aware which DFTNode-implementing class is being instantiated.
 * 
 * <p>Since parsers do not need to know the children of elements, no method is 
 * exposed for accessing children, although a class implementing this interface
 * will likely expose this through a different interface.
 * 
 * @author Phil Hutchinson
 */
public interface DFTNode
{

    /**
     * Provides the grammar symbol this node holds.
     * @return the grammar symbol of the node
     */
    GrammarSymbol getElement();
}
