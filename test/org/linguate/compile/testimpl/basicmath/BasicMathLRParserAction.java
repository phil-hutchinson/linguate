/*
 * This code has not bee released under any license.
 * In future, it will be released under an open-source license.
 */
package org.linguate.compile.testimpl.basicmath;

import org.linguate.compile.grammar.GrammarProduction;
import org.linguate.compile.parser.LRParserAction;
import org.linguate.compile.parser.LRParserState;

/**
 *
 * @author Phil Hutchinson
 */
public class BasicMathLRParserAction implements LRParserAction
{
    private ActionType action;
    private BasicMathLRParserState shiftState;
    private BasicMathGrammarProduction reduceRule;

    public BasicMathLRParserAction()
    {
        action = ActionType.Accept;
    }
    
    public BasicMathLRParserAction(BasicMathLRParserState shiftState)
    {
        action = ActionType.Shift;
        this.shiftState = shiftState;
    }

    public BasicMathLRParserAction(BasicMathGrammarProduction reduceRule)
    {
        action = ActionType.Reduce;
        this.reduceRule = reduceRule;
    }
    
    @Override
    public ActionType getAction() { return action; }

    @Override
    public LRParserState getShiftState() { return shiftState; }

    @Override
    public GrammarProduction getReduceRule() { return reduceRule; }
    
}
