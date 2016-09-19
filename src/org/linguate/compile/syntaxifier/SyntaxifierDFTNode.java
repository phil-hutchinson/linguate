/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

import org.linguate.compile.dft.DFTNode;
import org.linguate.compile.grammar.GrammarSymbol;
import org.linguate.compile.syntaxtree.SyntaxNode;
/**
 *
 * @author Phil Hutchinson
 */
class SyntaxifierDFTNode implements DFTNode
{
    private final SyntaxNode syntaxNode;
    private final GrammarSymbol grammarSymbol;

    public SyntaxifierDFTNode(SyntaxNode syntaxNode, GrammarSymbol grammarSymbol)
    {
        this.syntaxNode = syntaxNode;
        this.grammarSymbol = grammarSymbol;
    }

    @Override
    public GrammarSymbol getElement()
    {
        return grammarSymbol;
    }
    
    public SyntaxNode getSyntaxNode()
    {
        return syntaxNode;
    }
}
