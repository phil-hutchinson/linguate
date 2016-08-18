/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.compile;

import java.util.*;


/**
 *
 * @author Phil Hutchinson
 */
public class TestImplGrammarProduction implements GrammarProduction
{
    public TestImplGrammarNonTerminal head;
    public final ArrayList<GrammarSymbol> body = new ArrayList<>();
    
    TestImplGrammarProduction(TestImplGrammarNonTerminal head, GrammarSymbol... body)
    {
        this.head = head;
        for(GrammarSymbol bodyElement : body)
        {
            this.body.add(bodyElement);
        }
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
