/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.testimpl.basicmath;

import org.linguate.compile.grammar.GrammarNonTerminal;
import org.linguate.compile.grammar.GrammarTerminal;
import java.util.*;
import org.linguate.compile.grammar.GrammarNonTerminal;
import org.linguate.compile.grammar.GrammarTerminal;
import org.linguate.compile.parser.LRParserAction;
import org.linguate.compile.parser.LRParserState;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathLRParserState implements LRParserState
{
    public final Map<GrammarTerminal,LRParserAction> actions;
    public final Map<GrammarNonTerminal,LRParserState> postReductionStates;
    public LRParserAction endOfInputAction;
    private String name;

    public BasicMathLRParserState(String name)
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
