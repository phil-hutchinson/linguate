/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.testimpl.basicmath;

import java.util.*;
import org.linguate.compile.dft.DFTNode;
import org.linguate.compile.grammar.GrammarSymbol;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathDFTNode implements DFTNode
{
    private BasicMathGrammarSymbol element;
    public int value;
    public final ArrayList<BasicMathDFTNode> children = new ArrayList<>();

    public BasicMathDFTNode(BasicMathGrammarSymbol element)
    {
        this(element, null);
    }
    
    public BasicMathDFTNode(BasicMathGrammarSymbol element, int value)
    {
        this(element, null);
        this.value = value;
    }
    
    public BasicMathDFTNode(BasicMathGrammarSymbol element, List<BasicMathDFTNode> children)
    {
        this.element = element;
        if (children != null)
        {
            children.forEach(this.children::add);
        }
    }
    
    
    @Override
    public GrammarSymbol getElement()
    {
        return element;
    }
    
}
