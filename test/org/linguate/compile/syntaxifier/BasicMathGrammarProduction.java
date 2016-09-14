/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.syntaxifier;

import java.util.*;
import org.linguate.compile.grammar.GrammarNonTerminal;
import org.linguate.compile.grammar.GrammarProduction;
import org.linguate.compile.grammar.GrammarSymbol;


/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathGrammarProduction implements GrammarProduction
{
    private String name;
    
    BasicMathGrammarProduction(String name)
    {
        this.name = name;
    }
    
    @Override
    public GrammarNonTerminal getHead() { return null; }
    
    @Override
    public int getBodyLength()
    {
        return 0;
    }

    @Override
    public List<GrammarSymbol> getBody()
    {
        return Collections.unmodifiableList(new ArrayList<GrammarSymbol>());
    }
}
