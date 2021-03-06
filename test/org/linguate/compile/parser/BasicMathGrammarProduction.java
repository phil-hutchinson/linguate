/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.parser;

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
    public BasicMathGrammarNonTerminal head;
    public final ArrayList<GrammarSymbol> body = new ArrayList<>();
    
    BasicMathGrammarProduction(BasicMathGrammarNonTerminal head, GrammarSymbol... body)
    {
        this.head = head;
        this.body.addAll(Arrays.asList(body));
    }
    
    @Override
    public GrammarNonTerminal getHead() { return head; }
    
    @Override
    public int getBodyLength()
    {
        return body.size();
    }

    @Override
    public List<GrammarSymbol> getBody()
    {
        return Collections.unmodifiableList(body);
    }
}
