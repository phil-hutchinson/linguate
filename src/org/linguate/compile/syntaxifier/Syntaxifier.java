/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

import java.util.List;
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
    public Syntaxifier(SyntaxDefinition syntaxDefinition, SyntaxNodeFactory syntaxNodeFactory)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public SyntaxNode GetTree(DFTNode sourceDFTNode)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public DFTNode CreateInnerNode(GrammarProduction production, List<DFTNode> children)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DFTNode CreateLeafNode(Token element)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
