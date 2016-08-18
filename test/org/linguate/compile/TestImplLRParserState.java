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
public class TestImplLRParserState implements LRParserState
{
    public final Map<GrammarTerminal,LRParserAction> actions;
    public final Map<GrammarNonTerminal,LRParserState> postReductionStates;
    public LRParserAction endOfInputAction;
    private String name;

    public TestImplLRParserState(String name)
    {
        this.name = name;
        actions = new HashMap<>();
        postReductionStates = new HashMap<>();
    }
    
    @Override
    public LRParserAction getAction(GrammarTerminal nextSymbol)
    {
        return actions.getOrDefault(nextSymbol, null);
    }

    @Override
    public LRParserAction getEndOfInputAction()
    {
        return endOfInputAction;
    }
    
    @Override
    public LRParserState getPostReductionState(GrammarNonTerminal productionBase)
    {
        return postReductionStates.getOrDefault(productionBase, null);
    }

    @Override
    public String getName()
    {
        return name;
    }

}
